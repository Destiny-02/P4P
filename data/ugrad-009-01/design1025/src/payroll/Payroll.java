package payroll;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class Payroll {
	public static void main(String[] args) throws IOException, EmployeeException{
		EmployeeException.checkArgs(args);
		InputStream inputStream = new FileInputStream(args[0]);
		InputStreamReader reader = new InputStreamReader(inputStream);
		BufferedReader input = new BufferedReader(reader);
		ArrayList<Employees> employees = new ArrayList<Employees>();
		String line = input.readLine();
		while(line != null) {
			while (line != null && (line.equals("") || line.substring(0, 1).equals("#"))) {
				line = input.readLine();
			}
			if (line == null) {
				break;
			}
			EmployeeException inputChecker = new EmployeeException(employees);
			inputChecker.checkInput(line);
			employees.add(new Employees(line));
			line = input.readLine();
		}
		EmployeeException employeeChecker = new EmployeeException(employees);
		employeeChecker.checkEmployees();
		reader.close();
		input.close();
		Processing process = new Processing(employees);
		switch (args[1]) {
		case "Payslips" :
			process.payslips();
			break;
		case "Employees" :
			process.employees();
			break;
		case "Burden" :
			process.burden();
			break;
		case "PAYE" :
			process.paye();
			break;
		default :
			System.out.println("Invalid input");
			break;
		}
	}
}
