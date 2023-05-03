package payrollExceptions;
public class ArgumentInvalidException extends RuntimeException {
	private static final long serialVersionUID = 8644522074534969676L;
	public ArgumentInvalidException() {
	}
	public ArgumentInvalidException(String msg) {
		super(msg);
	}
}
