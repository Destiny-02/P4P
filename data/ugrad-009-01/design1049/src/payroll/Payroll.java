package payroll;
public class Payroll {
	public static void main(String[] args) {
		InputReader input = new InputReader();
		EmployeeInfo[] employeeData = input.readInput(args[0]);
		switch (args[1]) {
		case "Payslips" :
			PayslipsFormat payslips = new PayslipsFormat();
			payslips.printPayslip(employeeData);
			break;
		case "Employees" :
			EmployeesFormat employees = new EmployeesFormat();
			employees.printEmployees(employeeData);
			break;
		case "Burden" :
			BurdenFormat burden = new BurdenFormat();
			burden.printBurden(employeeData);
			break;
		case "PAYE" :
			PAYEFormat paye = new PAYEFormat();
			paye.printPAYE(employeeData);
			break;
		default :
			System.out.println("Invalid type of processing");
			break;
		}
	}
}
