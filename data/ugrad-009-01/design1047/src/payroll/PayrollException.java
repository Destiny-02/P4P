package payroll;
@SuppressWarnings("serial")
public class PayrollException extends Exception {
	private final String _field;
	private final String _value;
	private final String _problem;
	public PayrollException(String field, String value, String problem) {
		_field = field;
		_value = value;
		_problem = problem;
	}
	public PayrollException(String message) {
		super(message);
		_field = null; _value = null; _problem = null;
	}
	public PayrollException(int tid, String field, String value, String problem) {
		super("Employee TID:" + tid + " has an invalid value(\'" + value + "\') with \'" + field + "\': " + problem);
		_field = field;
		_value = value;
		_problem = problem;
	}
	public PayrollException(int tid, String[] data) {
		super("Employee TID:" + tid + " has an invalid value(\'" + data[1] + "\') with \'" + data[0] + "\': " + data[2]);
		_field = data[0];
		_value = data[1];
		_problem = data[2];
	}
	public String field() {
		return _field;
	}
	public String value() {
		return _value;
	}
	public String problem() {
		return _problem;
	}
	public String[] data() {
		return new String[]{_field, _value, _problem};
	}
}
