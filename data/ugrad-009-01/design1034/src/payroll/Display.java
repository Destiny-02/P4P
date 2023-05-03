package payroll;
import java.util.ArrayList;
import java.util.Collections;
public class Display {
	private ArrayList<Employee> _employees;
	private String _process;
	public Display(ArrayList<Employee> employees, String process) {
		_employees = employees;
		_process = process;
	}
	public void ASCIIDisplayForProcess() {
		try {
			switch (_process) {
			case "Payslips": Collections.sort(_employees);
			processPayslip(_employees);
			break;
			case "Employees": Collections.sort(_employees, new SurnameComparator());
			processEmployee(_employees);
			break;
			case "Burden": processBurden(_employees);
			break;
			case "PAYE": processPAYE(_employees);
			break;
			default : throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException iae) {
			System.out.println("An invalid process was entered. Please use 'Payslips', 'Employees', 'Burden' or 'PAYE'.");
		}
	}
	private void processPayslip(ArrayList<Employee> employees) {
		try {
		for (Employee e: employees) {
			String outputString = e.getTID() + "." + e.getFirstName() + " " + e.getLastName()
			+ ", Period: " + e.getStartDate() + " to " + e.getEndDate() + ". Gross: $" + e.computeGross()
			+ ", PAYE: $" + e.computeTax() + ", Deductions: $" + e.getDeduction() +
			" Nett: $" + e.computePaye() +" YTD: $" + e.computeYTD();
			System.out.println(outputString);
		}
		} catch (RuntimeException rte) {
			System.out.println("A negative number was provided as gross");
		}
	}
	private void processEmployee(ArrayList<Employee> employees) {
		try {
		for (Employee e: employees) {
			if ((Double.parseDouble(e.getRate()) < 0)) {
				throw new RuntimeException();
			}
			String outputString = e.getLastName() + "," + e.getFirstName() + " (" + e.getTID() + ") " + e.getEmployment()
			+ ", $" + e.getRate() + " YTD:$" + e.computeYTD();
			System.out.println(outputString);
		}
		} catch (RuntimeException rte) {
			System.out.println("A negative number was provided as gross");
		}
	}
	private void processBurden(ArrayList<Employee> employees) {
		double totalBurden = 0;
		String startDate = "";
		String endDate = "";
		for (Employee e: employees) {
			if (startDate.equals("") && endDate.equals("")) {
				startDate = e.getStartDate();
				endDate = e.getEndDate();
				totalBurden += Double.parseDouble(e.computeGross());
			} else if (startDate.equals(e.getStartDate()) && (endDate.equals(e.getEndDate()))) {
				totalBurden += Double.parseDouble(e.computeGross());
			} else {
				throw new RuntimeException();
			}
		}
		System.out.println(startDate + " to " + endDate);
		System.out.println("Total Burden: $" + totalBurden);
	}
	private void processPAYE(ArrayList<Employee> employees) {
		double totalPaye = 0;
		String startDate = "";
		String endDate = "";
		for (Employee e: employees) {
			if (startDate.equals("") && endDate.equals("")) {
				startDate = e.getStartDate();
				endDate = e.getEndDate();
				totalPaye += Double.parseDouble(e.computeTax());
			} else if (startDate.equals(e.getStartDate()) && (endDate.equals(e.getEndDate()))) {
				totalPaye += Double.parseDouble(e.computeTax());
			} else {
				throw new RuntimeException();
			}
		}
		System.out.println(startDate + " to " + endDate);
		System.out.println("Total PAYE: $" + totalPaye);
	}
}