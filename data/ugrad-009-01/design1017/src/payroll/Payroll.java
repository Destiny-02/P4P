package payroll;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
public class Payroll {
	public static void main(String args[]) {
		File file;
		String processing;
		try {
			file = new File(args[0]);
			processing = args[1];
		} catch (ArrayIndexOutOfBoundsException exception) {
			System.out.println("Error. There is a problem with on of the input arguments.");
			return;
		}
		List<Employee> employeeList = new LinkedList<Employee>();
		Input input = new Input(file, processing);
		input.getInputData(employeeList);
		input.getProcessingArg(employeeList);
	}
}
