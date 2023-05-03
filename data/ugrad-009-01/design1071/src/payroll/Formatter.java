package payroll;
public class Formatter {
	public String formatPayslipEntry(int id, String firstName, String lastName, String start, String end, double weekly, double paye, double deduction, double nett, double newYtd){
		return String.format("%d. %s %s, Period: %s to %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f\n",
				id, firstName, lastName, start, end, weekly, paye, deduction, nett, newYtd);
	}
	public String formatEmployees(String lastName, String firstName, int id, String employment, double rate, double newYtd){
		return String.format("%s, %s (%d) %s, $%.2f YTD:$%.2f\n", lastName, firstName, id, employment, rate, newYtd);
	}
}
