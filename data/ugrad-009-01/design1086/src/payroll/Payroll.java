package payroll;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class Payroll {
	public static void main (String[] args) throws EmploymentException{
		EmployeeList myList = new EmployeeList();
		FileInput file = new FileInput();
		file.processFile(args[0], myList);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		if (args[1].toLowerCase().equals("payslips")){
			System.out.println(dateFormat.format(date));
			myList.printPayslipReport();
		} else if (args[1].toLowerCase().equals("employees")){
			System.out.println(dateFormat.format(date));
			myList.printEmployeesReport();
		} else if(args[1].toLowerCase().equals("burden")){
			System.out.println(dateFormat.format(date));
			myList.printBurden();
		} else if (args[1].toLowerCase().equals("paye")){
			System.out.println(dateFormat.format(date));
			myList.printPAYE();
		} else {
			System.out.println("Error: Invalid process (" + args[1] + ")");
		}
	}
}
