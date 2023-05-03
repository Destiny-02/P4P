package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Output {
	public Output(EmployeeDatabase employeelist) {
	}
	public void PayslipOutput(EmployeeDatabase employees) {
		printDate();
		employees.taxidsort();
		for (int i = 0; i < employees.getNumberOfEmployees(); i++) {
			Employee employee = employees.getEmployee(i);
			String[] outname;
			outname = employee.getname().split(", ");
			System.out.printf(employee.gettaxID() + ". " + outname[1] + " "
					+ outname[0] + ", Period: " + employee.getstart() + " to "
					+ employee.getend());
			System.out
					.printf(". Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f%n",
							employee.getgross(), employee.getPAYE(),
							employee.getdeductions(), employee.getNett(),
							employee.getnewytd());
		}
	}
	public void BurdenOutput(EmployeeDatabase employees) {
		double TotalBurden = 0;
		Employee employee;
		printDate();
		for (int i = 0; i < employees.getNumberOfEmployees(); i++) {
			employee = employees.getEmployee(i);
			TotalBurden += employee.getgross();
		}
		employee = employees.getEmployee(0);
		System.out.println(employee.getstart() + " to " + employee.getend());
		System.out.printf("Total Burden: $%.2f", TotalBurden);
	}
	public void EmployeeListOutput(EmployeeDatabase employees) {
		printDate();
		employees.selectionSort();
		Employee employee = employees.getEmployee(0);
		for (int i = 0; i < employees.getNumberOfEmployees(); i++) {
			employee = employees.getEmployee(i);
			System.out.printf(employee.getname() + " (" + employee.gettaxID()
					+ ") " + employee.getemployment() + ", $");
			System.out.printf("%.2f YTD:$%.2f%n", employee.getrate(),
					employee.getnewytd());
		}
	}
	public void PAYEOutput(EmployeeDatabase employees) {
		float TotalPAYE = 0;
		Employee employee;
		printDate();
		for (int i = 0; i < employees.getNumberOfEmployees(); i++) {
			employee = employees.getEmployee(i);
			TotalPAYE += employee.getPAYE();
		}
		employee = employees.getEmployee(0);
		System.out.println(employee.getstart() + " to " + employee.getend());
		System.out.printf("Total PAYE: $%.2f", TotalPAYE);
	}
	private void printDate() {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		System.out.println(df.format(date));
	}
}
