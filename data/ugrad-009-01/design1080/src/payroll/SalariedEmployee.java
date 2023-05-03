package payroll;
public class SalariedEmployee extends Employee {
	private double _gross;
	private double _rate;
	private double _paye;
	private final String _type = "Salaried";
	public SalariedEmployee(String details) throws PayrollException {
		super(details);
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
		_gross = _rate / 52;
	}
	@Override
	public double getGross() {
		return _gross;
	}
	private void calcPaye() {
		_paye = ((_rate <= 14000) ? (0.105 * _gross) :
			((_rate > 14000) && (_rate <= 48000)) ? ((14000 * 0.105) + ((_rate - 14000) * 0.175)) :
			((_rate > 48000) && (_rate <= 72000)) ? ((14000 * 0.105) + ((34000) * 0.175) + (_rate - 48000) * 0.3) :
			((14000 * 0.105) + ((34000) * 0.175) + (22000) * 0.3 + (_rate - 70000) * 0.33)) / 52;
	}
	@Override
	public double getPaye() {
		return _paye;
	}
}
