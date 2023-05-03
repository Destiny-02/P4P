package payroll;
public class PayrollExceptions extends Exception {
	private static final long serialVersionUID = 1L;
	public PayrollExceptions()
	{
	}
	public PayrollExceptions(String message)
	{
		super(message);
	}
	public PayrollExceptions(Throwable cause)
	{
		super(cause);
	}
	public PayrollExceptions(String message, Throwable cause)
	{
		super(message, cause);
	}
	public PayrollExceptions(String message, Throwable cause,boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}