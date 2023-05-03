package payroll;
public class Payslips implements Display {
	public void display(EmployeeList list) {
		Display.printCurrentDate();
		String[] payslips = list.returnRequiredStrings("Payslips");
		for (int i = 0; i < payslips.length; i++) {
			if (i == payslips.length - 1) {
				System.out.print(payslips[i]);
			} else {
				System.out.println(payslips[i]);
			}
		}
	}
}
