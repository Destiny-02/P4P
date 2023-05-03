package payroll;
public class PayrollDateFormatException extends Exception {
	private static final long serialVersionUID = 1L;
	private String _date;
	public PayrollDateFormatException(String date) {
		_date = date;
	}
	@Override
	public String getMessage() {
		return (_date + " (Invalid date)");
	}
}
