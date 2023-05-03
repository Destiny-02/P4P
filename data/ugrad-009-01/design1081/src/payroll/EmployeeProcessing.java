package payroll;
public class EmployeeProcessing {
	public double calcGross(String employment, double rate, double hours) {
		double wages = 0.00;
		if (employment.compareTo("Hourly") == 0) {
			if (hours > 43.0) {
				wages = (40.0 * rate) + (3 * rate * 1.5) + ((hours - 43.0) * 2 * rate);
			} else if (hours > 40.0) {
				wages = (40.0 * rate) + ((hours - 40.0) * 1.5 * rate);
			} else {
				wages = hours * rate;
			}
		} else if (employment.compareTo("Salaried") == 0) {
			wages =  rate/52;
		} else {
			throw new ProcessingException("Invalid entry for 'employment'. 'Hourly' and 'Salaried' only.");
		}
		wages = Math.round(wages * 100);
		return wages/100;
	}
	public double calcPaye(double weeklyGross) {
		double yearlyGross = weeklyGross * 52;
		double paye = 0;
		while (yearlyGross > 0) {
			if (yearlyGross > 70000) {
				paye = (yearlyGross - 70000) * 0.33;
				yearlyGross = 70000;
			} else if (yearlyGross > 48000) {
				paye = paye + (yearlyGross - 48000) * 0.3;
				yearlyGross = 48000;
			} else if (yearlyGross > 14000) {
				paye += (yearlyGross - 14000) * 0.175;
				yearlyGross = 14000;
			} else {
				paye += yearlyGross * 0.105;
				yearlyGross = 0;
			}
		}
		paye = paye/52;
		paye = Math.round(paye * 100);
		return paye/100;
	}
	public double calcNett(double gross, double paye, double deduct) {
		double netPay = gross - paye - deduct;
		if (netPay < 0) {
			throw new ProcessingException("Record invalid. Net pay cannot be a negative value");
		} else {
			return netPay;
		}
	}
}
