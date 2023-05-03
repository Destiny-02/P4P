package payroll;
public class Finances {
	final private double UP_TO_14K = 0.105;
	final private double BETWEEN_14_AND_48K = 0.175;
	final private double BETWEEN_48_AND_70K = 0.3;
	final private double OVER_70K = 0.33;
	final private int INCOME_TIER_1 = 1400000;
	final private int INCOME_TIER_2 = 4800000;
	final private int INCOME_TIER_3 = 7000000;
	final private int WEEKS_IN_YEAR = 52;
	final private double OVER_TIME = 1.5;
	public int calcPaye(int weekIncome) {
		int annualGross = weekIncome * WEEKS_IN_YEAR;
		int paye = 0;
		if (annualGross > INCOME_TIER_3) {
			paye = (int) Math.round((annualGross - INCOME_TIER_3) * OVER_70K);
			paye += (int) Math.round((INCOME_TIER_3 - INCOME_TIER_2)
					* BETWEEN_48_AND_70K);
			paye += (int) Math.round((INCOME_TIER_2 - INCOME_TIER_1)
					* BETWEEN_14_AND_48K);
			paye += (int) Math.round(INCOME_TIER_1 * UP_TO_14K);
		} else if (annualGross > INCOME_TIER_2) {
			paye += (int) Math.round((annualGross - INCOME_TIER_2)
					* BETWEEN_48_AND_70K);
			paye += (int) Math.round((INCOME_TIER_2 - INCOME_TIER_1)
					* BETWEEN_14_AND_48K);
			paye += (int) Math.round(INCOME_TIER_1 * UP_TO_14K);
		} else if (annualGross > INCOME_TIER_1) {
			paye += (int) Math.round((annualGross - INCOME_TIER_1)
					* BETWEEN_14_AND_48K);
			paye += (int) Math.round(INCOME_TIER_1 * UP_TO_14K);
		} else {
			paye = (int) Math.round(annualGross * UP_TO_14K);
		}
		return (int) Math.round((double) paye / WEEKS_IN_YEAR);
	}
	public int calcGrossIncome(boolean salaried, double rate, double hours) {
		double income = 0;
		if (salaried) {
			income = rate / 52;
		} else {
			if (hours <= 40) {
				income = rate * hours;
			} else {
				income = (hours - 40) * rate * OVER_TIME + rate * 40;
			}
		}
		return (int) Math.round(income);
	}
	public String currencyFormat(int money) {
		String str = String.format("%.2f", ((double) money) / 100);
		return "$" + str;
	}
}
