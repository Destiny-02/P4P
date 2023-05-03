package payroll;
import java.io.IOException;
import java.util.ArrayList;
public class Payroll {
	public static void main(String[] args) throws IOException, PayrollException {
		String filename = args[0];
		String processingKey = args[1];
		InputReader inputReader = new InputReader(filename);
		ArrayList<String> stringList = inputReader.processInput();
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		for (int i = 0; i < stringList.size(); i++) {
			if (stringList.get(i).contains("Salaried")) {
				SalariedEmployee employee = new SalariedEmployee(stringList.get(i));
				employee.initialiseEmployee();
				employeeList.add(employee);
			} else if (stringList.get(i).contains("Hourly")) {
				HourlyEmployee employee = new HourlyEmployee(stringList.get(i));
				employee.initialiseEmployee();
				employeeList.add(employee);
			}
		}
		OutputProcessor outputProcessor = new OutputProcessor(employeeList);
		outputProcessor.printDate();
		if (processingKey.equals("Payslips")) {
			outputProcessor.sortByTID();
			outputProcessor.processPayslips();
		} else if (processingKey.equals("Employees")) {
			outputProcessor.processEmployees();
		} else if (processingKey.equals("Burden")) {
			outputProcessor.processBurden();
		} else if (processingKey.equals("PAYE")) {
			outputProcessor.processPAYE();
		}
	}
}
