
package payroll;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import payroll.employees.EmployeeList;
public class Payroll {
	public static void main(String[] args) throws FileNotFoundException, PayrollException {
		File file = new File(args[0]);
		Scanner scan = new Scanner(file);
		int count = countScan(scan);
		scan = new Scanner(file);
		EmployeeList employeeList;
		employeeList = new EmployeeList(count, scan);
		scan.close();
			process(employeeList, args[1]);
	}
	private static int countScan(Scanner scan) {
		int count = 0;
		while (scan.hasNextLine()) {
			count++;
			scan.nextLine();
		}
		scan.close();
		return count;
	}
	private static void process(EmployeeList employeeList, String processType) throws PayrollException {
		Date date = new Date();
		System.out.printf("%tF\n", date);
		ArrayList<String> output;
		switch (processType) {
			case "Payslips":
				output = employeeList.payslips();
				break;
			case "Employees":
				output = employeeList.employees();
				break;
			case "Burden":
				output = employeeList.burden();
				break;
			case "PAYE":
				output = employeeList.paye();
				break;
			default:
				throw new PayrollException("Invalid input command for args[1]. " +
						"Possible commands are: \'Payslips\', \'Employees\', \'Burden\', \'PAYE\'.");
		}
		for (String line : output) {
		System.out.println(line);
		}
	}
}

