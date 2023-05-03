package payroll;
import payroll.Command;
import payroll.BurdenCommand;
import payroll.EmployeesCommand;
import payroll.PAYECommand;
import payroll.PayslipsCommand;
import payroll.InvalidIOException;
public class UserCommand {
	private final String PAYSLIPS = "Payslips";
	private final String EMPLOYEES = "Employees";
	private final String BURDEN = "Burden";
	private final String PAYE = "PAYE";
	public Command commandResponse(String process) throws InvalidIOException {
		if (process.equals(PAYSLIPS)) {
			return new PayslipsCommand();
		} else if (process.equals(EMPLOYEES)) {
			return new EmployeesCommand();
		} else if (process.equals(BURDEN)) {
			return new BurdenCommand();
		} else if (process.equals(PAYE)) {
			return new PAYECommand();
		} else {
			throw new InvalidIOException("Invalid command : " + process);
		}
	}
}
