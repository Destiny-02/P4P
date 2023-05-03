
package payroll;
public class SalariedEmployee extends AbstractEmployee{
	private double payAsYouEarn;
	public SalariedEmployee(int taxID, Name name, double rate,
			double ytd, Date dates, double hours, double deduction) {
		super(taxID, name, rate, ytd, dates, hours, deduction);
		this.payAsYouEarn = calculatePAYE(rate);
	}
	@Override
	public double getPayPerPeriod() {
		return getRate() / 52;
	}
	@Override
	double getPAYE() {
		return this.payAsYouEarn;
	}
	@Override
	double getNett() {
		return this.getPayPerPeriod() - this.getPAYE() - getDeduction();
	}
}
