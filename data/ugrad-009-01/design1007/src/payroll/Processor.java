package payroll;
public class Processor {
	private double _weeksInAYear = 52;
	public Processor() {
	}
	public double calculateGross(double rate, double hoursWorked, String employment) {
		double gross;
		double normalHours, overtimeHours, doubletimeHours;
		double normalRate, overtimeRate, doubletimeRate;
		normalHours = 40.0;
		overtimeHours = 3.0;
		doubletimeHours = 0;
		normalRate = rate;
		overtimeRate = rate * 1.5;
		doubletimeRate = rate * 2.0;
		if (employment.equals("Salaried")) {
			gross = rate/_weeksInAYear;
			gross = round(gross);
		}
		else {
			if (hoursWorked <= normalHours) {
				normalHours = hoursWorked;
				overtimeHours = 0;
				doubletimeHours = 0;
			} else if (hoursWorked > normalHours && hoursWorked <= (normalHours +overtimeHours)) {
				overtimeHours = hoursWorked - normalHours;
				doubletimeHours = 0;
			} else {
				doubletimeHours = hoursWorked - (normalHours + overtimeHours);
			}
			gross = (normalRate * normalHours) + (overtimeRate * overtimeHours) + (doubletimeRate * doubletimeHours);
			gross = round(gross);
		}
		return gross;
	}
	public double calculatePAYE (double gross) {
		double firstTaxIncome, secondTaxIncome, thirdTaxIncome, fourthTaxIncome;
		double firstTaxRate, secondTaxRate, thirdTaxRate, fourthTaxRate;
		double tax;
		double annualGross = gross * _weeksInAYear;
		firstTaxIncome = 14000.0;
		secondTaxIncome = 48000.0;
		thirdTaxIncome = 70000.0;
		fourthTaxIncome = 0;
		firstTaxRate = 0.105;
		secondTaxRate = 0.175;
		thirdTaxRate = 0.3;
		fourthTaxRate = 0.33;
		if (annualGross <= firstTaxIncome) {
			thirdTaxIncome = 0;
			secondTaxIncome = 0;
			firstTaxIncome = annualGross;
		} else if (annualGross > firstTaxIncome && annualGross <= secondTaxIncome) {
			thirdTaxIncome = 0;
			secondTaxIncome = annualGross - firstTaxIncome;
		} else if (annualGross > secondTaxIncome && annualGross <= thirdTaxIncome) {
			thirdTaxIncome = annualGross - secondTaxIncome;
			secondTaxIncome -= firstTaxIncome;
		} else {
			fourthTaxIncome = annualGross - thirdTaxIncome;
			thirdTaxIncome -= secondTaxIncome;
			secondTaxIncome -= firstTaxIncome;
		}
		tax = round((firstTaxIncome * firstTaxRate) + (secondTaxIncome * secondTaxRate) + (thirdTaxIncome * thirdTaxRate) + (fourthTaxIncome * fourthTaxRate));
		tax = round(tax / _weeksInAYear);
		return tax;
	}
	public double calculateNett(double gross, double tax, double deductions) {
		double nett;
		nett = gross - tax - deductions;
		return nett;
	}
	public double calculateYTD(double yearToDate, double gross) {
		double newYTD;
		newYTD = yearToDate + gross;
		return newYTD;
	}
	private double round(double value) {
		return Math.round(value * 100.00) / 100.00;
	}
}
