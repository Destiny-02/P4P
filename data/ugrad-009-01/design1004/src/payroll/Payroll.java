package payroll;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class Payroll {
	private EmployeeList _employees;
	private Process _process;
	public Payroll() {
		_employees = new EmployeeList();
	}
	public static void main(String[] args){
		Payroll thisPayroll = new Payroll();
		if (args.length<2) {
			throw new RuntimeException("Missing arguments. Please supply input file and type of processing.");
		}
		String fileName = args[0];
		String line = null;
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
			while((line = bufferedReader.readLine()) != null) {
				if (!line.startsWith("#") && !line.isEmpty()) {
					thisPayroll._employees.enterRecord(line);
				}
			}
		}
		catch(FileNotFoundException fnfex) {
			System.out.println("Unable to find file " + fileName);
		}
		catch(IOException ioex) {
			System.out.println("Error reading " + fileName);
			ioex.printStackTrace();
		}
		switch (args[1]) {
		case "Payslips":
			thisPayroll._process = new Payslips();
			thisPayroll._employees.sortEmployees(thisPayroll._employees.employeeList);
			break;
		case "Employees":
			thisPayroll._process = new Employees();
			thisPayroll._employees.sortByFamilyName();
			break;
		case "Burden":
			thisPayroll._process = new Burden();
			break;
		case "PAYE":
			thisPayroll._process = new PAYE();
			break;
		default:
			throw new RuntimeException ("'" + args[1] + "' is an invalid process. Please select from Payslips, Employees, Burden, and PAYE (case sensitive).");
		}
		thisPayroll._process.printDate();
		thisPayroll._process.process(thisPayroll._employees.employeeList);
	}
}