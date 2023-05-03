package payroll;
public class Employee {
	private int _taxID;
	private String _name;
	private String _employeeType;
	private double _rate;
	private double _yearToDate;
	private String _startDate;
	private String _endDate;
	private double _hours;
	private double _deductions;
	private double _gross;
	private double _PAYE;
	private double _nett;
	private String _firstName;
	private String _lastName;
	public Employee(int taxID, String name, String employeeType, double rate, double YTD, String startDate, String endDate, double hours, double deductions){
		_taxID = taxID;
		_name = name;
		_employeeType = employeeType;
		_rate = rate;
		_yearToDate = YTD;
		_startDate = startDate;
		_endDate = endDate;
		_hours = hours;
		_deductions = deductions;
	}
	public void calculateAllData(){
		calculateFirstandLastName();
		calculateGross();
		calculatePAYE();
		calculateNett();
		calculateYTD();
	}
	public void calculateFirstandLastName(){
		String[] lastFirst = _name.split(", ");
		_lastName = lastFirst[0];
		_firstName = lastFirst[1];
	}
	public void calculateGross(){
		if(_employeeType.equals("Hourly")){
			if(_hours <= 40){
				_gross = _hours * _rate;
			}
			else if((_hours > 40)&&(_hours <= 43)){
				_gross = (40.0 * _rate) + ((_hours - 40.0) * _rate * 1.5);
			}
			else if (_hours > 43){
				_gross = (40.0 * _rate) + (3.0 * _rate * 1.5) + ((_hours - 43.0) * _rate * 2.0);
			}
		}
		else if (_employeeType.equals("Salaried")){
			_gross = _rate / 52.0;
		}
		_gross = Math.round(_gross * 100.0) / 100.0;
	}
	public void calculatePAYE(){
		double annualRate = 0.0;
		if(_employeeType.equals("Hourly")){
			annualRate = _gross * 52.0;
		}
		else if((_employeeType.equals("Salaried"))){
			annualRate = _rate;
		}
		if(annualRate <= 14000.0){
			_PAYE = annualRate * 0.105;
		}
		else if(annualRate <= 48000.0){
			_PAYE = (14000.0 * 0.105) + ((annualRate - 14000.0) * 0.175);
		}
		else if (annualRate <= 70000.0){
			_PAYE = (14000.0 * 0.105) + (34000.0 * 0.175) + ((annualRate - 48000.0)* 0.30);
		}
		else if (annualRate > 70000.0){
			_PAYE = (14000.0 * 0.105) + (34000.0 * 0.175) + (22000.0 * 0.30) + ((annualRate - 70000.0) * 0.33);
		}
		_PAYE = (Math.round((_PAYE / 52.0) * 100.0) / 100.0);
	}
	public void calculateNett(){
		_nett = _gross - _PAYE - _deductions;
		if(_nett >= 0){
			_nett = Math.round(_nett * 100.0) / 100.0;
		}
		else if(_nett < 0){
			System.out.println("Error, nett value negative");
		}
	}
	public void calculateYTD(){
		_yearToDate = _yearToDate + _gross;
		_yearToDate = Math.round(_yearToDate * 100.0) / 100.0;
	}
	public int getTaxID(){
		return _taxID;
	}
	public String getLastName(){
		return _lastName;
	}
	public double getPAYE(){
		return _PAYE;
	}
	public double getGross(){
		return _gross;
	}
	public String getFirstName(){
		return _firstName;
	}
	public String getStartDate(){
		return _startDate;
	}
	public String getEndDate(){
		return _endDate;
	}
	public double getDeductions(){
		return _deductions;
	}
	public double getNett(){
		return _nett;
	}
	public double getYTD(){
		return _yearToDate;
	}
	public String getEmployeeType(){
		return _employeeType;
	}
	public double getRate(){
		return _rate;
	}
}
