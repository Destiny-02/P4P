package payroll;
public class SalariedEmployee extends Employee {
	public SalariedEmployee(String[] employeedata) {
		super(employeedata);
	}
	public double grossEarnings() {
		return round(getrate() / 52);
	}
}