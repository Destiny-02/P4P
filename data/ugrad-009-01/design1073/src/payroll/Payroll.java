
package payroll;
import java.io.IOException;
public class Payroll {
	public static void main(String[] args) throws IOException, PayRollSystemException{
		String fileName = args[0];
		String functionToDo = args[1];
		EmployeeList employeeData = new EmployeeList(fileName);
		switch (functionToDo){
		case "Payslips":
			employeeData.printPayslip();
			break;
		case "Employees":
			employeeData.printEmployees();
			break;
		case "Burden":
			employeeData.printProcessedBurdenInformation();
			break;
		case "PAYE":
			employeeData.printProcessedPAYEInformation();
			break;
		}
	}
}

