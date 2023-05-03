
package payroll;
import payrollExceptions.ArgumentNumberMismatchException;
public class CommandParser {
	private String fileString;
	private String processingString;
	public CommandParser(String[] commands) {
		if(commands.length != 2) {
			throw new ArgumentNumberMismatchException("Number of argument is "
					+ commands.length + "\nPlease specify 2 arugments.");
		}
		this.fileString = commands[0];
		this.processingString = commands[1];
	}
	public String getFileString() {
		return fileString;
	}
	public String getProcessingString() {
		return processingString;
	}
}
