package payroll;
import java.util.Scanner;
public class Payroll {
	public static void main(String[] args) {
		String argOne = null;
		String argTwo = null;
		argOne = args[0];
		argTwo = args[1];
		EmployeeList employeeList = new EmployeeList();
		employeeList.makeEmployeeList(argOne);
		if (argTwo.equals("Employees")) {
			employeeList.sortListByName();
		}
		else {
			employeeList.sortListByID();
		}
		employeeList.outputProcess(argTwo);
	}
}
