package payroll;
import java.io.IOException;
import java.io.File;
public class Payroll {
	public static void main(String[] args) throws IOException, EmployeeException{
		if (args.length < 2) {
			throw new IllegalArgumentException("Error: no file input and/or process command found");
		}
		File file = new File(args[0]);
		EmployeeList list = new EmployeeList(file);
		ErrorDetector detector = new ErrorDetector(list);
		detector.Detect();
		String processCMD = args[1];
		ProcessFactory proFac = new ProcessFactory();
		proFac.process(list, processCMD);
	}
}
