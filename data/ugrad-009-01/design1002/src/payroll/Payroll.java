package payroll;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Payroll {
	public static void main(String [] args) {
		Scanner inputFile;
		try {
			inputFile = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		EmployeeList employeeDatabase = new EmployeeList();
		while (inputFile.hasNextLine()) {
			String line = inputFile.nextLine();
			if ((line.compareTo("") != 0) && (Character.isDigit(line.charAt(0)))) {
				String[] lineParts = line.split("\\t");
				employeeDatabase.add(lineParts);
			}
		}
		inputFile.close();
		Display output = null;
		if (args[1].compareTo("Payslips") == 0) {
			output = new PayslipsDisplay(employeeDatabase);
		} else if (args[1].compareTo("Employees") == 0) {
			output = new EmployeesDisplay(employeeDatabase);
		} else if (args[1].compareTo("Burden") == 0) {
			output = new BurdenDisplay(employeeDatabase);
		} else if (args[1].compareTo("PAYE") == 0) {
			output = new PAYEDisplay(employeeDatabase);
		}
		output.display();
	}
}
