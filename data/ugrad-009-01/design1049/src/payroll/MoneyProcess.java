package payroll;
public class MoneyProcess {
	public double calcGrossSalaried(double rate) {
		double pay = rate/52.0;
		pay = Math.round(pay * 100.0) / 100.0;
		return pay;
	}
	public double calcGrossHourly(double rate, double hours) {
		if (hours <= 40) {
			double pay = hours * rate;
			pay = Math.round(pay * 100.0) / 100.0;
			return pay;
		} else if ((hours > 40) && (hours <= 43)) {
			double pay = rate * 40.0;
			hours = hours - 40.0;
			pay = pay + (hours * rate * 1.5);
			pay = Math.round(pay * 100.0) / 100.0;
			return pay;
		} else {
			double pay = rate * 40.0;
			pay = pay + 3.0 * rate * 1.5;
			hours = hours - 43.0;
			pay = pay + (hours * rate * 2.0);
			pay = Math.round(pay * 100.0) / 100.0;
			return pay;
		}
	}
	public double hourlyAnnual(double grossHourly) {
		return grossHourly * 52;
	}
	public double payPeriodTax(double annualPay) {
		double tax;
		if (annualPay <= 14000) {
			tax = annualPay * 0.105;
			tax = Math.round(tax * 100.0) / 100.0;
			return tax / 52.0;
		} else if ((annualPay > 14000) && (annualPay <= 48000)) {
			tax = 14000.0 * 0.105;
			annualPay = annualPay - 14000.0;
			tax = tax + (annualPay * 0.175);
			tax = Math.round(tax * 100.0) / 100.0;
			return tax / 52.0;
		} else if ((annualPay > 48000) && (annualPay <= 70000)) {
			tax = 14000.0 * 0.105 + (48000.0 - 14000.0) * 0.175;
			annualPay = annualPay - 48000.0;
			tax = tax + annualPay * 0.3;
			tax = Math.round(tax * 100.0) / 100.0;
			return tax / 52.0;
		} else {
			tax = 14000.0 * 0.105 + (48000.0 - 14000.0) * 0.175 + (70000.0 - 48000) * 0.3;
			annualPay = annualPay - 70000.0;
			tax = tax + annualPay * 0.33;
			tax = Math.round(tax * 100.0) / 100.0;
			return tax  / 52.0;
		}
	}
	public double calcNett(double gross, double deductions) {
		return gross - deductions;
	}
	public double calcYTD(double nett, double ytd) {
		ytd = ytd + nett;
		return ytd;
	}
}
