package payroll;
public class SalariedEmployee extends Employee {
	public SalariedEmployee(int TID, String name, double rate, double YTD, String[] dates, double hours, double deduction) {
		super(TID, name, rate, YTD, dates, hours, deduction);
	}
	public double getGrossValue() {
		double gross = _rate/52;
		return roundNearestCent(gross);
	}
}
