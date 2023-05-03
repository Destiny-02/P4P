package payroll;
public class Calculation {
	final double overTime40 = 1.5;
	final double overTime43 = 2;
	final double year = 52;
	public double checkEmployment(Employee employee, Calculation calculate) {
		double gross = 0.0;
		if (employee.getEmployment().equals("Hourly")) {
			gross = CalcHourlyGross(employee);
		}
		else if (employee.getEmployment().equals("Salaried")) {
			gross = CalcSalariedGross(employee);
		}
		return gross;
	}
	public double CalcHourlyGross(Employee employee) {
		double rate = Double.parseDouble(employee.getRate());
		double hours = Double.parseDouble(employee.getWorkHours());
		double gross = 0.0;;
		if (hours <= 40) {
			gross = rate * hours;
		}
		else if (hours <= 43 && hours > 40) {
			gross = (rate * 40 + rate * overTime40 * (hours - 40));
		}
		else if (hours > 43) {
			double overTimeCalc = rate * 40 + rate * overTime40 * 3;
			gross = (overTimeCalc + rate * overTime43 * (hours - 43));
		}
		return gross;
	}
	public double CalcSalariedGross(Employee employee) {
		double rate = Double.parseDouble(employee.getRate());
		double gross = rate / 52;
		return gross;
	}
	final double firstRate = 0.105;
	final double secondRate = 0.175;
	final double thirdRate = 0.3;
	final double lastRate = 0.33;
	final int betweenIncome1 = 34000;
	final int betweenIncome2 = 22000;
	public double CalcTax(Employee employee, Double gross) {
		double tax = 0.0;
		gross = gross * 52;
		if (gross > 70000) {
			tax = firstRate*14000 + secondRate*betweenIncome1
					+ thirdRate*betweenIncome2 + (gross - 70000)*lastRate;
		}
		else if (gross > 48000) {
			tax = firstRate*14000 + secondRate*betweenIncome1
					+ thirdRate*(gross - 48000);
			}
		else if (gross > 14000) {
			tax = firstRate*14000 + secondRate*(gross - 14000);
		}
		else if (gross < 14000) {
			tax = firstRate*gross;
		}
		tax = tax / 52;
		return tax;
	}
	public double CalcNett(Employee employee, double gross, double tax) {
		double deduction = Double.parseDouble(employee.getDeduction());
		double Nett = gross - (tax + deduction);
		return Nett;
	}
	public double CalcYTD(Employee employee, double gross) {
		double oldYTD = Double.parseDouble(employee.getYTD());
		double YTD = oldYTD + gross;
		return YTD;
	}
}
