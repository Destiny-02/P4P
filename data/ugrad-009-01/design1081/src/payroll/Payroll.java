package payroll;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Payroll {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(args[0]));
		ArrayList<Employee> arraylist = new ArrayList<Employee>();
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.startsWith("#") == false) {
				if (line.isEmpty() == false) {
					arraylist.add(new Employee(line));
				}
			}
		}
		if (arraylist.isEmpty()) {
			throw new ProcessingException("No valid input lines found");
		}
		Processing process = new Processing(arraylist);
		switch (args[1]){
			case "Burden" :
				process.burdenProcess();
				break;
			case "PAYE" :
				process.payeProcess();
				break;
			case "Employees" :
				process.employeeProcess();
				break;
			case "Payslips" :
				process.payslipProcess();
				break;
			default :
				System.out.println("Invalid Input. Please type: 'Burden', 'PAYE', 'Employees', or 'Payslips' only");
		}
	}
}
