package payroll;
@SuppressWarnings("serial")
public class InputException extends Exception {
	public InputException(String message) {
		super("Error: " + message);
	}
}
