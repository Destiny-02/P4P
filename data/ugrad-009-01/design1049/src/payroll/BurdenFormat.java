package payroll;
public class BurdenFormat extends Formatting{
	public String formatBurden(double burden) {
		String burdenString = "Total Burden: $" + twoDP(burden);
		return burdenString;
	}
	public void printBurden(EmployeeInfo[] employeeData) {
		System.out.println(todaysDate());
		System.out.println(periodFormat(employeeData[0].getStartDate(),employeeData[0].getEndDate()));
		TotalProcess TP = new TotalProcess();
		double burden = TP.totalBurden(employeeData);
		System.out.println(formatBurden(burden));
	}
}
