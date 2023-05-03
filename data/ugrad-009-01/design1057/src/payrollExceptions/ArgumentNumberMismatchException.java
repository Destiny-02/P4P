package payrollExceptions;
public class ArgumentNumberMismatchException extends RuntimeException {
	private static final long serialVersionUID = -2825331163605276511L;
	public ArgumentNumberMismatchException(String string) {
		super(string);
	}
}
