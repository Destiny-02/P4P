package payroll;
import java.text.DecimalFormat;
public class Employee {
	private int _TID;
	private String _NAME;
	private String _EMPLOYMENT;
	private float _RATE;
	private float _YTD;
	private String _START;
	private String _END;
	private float _HOURS;
	private float _DEDUCTION;
	public Employee(String string) throws EmployeeException{
		String[] parts = string.split("\t");
		_TID = Integer.parseInt(parts[0]);
		_NAME = parts[1];
		if((parts[2].equals("Salaried"))||(parts[2].equals("Hourly"))){
			_EMPLOYMENT = parts[2];
		} else{
			throw new EmployeeException("Invalid employment type");
		}
		if((parts[3].substring(0,1)).equals("$")){
			_RATE = Float.parseFloat(parts[3].substring(1));
		} else{
			throw new EmployeeException("Missing '$' in RATE");
		}
		if((parts[4].substring(0,1)).equals("$")){
			_YTD = Float.parseFloat(parts[4].substring(1));
		} else{
			throw new EmployeeException("Missing '$' in YTD");
		}
		if((Integer.parseInt(parts[6].substring(8)) - (Integer.parseInt(parts[5].substring(8))) == 6)){
			_START = parts[5];
			_END = parts[6];
		} else{
			throw new EmployeeException("Invalid pay period");
		}
		_HOURS = Float.parseFloat(parts[7]);
		if((parts[8].substring(0,1)).equals("$")){
			_DEDUCTION = Float.parseFloat(parts[8].substring(1));
		} else{
			throw new EmployeeException("Missing '$' in DEDUCTION");
		}
	}
	public int GetTID(){
		return _TID;
	}
	public float CalculateGross(Employee employee){
		if(_EMPLOYMENT.equals("Salaried")){
			return _RATE/52;
		}
		else{
			if(_HOURS <= 40){
				return _HOURS*_RATE;
			}
			else if(_HOURS <= 43){
				return (float) ((40 * _RATE) + (_HOURS - 40 ) * (_RATE * 1.5));
			}
			else{
				return (float) ((40 * _RATE) + (3 * (_RATE * 1.5)) + (_HOURS - 43) * _RATE * 2);
			}
		}
	}
	public float CalculatePAYE(Employee employee){
		float income;
		if((employee._EMPLOYMENT).equals("Salaried")){
			income = _RATE;
		}
		else{
			income = employee.CalculateGross(employee)*52;
		}
		if(income < 14000){
			return (float) (income * 0.105)/52;
		}
		else if(income < 48000){
			return (float) ((14000 * 0.105) + ((income - 14000) * 0.175))/52;
		}
		else if(income < 70000){
			return (float) (14000 * 0.105 + 34000 * 0.175 + (income - 48000) * 0.30)/52;
		}
		else{
			return (float) (14000 * 0.105 + 34000 * 0.175 + 22000 * 0.30 + (income - 70000) * 0.33)/52;
		}
	}
	public float CalculateNett(Employee employee){
		return employee.CalculateGross(employee) - employee.CalculatePAYE(employee) - employee._DEDUCTION;
	}
	public String Round(float f){
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(f);
	}
	public String Payslips(Employee employee){
		String name = _NAME;
		String[] parts = name.split(",");
		String output = "";
		output = output + (_TID + ".");
		output = output + (parts[1] + " " + parts[0]);
		output = output + (", Period: ");
		output = output +(_START);
		output = output + (" to ");
		output = output + (_END);
		output = output + (". Gross: $");
		output = output + (Round(employee.CalculateGross(employee)));
		output = output + (", PAYE: $");
		output = output + (Round(employee.CalculatePAYE(employee)));
		output = output + (", Deductions: $");
		output = output + (Round(_DEDUCTION));
		output = output + (" Nett: $");
		output = output + (Round(employee.CalculateNett(employee)));
		output = output + (" YTD: $");
		output = output + (Round(_YTD + employee.CalculateGross(employee)));
		return output;
	}
	public String Employees(Employee employee){
		String output = (_NAME + " (" + _TID + ") " + _EMPLOYMENT + ", $" + Round(_RATE) + " YTD:$" + (Round(_YTD + employee.CalculateGross(employee))));
		return output;
	}
	public String Burden(Employee employee){
		String output = (_START) + (" to ") + (_END);
		return output;
	}
	public String PAYE(Employee employee){
		String output = (_START) + (" to ") + (_END);
		return output;
	}
}
