package payroll;
public class HourlyEmployee extends Employee {
	public HourlyEmployee(String[] employeedata) {
		super(employeedata);
	}
	public double grossEarnings() {
		if (gethours() <= 40) {
			return round(gethours() * getrate());
		} else if (gethours() <= 43) {
			return round(((40 * getrate()) + (1.5 * (gethours() % 40) * getrate())));
		} else {
			return round(((40 * getrate()) + (1.5 * 3 * getrate()) + (2 * (gethours() % 43) * getrate())));
		}
	}
}
