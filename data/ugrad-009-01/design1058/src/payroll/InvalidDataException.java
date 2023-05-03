package payroll;
public class InvalidDataException extends Exception {
	private static final long serialVersionUID = 1L;
	public InvalidDataException(String msg) {
		super(msg);
	}
}
