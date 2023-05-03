
package payroll.employees.salary;
import payroll.PayrollException;
public class HourlyPay extends Pay{
	private final float _rate;
	private final float _ytd;
	private final float _hours;
	private final float _deduction;
	public HourlyPay(String[] arr) throws PayrollException {
		_rate = Float.parseFloat(arr[4]);
		_ytd = Float.parseFloat(arr[5]);
		_hours = Float.parseFloat(arr[8]);
		_deduction = Float.parseFloat(arr[9]);
		switch ((int)(_hours % 1 * 100)) {
		case 25: case 50: case 75: case 0: break;
		default: throw new PayrollException("hours", Float.toString(_hours), "Must be rounded to the nearest quarter hour(.0, .25, .5, .75)");
		}
	}
	public float gross() throws PayrollException {
		float gross;
		if (_rate < 0) {
			throw new PayrollException("rate", Float.toString(_rate), "Negative number");
		} else if (_hours < 0) {
			throw new PayrollException("hours", Float.toString(_hours), "Negative number");
		} else if (_hours <= 40) {
			gross = _rate * _hours;
		} else if (_hours <= 43) {
			gross = (_rate * 40) + (_rate * (_hours - 40) * (float)1.5);
		} else {
			gross = (_rate * 40) + (_rate * (float)1.5 * 3) + (_rate * 2 * (_hours - 43));
		}
		return gross;
	}
	public float paye() throws PayrollException {
		return payeCalculations(gross() * 52);
	}
	public String employeeStatement() throws PayrollException {
		String employment = "Hourly, $" + df.format(_rate) + " YTD:$" + df.format(ytd());
		return employment;
	}
	protected float deduction() {
		return _deduction;
	}
	protected float ytd() throws PayrollException {
		return _ytd + gross();
	}
}
