package payroll;
public class HourlyEmployee extends Employee{
	public HourlyEmployee(int TID, String name, double rate, double YTD, String[] dates, double hours, double deduction) {
		super(TID, name, rate, YTD, dates, hours, deduction);
	}
	public double getGrossValue() {
		double gross;
		if (_hours <= 40) {
			gross = _rate*_hours;
		} else if (_hours <= 43) {
			gross = (_rate*40);
			gross += (_hours - 40) * (_rate * 1.5);
		} else {
			gross = _rate*40;
			gross += 3 * _rate * 1.5;
			gross += (_hours - 43) * (_rate * 2);
		}
		return roundNearestCent(gross);
	}
}
