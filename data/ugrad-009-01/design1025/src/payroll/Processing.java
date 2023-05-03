package payroll;
import java.util.ArrayList;
public class Processing extends Calculations{
	private ArrayList<Employees> _employees;
	public Processing(ArrayList<Employees> employees) {
		_employees = employees;
	}
	@SuppressWarnings("unchecked")
	public void payslips() {
		printDate();
		ArrayList<Integer> TaxIDs = (ArrayList<Integer>) sortList(_employees, true);
		for (int TID: TaxIDs) {
			int index = Employees.find(TID, _employees, true);
			Employees worker = _employees.get(index);
			int taxID = worker.getTID();
			String[] name = worker.getName().split(", ");
			String fullName = name[1] + " " + name[0];
			Calculations person = new Calculations(worker);
			float gross = person.getGross();
			float paye = person.getPAYE();
			float deductions = worker.getDeductions();
			float nett = gross - paye - deductions;
			float ytd = worker.getYTD() + gross;
			System.out.print(taxID + ". ");
			System.out.print(fullName);
			System.out.print(", Period: ");
			worker.printStart();
			System.out.print(" to ");
			worker.printEnd();
			System.out.printf(". Gross: $%.2f", gross);
			System.out.printf(", PAYE: $%.2f", paye);
			System.out.printf(", Deductions: $%.2f", deductions);
			System.out.printf(" Nett: $%.2f", nett);
			System.out.printf(" YTD: $%.2f\n", ytd);
		}
	}
	@SuppressWarnings("unchecked")
	public void employees() {
		printDate();
		ArrayList<String> names = (ArrayList<String>) sortList(_employees, false);
		for (String nameX: names) {
			int index = Employees.find(nameX, _employees, false);
			Employees worker = _employees.get(index);
			String name = worker.getName();
			int taxID = worker.getTID();
			String employment = worker.getEmployment();
			Calculations person = new Calculations(worker);
			float rate = worker.getRate();
			float gross = person.getGross();
			float ytd = worker.getYTD() + gross;
			System.out.print(name + " (" + taxID + ") ");
			System.out.print(employment + ", $");
			System.out.printf("%.2f", rate);
			System.out.printf(" YTD:$%.2f\n", ytd);
		}
	}
	public void burden() {
		printDate();
		float burden = 0;
		for (Employees employee: _employees) {
			Calculations person = new Calculations(employee);
			burden += person.getGross();
		}
		printWeek(_employees);
		System.out.printf("Total Burden: $%.2f\n", burden);
	}
	public void paye() {
		printDate();
		float paye = 0;
		for (Employees employee: _employees) {
			Calculations person = new Calculations(employee);
			paye += person.getPAYE();
		}
		printWeek(_employees);
		System.out.printf("Total PAYE: $%.2f\n", paye);
	}
}
