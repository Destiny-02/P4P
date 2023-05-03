package payroll;
public class SalaryCalculations implements Calculations {
	public double grossAmount(double hours, double rate) {
		double gross;
		gross = rate/ 52;
		gross = Math.round(gross * 100);
		gross = gross / 100;
		return gross;
	}
	public double incomeTax(double gross, double rate) {
		double incomeTax;
		if (rate <= 14000) {
		incomeTax = rate	* .105;
		} else if (rate > 14000 && rate <= 48000) {
			incomeTax = 14000 * .105 + (rate - 14000) * .175;
		} else if (rate > 48000 && rate <= 70000) {
			incomeTax= 14000 * .105 + 34000 * .175 + (rate - 48000) * .3;
		} else {
			incomeTax= 14000 * .105 + 34000 * .175 + 22000 * .3 + (rate - 70000) * .33;
		}
		incomeTax = incomeTax / 52;
		incomeTax = Math.round(incomeTax * 100);
		incomeTax = incomeTax / 100;
		return incomeTax;
	}
	public double nettDeductions(double gross, double deductions, double incomeTax) {
		double nett;
		nett = gross - deductions - incomeTax;
		return nett;
	}
	public double ytdCalculation(double gross, double ytd) {
		ytd = ytd + gross;
		return ytd;
	}
}
