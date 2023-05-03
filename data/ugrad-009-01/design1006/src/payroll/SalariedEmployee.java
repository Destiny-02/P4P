
package payroll;
public class SalariedEmployee extends Employee {
	public SalariedEmployee(int taxID, String familyName, String givenName, String employment, double rate, double YTD, String startDate, String endDate,
			double hours, double deductions) {
		super(taxID, familyName, givenName, employment, rate, YTD, startDate, endDate, hours, deductions);
	}
	public double calculateGrossWeeklyIncome(double rate, double hours) {
		return rate/Payroll.WEEKS_IN_A_YEAR;
	}
}
