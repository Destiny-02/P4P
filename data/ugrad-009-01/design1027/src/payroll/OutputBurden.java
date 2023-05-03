package payroll;
public class OutputBurden {
	OutputBurden (double totalBurden, Employee currentEmployee) {
		System.out.println(String.format("%s to %s", currentEmployee.getStartDate(), currentEmployee.getEndDate()));
		System.out.println(String.format("Total Burden: $%.2f", totalBurden));
	}
}
