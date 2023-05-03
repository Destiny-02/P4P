package payroll;
import payroll.Command;
import payroll.Information;
import payroll.InvalidIOException;
import payroll.UserCommand;
import java.io.FileNotFoundException;
public class Payroll  {
	public static void main(String[] args) throws FileNotFoundException, InvalidIOException {
		Information insert = new Information();
		insert.Insert(args[0]);
		UserCommand command = new UserCommand();
		Command cmd = command.commandResponse(args[1]);
		cmd.operation(insert);
	}
}
