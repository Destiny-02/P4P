package payroll;
import java.io.FileNotFoundException;
public class Payroll {
	public static void main(String[] args) throws FileNotFoundException {
		EmployeeDatabase employeelist = new EmployeeDatabase(args[0]);
		Output output = new Output(employeelist);
		if (args[1].equals("Employees")) {
			output.EmployeeListOutput(employeelist);
		} else if (args[1].equals("Burden")) {
			output.BurdenOutput(employeelist);
		} else if (args[1].equals("PAYE")) {
			output.PAYEOutput(employeelist);
		} else if (args[1].equals("Payslips")) {
			output.PayslipOutput(employeelist);
		} else {
			System.out
					.println("=======ERROR: Processing type input is invalid=======");
			System.out.println("Attempting to produce payslip...");
			output.PayslipOutput(employeelist);
		}
	}
}
