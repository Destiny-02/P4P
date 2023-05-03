package payroll;
public enum PAYETax {
	RATE1(.105, 14000, 0), RATE2(.175, 48000, 14000), RATE3(.30, 70000, 48000), RATE4(.33, Double.POSITIVE_INFINITY,
			70000);
	private final double rate;
	private final double income;
	private final double maxTax;
	private PAYETax(double rate, double income, double prevIncomeTotal) {
		this.rate = rate;
		this.income = income;
		this.maxTax = Math.round(rate * (income - prevIncomeTotal) * 100.0) / 100.0;
	}
	public static double calculatePAYE(double gross) {
		double yearIncome = gross * 52.0;
		double PAYE;
		if (yearIncome <= RATE1.income) {
			PAYE = Math.round(RATE1.rate * yearIncome * 100.0) / 100.0;
		} else if (yearIncome <= RATE2.income) {
			PAYE = RATE1.maxTax;
			PAYE += Math.round(RATE2.rate * (yearIncome - RATE1.income) * 100.0) / 100.0;
		} else if (yearIncome <= RATE3.income) {
			PAYE = RATE1.maxTax + RATE2.maxTax;
			PAYE += Math.round(RATE3.rate * (yearIncome - RATE2.income) * 100.0) / 100.0;
		} else {
			PAYE = RATE1.maxTax + RATE2.maxTax + RATE3.maxTax;
			PAYE += Math.round(RATE4.rate * (yearIncome - RATE3.income) * 100.0) / 100.0;
		}
		PAYE = Math.round((PAYE / 52.0) * 100.0) / 100.0;
		return PAYE;
	}
}
