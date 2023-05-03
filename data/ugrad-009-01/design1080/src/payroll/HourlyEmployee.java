package payroll;
public class HourlyEmployee extends Employee{
	private double _gross;
	private double _annualGross;
	private double _hours;
	private double _rate;
	private double _paye;
	private final String _type = "Hourly";
	public HourlyEmployee(String details) throws PayrollException {
		super(details);
		_hours = getHours();
		_rate = getRate();
		calcGross();
		_gross = round(_gross);
		calcPaye();
		_paye = round(_paye);
		super.setCalculatedFields();
	}
	@Override
	public String getType() {
		return _type;
	}
	private void calcGross() {
		_gross = (_hours <= 40) ? _hours * _rate :
		((_hours <= 43) && (_hours >= 40)) ? (40 * _rate + (_hours - 40) * _rate * 1.5) :
		(40 * _rate + (3) * _rate * 1.5 + (_hours - 43) * _rate * 2);
	}
	private void calcPaye() {
		_annualGross = _gross * 52;
		_paye = ((_annualGross <= 14000) ? (0.105 * _gross) :
				((_annualGross > 14000) && (_annualGross <= 48000)) ? ((14000 * 0.105) + ((_annualGross - 14000) * 0.175)) :
				((_annualGross > 48000) && (_annualGross <= 72000)) ? ((14000 * 0.105) + ((34000) * 0.175) + (_annualGross - 48000) * 0.3) :
				((14000 * 0.105) + ((34000) * 0.175) + (22000) * 0.3 + (_annualGross - 70000) * 0.33)) / 52;
	}
	@Override
	public double getPaye() {
		return _paye;
	}
	@Override
	public double getGross() {
		return _gross;
	}
}
