package payroll;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Payroll {
	public static void main(String[] args) {
		File fileName;
		String process;
		Scanner file;
		try {
			fileName = new File(args[0]);
			process = args[1];
			checkProcessName(process);
			file = new Scanner(fileName);
		} catch (NullPointerException e) {
			System.out.println("Please enter a file name.");
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please provide a desired process type.");
			return;
		} catch (RuntimeException e) {
			System.out.println("Invalid process type.");
			return;
		} catch (FileNotFoundException e) {
			System.out.println("File cannot be found.");
			return;
		}
		EmployeeList roster = new EmployeeList();
		roster.storeData(file, process);
		file.close();
		Display disp = determineWhichProcess(process);
		disp.display(roster);
	}
	private static void checkProcessName(String process) throws RuntimeException {
		if (!process.equals("Payslips") && !process.equals("Employees") && !process.equals("Burden") && !process.equals("PAYE")) {
			throw new RuntimeException();
		}
	}
	private static Display determineWhichProcess(String process) {
		Display disp;
		if (process.equals("Payslips")) {
			disp = new Payslips();
		} else if (process.equals("Employees")) {
			disp = new Roster();
		} else if (process.equals("Burden")) {
			disp = new Burden();
		} else {
			disp = new PAYE();
		}
		return disp;
	}
}
