package payroll;
import java.text.DecimalFormat;
import java.util.Comparator;
public class Employee{
	private int _taxID;
	private String _name;
	private String _employment;
	private double _rate;
	private double _yearToDate;
	private String _startDate;
	private String _endDate;
	private double _hours;
	private double _deductions;
	public Employee(String employeeRecord){
		String[] employeeDetails = employeeRecord.replace("$", "").split("\t");
		_taxID = Integer.parseInt(employeeDetails[0]);
		_name = employeeDetails[1];
		_employment = employeeDetails[2];
		_rate = Double.parseDouble(employeeDetails[3]);
		_yearToDate = Double.parseDouble(employeeDetails[4]);
		_startDate = employeeDetails[5];
		_endDate = employeeDetails[6];
		_hours = Double.parseDouble(employeeDetails[7]);
		_deductions = Double.parseDouble(employeeDetails[8]);
	}
	public String getPayPeriod(){
		return _startDate + " to " + _endDate;
	}
	public String givenNamesFirst(){
		String[] separatedName = _name.split(" ");
		return separatedName[1] + " " + separatedName[0];
	}
	public class EmployeeDataComputation{
		public String payslip(){
			return _taxID + ". " + givenNamesFirst() + " Period: " + getPayPeriod() + ". Gross: $" + convertToTwoDP(calculateGross())
			+ ", PAYE: $" + convertToTwoDP(calculatePAYE()) + ", Deductions: $" + convertToTwoDP(_deductions) + " Nett: $" + convertToTwoDP(calculateNett())+
			" YTD: $" + convertToTwoDP(calculateYTD());
		}
		public String employees(){
			return _name + " (" + _taxID + ") " + _employment + ", $" + convertToTwoDP(_rate) + " YTD:$" + convertToTwoDP(calculateYTD());
		}
		public String convertToTwoDP(double doubleValue){
			DecimalFormat df2 = new DecimalFormat(".00");
			return df2.format(doubleValue);
		}
		public double roundTo2DP(double value) {
		    value = value * 100;
		    long tmp = Math.round(value);
		    return (double) tmp / 100;
		}
		public double calculateGross(){
			double gross;
			if (_employment.toLowerCase().equals("salaried")){
				gross = roundTo2DP(_rate/52);
			} else {
				if (_hours <= 40){
					gross = roundTo2DP(_rate*_hours);
				} else if (_hours <= 43){
					gross = roundTo2DP(_rate*40 + 1.5*_rate*(_hours-40));
				} else {
					gross = roundTo2DP(_rate*40 + 3*_rate + 2*_rate*(_hours-42));
				}
			}
			if (gross < _deductions){
				throw new PayrollException("Deductions cannot be greater than what the employee with TID:" + _taxID + " earns.");
			}
			return gross;
		}
		public double calculatePAYE(){
			double rate = _rate;
			double tax;
			if (_employment.toLowerCase().equals("hourly")){
				rate = 52*calculateGross();
			}
			if (rate <= 14000){
				tax = (rate*0.105)/52;
			} else if (rate <= 48000){
				tax = (14000*0.105)/52 + ((rate-14000)*0.175)/52;
			} else if (rate <= 70000){
				tax = (14000*0.105)/52 + ((34000)*0.175)/52 + ((rate - 48000)*0.3)/52;
			} else {
				tax = (14000*0.105)/52 + ((34000)*0.175)/52 + (22000*0.3)/52 + ((rate-70000)*0.33)/52;
			}
			return roundTo2DP(tax);
		}
		public double calculateNett(){
			return calculateGross() - _deductions - calculatePAYE();
		}
		public double calculateYTD(){
			_yearToDate += calculateGross();
			return _yearToDate;
		}
	}
    public static Comparator<Employee> TIDOrder() {
        return new Comparator<Employee>() {
        	public int compare(Employee thisEmployee, Employee otherEmployee) {
        		return  thisEmployee._taxID - otherEmployee._taxID;
        	}
        };
    }
    public static Comparator<Employee> NameOrder() {
        return new Comparator<Employee>() {
			public int compare(Employee thisEmployee, Employee otherEmployee) {
				return (thisEmployee._name).compareTo(otherEmployee._name);
			}
        };
    }
}
