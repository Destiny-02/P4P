package payroll;
public class PAYEFormat extends Formatting {
	public String formatTotalPAYE(double paye) {
		String payeString = "Total PAYE: $" + twoDP(paye);
		return payeString;
	}
	public void printPAYE(EmployeeInfo[] employeeData) {
		System.out.println(todaysDate());
		System.out.println(periodFormat(employeeData[0].getStartDate(),employeeData[0].getEndDate()));
		TotalProcess TP = new TotalProcess();
		double paye = TP.totalPAYE(employeeData);
		System.out.println(formatTotalPAYE(paye));
	}
}
