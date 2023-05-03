package payroll;
public class OutputPAYE {
	OutputPAYE (double totalPAYE, Employee currentEmployee) {
		System.out.println(String.format("%s to %s", currentEmployee.getStartDate(), currentEmployee.getEndDate()));
		System.out.println(String.format("Total PAYE: $%.2f", totalPAYE));
	}
}