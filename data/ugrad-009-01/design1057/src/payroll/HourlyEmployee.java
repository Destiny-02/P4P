package payroll;
public class HourlyEmployee extends AbstractEmployee{
	private double payPerPeriod;
	private double payAsYouEarn;
	public HourlyEmployee(int taxID, Name name, double rate,
			double ytd, Date dates, double hours, double deduction) {
		super(taxID, name, rate, ytd, dates, hours, deduction);
		this.payPerPeriod = this.calculatePayPerPeriod();
		this.payAsYouEarn = calculatePAYE(this.payPerPeriod * 52);
	}
	private double calculatePayPerPeriod() {
		double payPerPeriod = getRate() * getHours();
		if(getHours() > 40) {
			payPerPeriod += getRate() * (getHours() - 40) * 0.5;
		}
		if(getHours() > 43) {
			payPerPeriod += getRate() * (getHours() - 43);
		}
		return payPerPeriod;
	}
	@Override
	public double getPAYE() {
		return this.payAsYouEarn;
	}
	@Override
	public double getNett() {
		return this.payPerPeriod - this.getPAYE() - getDeduction();
	}
	@Override
	public double getPayPerPeriod() {
		return this.payPerPeriod;
	}
}
