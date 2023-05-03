package payroll;
public class Payslip {
	private String _employeePayslip;
	public Payslip(Employee employee) {
		_employeePayslip = employee.generateReport("Payslips", 0.0);
	}
	public void printPayslip() {
		System.out.println(_employeePayslip);
	}
}
