package payroll;
public class Payroll {
	public static void main (String[] args) {
		EmployeeList employeeList = new EmployeeList();
		employeeList.createEmployeeList(args[0]);
		if (args[1].equals("Payslips")) {
			employeeList.calculatePayslipsProcessing();
		} else if (args[1].equals("Employees")) {
			employeeList.calculateEmployeeProcessing();
		} else if (args[1].equals("Burden")) {
			employeeList.calculateBurdenProcessing();
		} else if (args[1].equals("PAYE")) {
			employeeList.calculatePAYEProcessing();
		}
	}
}
