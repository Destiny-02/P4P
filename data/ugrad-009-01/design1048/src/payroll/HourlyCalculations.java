package payroll;
public class HourlyCalculations implements Calculations {
	public double grossAmount(double hours, double rate) {
		double gross;
		if (hours <= 40) {
			gross = hours * rate;
		} else if (hours > 40 && hours <= 43) {
			gross = 40 * rate + (hours - 40) * 1.5 * rate;
		} else {
			gross = 40 * rate + 3 * 1.5 *rate + (hours - 43) * rate * 2;
		}
		gross = Math.round(gross * 100);
		gross = gross / 100;
		return gross;
	}
	public double incomeTax(double gross, double rate) {
		double incomeTax = 0;
		double income = gross * 52;
		if (income <= 14000) {
		incomeTax = income	* .105;
		} else if (income > 14000 && income <= 48000) {
			incomeTax = 14000 * .105 + (income - 14000) * .175;
		} else if (income > 48000 && income <= 70000) {
			incomeTax= 14000 * .105 + 34000 * .175 + (income - 48000) * .3;
		} else if (income > 70000 ) {
			incomeTax= 14000 * .105 + 34000 * .175 + 22000 * .3 + (income - 70000) * .33;
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
