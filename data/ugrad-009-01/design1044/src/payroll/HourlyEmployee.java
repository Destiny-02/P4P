package payroll;
public class HourlyEmployee extends Employee {
	public HourlyEmployee(String[] employeeData, int line, String filePath)
			throws PayrollDateFormatException, EmployeeDataException {
		super(employeeData, line, filePath);
	}
	public double calculateGross(double hours, double rate) {
		double gross = 0;
		if (hours <= 40) {
			gross = Math.round(hours * rate * 100.0) / 100.0;
		} else if (hours <= 43) {
			gross = Math.round(40 * rate * 100.0) / 100.0 + Math.round(((hours - 40) * (rate * 1.5)) * 100.0) / 100.0;
		} else {
			gross = Math.round(40 * rate * 100.0) / 100.0 + Math.round(3 * (rate * 1.5) * 100.0) / 100.0
					+ Math.round((hours - 43) * (rate * 2) * 100.0) / 100.0;
		}
		return gross;
	}
	@Override
	public int compareTo(Employee o) {
		return 0;
	}
}
