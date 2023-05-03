package payroll;
public interface Calculations {
	public double grossAmount(double hours, double rate);
	public double incomeTax(double gross, double rate);
	public double nettDeductions(double gross, double deductions, double incomeTax);
	public double ytdCalculation(double gross, double ytd);
}
