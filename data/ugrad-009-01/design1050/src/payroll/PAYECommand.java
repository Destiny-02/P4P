package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class PAYECommand implements Command{
	public void display(EmployeeList employeeList){
		PAYECalculations(employeeList);
		PAYEDisplay(employeeList);
	}
	private void PAYECalculations(EmployeeList employeeList){
		ArrayList<Employee> employees = employeeList.getEmployeeList();
		for(Employee employee: employees){
			employee.calculateGross();
			employee.calculateYearlyGross();
			employee.calculatePAYE();
			}
	}
	private void PAYEDisplay(EmployeeList employeeList){
		ArrayList<Employee> employees = employeeList.getEmployeeList();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		System.out.println(employees.get(0).justWantDate());
		double sumAllPAYE = 0;
		for(Employee employee: employees){
			sumAllPAYE += Double.parseDouble(employee.getPAYE());
		}
		System.out.print(String.format("Total PAYE: $%.2f", sumAllPAYE));
	}
}