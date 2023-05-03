
package payroll;
public class ProcessTax {
	private static final double TAX_UNDER_14K = 0.105;
	private static final double TAX_14K_TO_48K = 0.175;
	private static final double TAX_48K_TO_70K = 0.30;
	private static final double TAX_OVER_70K = 0.33;
	public double calculateSalariedTax(double rate){
		double tax=0;
		if (rate >= 14000){
			tax += Payroll.round2DP(14000 * TAX_UNDER_14K);
		} else {
			tax += Payroll.round2DP((rate) * TAX_UNDER_14K);
			return tax/Payroll.WEEKS_IN_A_YEAR;
		}
		if (rate >= 48000){
			tax += Payroll.round2DP(34000 * TAX_14K_TO_48K);
		} else {
			tax += Payroll.round2DP((rate-14000) * TAX_14K_TO_48K);
			return tax/Payroll.WEEKS_IN_A_YEAR;
		}
		if (rate >= 70000){
			tax += Payroll.round2DP(22000 * TAX_48K_TO_70K);
		} else{
			tax += Payroll.round2DP((rate-48000) * TAX_48K_TO_70K);
			return tax/Payroll.WEEKS_IN_A_YEAR;
		}
		tax += Payroll.round2DP((rate-70000)* TAX_OVER_70K);
		return tax/Payroll.WEEKS_IN_A_YEAR;
	}
	public double calculateHourlyTax(double grossWeeklyIncome){
		double yearlyIncome = Payroll.WEEKS_IN_A_YEAR * grossWeeklyIncome;
		return calculateSalariedTax(yearlyIncome);
	}
}
