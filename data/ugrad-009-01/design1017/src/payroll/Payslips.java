package payroll;
import java.util.Collections;
import java.util.List;
public class Payslips extends AbstractProcessingMethods implements Calculation {
	public void operationPrep(List<Employee> employeeList) {
		Collections.sort(employeeList, new ComparatorTID());
	}
	public void getData(Employee currentEmployee) {
		String name = currentEmployee.getName();
		name = switchName(name);
		double gross = currentEmployee.getGross();
		double PAYE = getPAYE(gross);
		double Nett = gross - PAYE - currentEmployee.getDeduction();
		double YTD = currentEmployee.getYTD() + gross;
		if (Nett < 0) {
			System.out.println("Error. Gross is negative. See TID: " + currentEmployee.getTID());
			System.out.printf("\n");
			return;
		}
		printEmployeePayslip(currentEmployee, name, gross, PAYE, Nett, YTD);
	}
	private String switchName(String oldName) {
		String[] splitName = oldName.split("[, ]+");
		oldName = splitName[1] + " " + splitName[0];
		return oldName;
	}
	private void printEmployeePayslip(Employee currentEmployee, String name, double gross, double PAYE, double Nett, double YTD) {
		System.out.printf(currentEmployee.getTID() + ". ");
		System.out.printf(name + ", ");
		System.out.printf("Period: " + currentEmployee.getStartDate() + " to " + currentEmployee.getEndDate() + ". ");
		System.out.printf("Gross: $%.2f, ", gross);
		System.out.printf("PAYE: $%.2f, ", PAYE);
		System.out.printf("Deductions: $%.2f ", currentEmployee.getDeduction());
		System.out.printf("Nett: $%.2f ", Nett);
		System.out.printf("YTD: $%.2f", YTD);
		System.out.printf("\n");
	}
	public void dispTotal() {
	}
}
