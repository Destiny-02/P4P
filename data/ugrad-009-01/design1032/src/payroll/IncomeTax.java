package payroll;
public class IncomeTax {
	public static double calculateTax(double gross, boolean isSalaried) {
		double annualTax = 0;
		double annualGross = gross * 52;
		double[][] taxRates = {{14000, 0.105}, {34000, 0.175}, {22000, 0.3}, {10000000, 0.33}};
		for (int index = 0; index < taxRates.length; index++) {
			if ((annualGross/taxRates[index][0]) >= 1) {
				annualTax += taxRates[index][0] * taxRates[index][1];
				annualGross -= taxRates[index][0];
			}
			else {
				annualTax += taxRates[index][1] * annualGross;
				annualGross = 0;
			}
		}
		return annualTax/52;
	}
}
