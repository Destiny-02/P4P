package payroll;
public class Print {
	public void printPayslip(Employee employee){
		employee.printTid();
		System.out.print(". ");
		employee.printName(true);
		System.out.print(" Period: ");
		employee.printDate();
		System.out.print(". ");
		System.out.print("Gross: ");
		Gross g = new Gross();
		g.printGross(employee);
		System.out.print("PAYE: ");
		PAYE p = new PAYE();
		p.printPaye(employee);
		System.out.print("Deductions: ");
		employee.printDeduction();
		System.out.print("Nett: ");
		employee.printNett(employee);
		System.out.print("YTD: ");
		g.printYTD(employee);
	}
	public void printEmployees(Employee employee){
		employee.printName(false);
		System.out.print(" (");
		employee.printTid();
		System.out.print(") ");
		employee.printEarnType();
		System.out.print(", ");
		employee.printEarnings();
		System.out.print(" YTD:");
		Gross g = new Gross();
		g.printYTD(employee);
	}
}
