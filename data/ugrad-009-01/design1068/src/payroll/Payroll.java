package payroll;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class Payroll {
	public static void main(String[] args) throws PayrollException {
		TakeInputArgument inputArgument = new TakeInputArgument(args);
		List<String> lineListOfEmployees = inputArgument.processFile();
		ListOfEmployees listOfEmployees = new ListOfEmployees(lineListOfEmployees);
		listOfEmployees.separateListToIndividualEmployees();
		String process = inputArgument.findProcess();
		listOfEmployees.calculateNeededData();
		listOfEmployees.checkForSomeErrors();
		Date myDate = new Date();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
		if(process.equals("Payslips")){
			listOfEmployees.sortEmployeesListByTID();
			listOfEmployees.printAllEmployeePayslips();
		} else if(process.equals("Employees")){
			listOfEmployees.sortEmployeesListByName();
			listOfEmployees.printAllEmployeeData();
		} else if(process.equals("Burden")){
			listOfEmployees.employeeListTotalGross();
		} else if(process.equals("PAYE")){
			listOfEmployees.employeeListTotalPAYE();
		} else {
			System.out.println("Incorrect Process Input Error");
		}
	}
}
