package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Processor {
	private String output = getDate() + "\n";
	public String getDate() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		return format.format(today);
	}
	public String process(EmployeeList employeeList, FormatType fm){
		String start = employeeList.getStartDate();
		String end = employeeList.getEndDate();
		switch(fm){
			case PAYSLIPS:
				employeeList.sortByID();
				for (Employable e : employeeList){
					output += e.generateEntry(FormatType.PAYSLIPS);
				}
				break;
			case EMPLOYEES:
				employeeList.sortByLastName();
				for (Employable e: employeeList){
					output = output + e.generateEntry(FormatType.EMPLOYEES);
				}
				break;
			case BURDEN:
				double burden = employeeList.sumBurden();
				output += String.format("%s to %s%nTotal Burden: $%.2f\n", start, end, burden);
				break;
			case PAYE:
				double paye = employeeList.sumPaye();
				output += String.format("%s to %s%nTotal PAYE: $%.2f\n", start, end, paye);
				break;
			default:
				System.out.println("Invalid processing argument.");
		}
		return output.trim();
	}
}
