package payroll;
public class PayrollUserException extends Exception {
	private static final long serialVersionUID = 1L;
	PayrollUserException(String msg){
		super(msg);
	}
}
