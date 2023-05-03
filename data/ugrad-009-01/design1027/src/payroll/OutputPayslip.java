package payroll;
public class OutputPayslip {
	OutputPayslip (Employee currentEmployee, double[] payslipDetails) {
		printOutput(currentEmployee, payslipDetails);
	}
	private void printOutput(Employee currentEmployee, double[] payslipDetails) {
		String name = rename(currentEmployee.getName());
		System.out.println(String.format("%d. %s, Period: %s to %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f",
			currentEmployee.getTaxID(),
			name,
			currentEmployee.getStartDate(),
			currentEmployee.getEndDate(),
			payslipDetails[0],
			payslipDetails[1],
			payslipDetails[2],
			payslipDetails[3],
			payslipDetails[4]
		));
	}
	private String rename(String name) {
		String[] tempName = name.replace(",", "").split(" ");
		return tempName[1] + " " + tempName[0];
	}
}