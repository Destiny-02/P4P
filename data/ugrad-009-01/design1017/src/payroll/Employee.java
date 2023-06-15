package payroll;
public abstract class Employee extends AbstractProcessingMethods {
	private int _TID;
	private String _name;
	protected double _rate;
	private double _YTD;
	private String _startDate;
	private String _endDate;
	protected double _hours;
	private double _deduction;
	public Employee(int TID, String name, double rate, double YTD, String[] dates, double hours, double deduction) {
		_TID = TID;
		_name = name;
		_rate = rate;
		_YTD = YTD;
		_startDate = dates[0];
		_endDate = dates[1];
		_hours = hours;
		_deduction = deduction;
	}
	public int getTID() {
		return _TID;
	}
	public String getName() {
		return _name;
	}
	public double getRate() {
		return _rate;
	}
	public double getYTD() {
		return _YTD;
	}
	public String getStartDate() {
		return _startDate;
	}
	public String getEndDate() {
		return _endDate;
	}
	public double getHours() {
		return _hours;
	}
	public double getDeduction() {
		return _deduction;
	}
	public int compareTID(Employee otherEmployee) {
		if (_TID < otherEmployee._TID) {
			return -1;
		} else if (_TID > otherEmployee._TID) {
			return 1;
		} else {
			return 0;
		}
	}
	public int compareName(Employee otherEmployee) {
		return _name.compareTo(otherEmployee._name);
	}
	public double getGross() {
		if (this instanceof HourlyEmployee) {
			HourlyEmployee currentEmployee = (HourlyEmployee) this;
			return currentEmployee.getGrossValue();
		} else {
			SalariedEmployee currentEmployee = (SalariedEmployee) this;
			return currentEmployee.getGrossValue();
		}
	}
	public String getEmploymentType(){
		if (this instanceof HourlyEmployee) {
			return "Hourly";
		} else {
			return "Salaried";
		}
	}
}
