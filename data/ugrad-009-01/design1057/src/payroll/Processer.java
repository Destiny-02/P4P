
package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import payrollExceptions.ArgumentInvalidException;
public class Processer {
	private ProcessType processType;
	public Processer(String processString) {
		if(processString.matches("Payslips")) {
			this.processType = ProcessType.PAYSLIPS;
		}
		else if(processString.matches("Employees")) {
			this.processType = ProcessType.EMPLOYEES;
		}
		else if(processString.matches("Burden")) {
			this.processType = ProcessType.BURDEN;
		}
		else if(processString.matches("PAYE")) {
			this.processType = ProcessType.PAYE;
		}
		else {
			assert false : "Unknow processing type.";
			throw new ArgumentInvalidException(processString + " is not a valid processing method.");
		}
	}
	public void printProcessedOutput(EmployeeList list) {
		this.printCurrentDateString();
		switch(processType) {
			case PAYSLIPS:
				list.sortByTID();
				processPayslip(list);
				break;
			case EMPLOYEES:
				list.sortByLastName();
				processEmployees(list);
				break;
			case BURDEN:
				processBurden(list);
				break;
			case PAYE:
				processPAYE(list);
				break;
			default:
				assert false : "Unknown processing type";
		}
	}
	public static String capitalize(final String typeString) {
		   return Character.toUpperCase(typeString.charAt(0)) + typeString.substring(1);
	}
	private void printCurrentDateString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calender = Calendar.getInstance();
		System.out.println(dateFormat.format(calender.getTime()));
	}
	private void processPAYE(EmployeeList list) {
		double paye = 0;
		for(AbstractEmployee employee : list) {
			paye += employee.getPAYE();
		}
		System.out.println(String.format("%s to %s\nTotal PAYE: $%.2f",
							list.getPayPeriod().getStartDateFormat(), list.getPayPeriod().getEndDateFormat(),
							paye));
	}
	private void processBurden(EmployeeList list) {
		double burden = 0;
		for(AbstractEmployee employee : list) {
			burden += employee.getPayPerPeriod();
		}
		System.out.println(String.format("%s to %s\nTotal Burden: $%.2f",
							list.getPayPeriod().getStartDateFormat(), list.getPayPeriod().getEndDateFormat(),
							burden));
	}
	private void processEmployees(EmployeeList list) {
		for(AbstractEmployee employee : list) {
			System.out.println(String.format("%s, %s (%d) %s, $%.2f YTD:$%.2f",
							employee.getName().getLastName(), employee.getName().getFirstName(),
							employee.getTaxID(), employee instanceof HourlyEmployee ? "Hourly" : "Salaried",
							employee.getRate(),	employee.getYtd() + employee.getPayPerPeriod()));
		}
	}
	private void processPayslip(EmployeeList list) {
		for(AbstractEmployee employee : list) {
			System.out.println(String.format("%d. %s %s, Period: %s to %s. Gross: $"
							+ "%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f",
							employee.getTaxID(), employee.getName().getFirstName(),
							employee.getName().getLastName(), employee.getDates().getStartDateFormat(),
							employee.getDates().getEndDateFormat(), employee.getPayPerPeriod(),
							employee.getPAYE(), employee.getDeduction(), employee.getNett(),
							employee.getYtd() + employee.getPayPerPeriod()));
		}
	}
}
