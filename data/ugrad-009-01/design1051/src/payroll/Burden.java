package payroll;
public class Burden implements Display {
	public void display(EmployeeList list) {
		Display.printCurrentDate();
		System.out.println(list.returnPeriod());
		String totalBurden = String.format("Total Burden: %.2f", list.returnRequiredTotal("Burden"));
		System.out.print(totalBurden);
	}
}
