package payroll;
public class SalariedEmployee extends Employee {
	public SalariedEmployee(String[] employeeData, int line, String filePath)
			throws PayrollDateFormatException, EmployeeDataException {
		super(employeeData, line, filePath);
	}
	public double calculateGross(double hours, double rate) {
		double gross = 0;
		gross = Math.round((rate / 52.0) * 100.0) / 100.0;
		return gross;
	}
	@Override
	public int compareTo(Employee o) {
		return 0;
	}
}