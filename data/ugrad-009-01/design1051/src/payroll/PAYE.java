package payroll;
public class PAYE implements Display {
	public void display(EmployeeList list) {
		Display.printCurrentDate();
		System.out.println(list.returnPeriod());
		String totalPAYE = String.format("Total PAYE: %.2f", list.returnRequiredTotal("PAYE"));
		System.out.print(totalPAYE);
	}
}
