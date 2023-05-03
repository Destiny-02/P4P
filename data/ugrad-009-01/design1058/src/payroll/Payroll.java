package payroll;
import java.io.IOException;
import java.util.Vector;
public class Payroll {
	public static void main(String[] args) throws IOException {
		ProcessData process = new ProcessData();
		Vector<Employee> employeeList = process.processEmployees(args[0]);
		ProcessType pt;
		switch (args[1]) {
		case "Payslips":
			pt = new Payslips(employeeList);
			break;
		case "Employees":
			pt = new Employees(employeeList);
			break;
		case "Burden":
			pt = new Burden(employeeList);
			break;
		case "PAYE":
			pt = new PAYE(employeeList);
			break;
		default:
			pt = null;
			break;
		}
		if (pt == null) {
			System.out.println("Error: Invalid Processing Type entered");
			System.out.println("Valid types are \"Payslips\", \"Employees\", \"Burden\", and \"PAYE\".");
		} else {
			pt.print();
		}
	}
}
