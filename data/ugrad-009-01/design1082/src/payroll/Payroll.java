package payroll;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
public class Payroll {
	public static void main(String[] args) throws FileNotFoundException, PayrollExceptions{
		ArrayList<EmployeeData> list = new ArrayList<EmployeeData>();
		Scanner scanner = new Scanner(new File(args[0]));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(!line.startsWith("#") && !line.equals("")){
				line = line.replace("$", "");
				String[] data = line.split("\\t");
				EmployeeData employees = new EmployeeData(data);
				list.add(employees);
			}
		}
		scanner.close();
		Process process = new Process(list);
		switch(args[1]){
		case "Payslips":
			process.date();
			process.Payslips();;
			break;
		case "Employees":
			process.date();
			process.Employees();
			break;
		case "Burden":
			process.date();
			process.Burden();
			break;
		case "PAYE":
			process.date();
			process.PAYE();
			break;
		default:
			System.out.println("Please enter a valid input.");
			break;
		}
	}
}
