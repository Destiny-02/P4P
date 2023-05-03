package payroll;
public class SalariedEmployee extends Employee {
	public SalariedEmployee(int tid, String firstName, String lastName, double rate,
			double ytd, String start, String end,
			double hours, double deduction){
		super(tid, firstName, lastName, rate, ytd, start, end, hours, deduction);
	}
	public String toStringEmployees(){
		String employee = "";
		employee += String.format("%s, %s", _lastName, _firstName);
		employee += String.format(" (" + _taxID + ")");
		employee += String.format(" " + EmployeeType.salaried);
		employee += String.format(", $%.2f", _rate);
		employee += String.format(" YTD:$%.2f", _ytd);
		return employee;
	}
}
