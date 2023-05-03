package payroll;
public class Roster implements Display {
	public void display(EmployeeList list) {
		Display.printCurrentDate();
		String[] roster = list.returnRequiredStrings("Employees");
		for (int i = 0; i < roster.length; i++) {
			if (i == roster.length - 1) {
				System.out.print(roster[i]);
			} else {
				System.out.println(roster[i]);
			}
		}
	}
}
