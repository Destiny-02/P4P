package payrollExceptions;
public class InvalidDataException extends Exception {
	private static final long serialVersionUID = 87758362640101773L;
	public InvalidDataException(String string) {
		super(string);
	}
}
