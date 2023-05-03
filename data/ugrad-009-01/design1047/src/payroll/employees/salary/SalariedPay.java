
package payroll.employees.salary;
import payroll.PayrollException;
public class SalariedPay extends Pay{
	private final float _rate;
	private final float _ytd;
	private final float _hours;
	private final float _deduction;
	public SalariedPay(String[] arr) throws PayrollException {
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
		gross = _rate / 52;
		return gross;
	}
	public float paye() throws PayrollException {
		return super.payeCalculations(_rate);
	}
	public String employeeStatement() throws PayrollException {
		String employment = "Salaried, $" + df.format(_rate) + " YTD:$" + df.format(ytd());
		return employment;
	}
	protected float deduction() {
		return _deduction;
	}
	protected float ytd() throws PayrollException {
		return _ytd + gross();
	}
}
