package payroll;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
public class Input {
	private File _file;
	private String _processingArg;
	public Input(File file, String processingArg) {
		_file = file;
		_processingArg = processingArg;
	}
	public void getInputData(List<Employee> employeeList) {
		try {
			Scanner scanner = new Scanner(_file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!(line.contains("#") || line.equals(""))) {
					CreateEmployee newEmployee = new CreateEmployee();
					newEmployee.addEmployee(employeeList, line);
				}
			}
			scanner.close();
		}
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	public void getProcessingArg(List<Employee> employeeList) {
		String[] processingOptions = {"Payslips", "Employees", "Burden", "PAYE"};
		for (int i=0; i<processingOptions.length; i++) {
			if (_processingArg.equals(processingOptions[i])) {
				ProcessingFactory processing = new ProcessingFactory(_processingArg);
				processing.initialise(employeeList);
				return;
			}
		}
		System.out.println("An invalid processing option was entered: " + _processingArg);
	}
}
