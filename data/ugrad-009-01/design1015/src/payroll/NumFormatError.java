package payroll;
public class NumFormatError extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public NumFormatError(String message){
		super(message);
	}
}
