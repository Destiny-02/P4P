package payroll;
import java.io.IOException;
import java.util.List;
public class Payroll {
	public static void main(String[] args) throws IOException  {
		Input currentInput = new Input(args);
		List<String> _information = currentInput.readInput();
		GetCurrentDate currentDate = new GetCurrentDate();
		currentDate.printCurrentDate();
		Process currentProcess = null;
		if (args[1].equals("Payslips")){
			currentProcess = new PayslipsProcessing(_information);
		} else if (args[1].equals("Employees")){
			currentProcess = new EmployeesProcessing(_information);
		} else if(args[1].equals("Burden")){
			currentProcess = new BurdenProcessing(_information);
		} else if (args[1].equals("PAYE")){
			currentProcess = new PAYEProcessing(_information);
		} else{
			throw new NullPointerException(args[1] + " is an invadlid Processing, please enter ¡®Payslips¡¯, ¡®Employees¡¯, ¡®Burden¡¯ or ¡®PAYE¡¯ ONLY" );
		}
		currentProcess.sortInformation();
		currentProcess.printResult();
		return;
	}
}
