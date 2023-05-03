package payroll;
public class InvalidEmployeeRecordException extends Exception {
	private static final long serialVersionUID = 5291998399550896731L;
	public InvalidEmployeeRecordException(String message){
		super(message);
	}
}
