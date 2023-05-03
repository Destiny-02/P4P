package payroll;
public abstract class AbstractEmployee {
	private int taxID;
	private Name name;
	private double rate;
	private double ytd;
	private Date dates;
	private double hours;
	private double deduction;
	public AbstractEmployee(int taxID, Name name,
			double rate, double ytd, Date dates, double hours,
			double deduction) {
		this.taxID = taxID;
		this.name = name;
		this.rate = rate;
		this.ytd = ytd;
		this.dates = dates;
		this.hours = hours;
		this.deduction = deduction;
	}
	public int getTaxID() {
		return taxID;
	}
	public Name getName() {
		return name;
	}
	public double getRate() {
		return rate;
	}
	public double getYtd() {
		return ytd;
	}
	public Date getDates() {
		return dates;
	}
	public double getHours() {
		return hours;
	}
	public double getDeduction() {
		return deduction;
	}
	public static double calculatePAYE(double yearlyIncome) {
		double totalTax = 0;
		if(yearlyIncome <= 14000.00) {
			totalTax = yearlyIncome * 0.105;
		}
		else if(yearlyIncome <= 48000.00) {
			totalTax = 1470 + (yearlyIncome - 14000.00) * 0.175;
		}
		else if(yearlyIncome <= 70000.00) {
			totalTax = 7420 + (yearlyIncome - 48000.00) * 0.3;
		}
		else {
			totalTax = 14020 + (yearlyIncome - 70000.00) * 0.33;
		}
		return totalTax / 52;
	}
	abstract double getPayPerPeriod();
	abstract double getPAYE();
	abstract double getNett();
}
