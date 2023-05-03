
package payroll;
public class HourlyEmployee extends Employee {
	public HourlyEmployee(int taxID, String familyName, String givenName, String employment, double YTD, double rate, String startDate, String endDate,
			double hours, double deductions) {
		super(taxID, familyName, givenName, employment, YTD, rate, startDate, endDate, hours, deductions);
	}
	public double calculateGrossWeeklyIncome(double rate, double hours) {
		double gross = 0;
		if (hours >= 40){
			gross += 40*rate;
		} else {
			return Payroll.round2DP(rate*hours);
		}
		if (hours >= 43){
			gross += 3 * rate * 1.5 + (hours - 43) * 2 * rate;
		} else {
			gross += (hours - 40) * 1.5 * rate;
		}
		return Payroll.round2DP(gross);
	}
}
