package payroll;
public class HourlyEmployee extends Employee {
	public HourlyEmployee(int tid, String firstName, String lastName, double rate,
			double ytd, String start, String end,
			double hours, double deduction){
		super(tid, firstName, lastName, rate, ytd, start, end, hours, deduction);
	}
	public void calculatePay(){
		double hours = _hoursWorked;
		_pay = hours * _rate;
		if (hours > 40.0){
			hours -= 40.0;
			_pay += hours * _rate / 2;
			if (hours > 3.0){
				hours -= 3.0;
				_pay += hours * _rate / 2;
			}
		}
		_pay = Math.round(_pay * 100) / 100;
	}
	public String toStringEmployees(){
		String employee = "";
		employee += String.format("%s, %s", _lastName, _firstName);
		employee += String.format(" (" + _taxID + ")");
		employee += String.format(" " + EmployeeType.hourly);
		employee += String.format(", $%.2f", _rate);
		employee += String.format(" YTD:$%.2f", _ytd);
		return employee;
	}
}
