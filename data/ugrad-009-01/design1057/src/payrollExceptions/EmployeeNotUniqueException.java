package payrollExceptions;
public class EmployeeNotUniqueException extends Exception {
	private static final long serialVersionUID = -9137836272911515626L;
	public EmployeeNotUniqueException (String msg) {
		super(msg);
	}
}
