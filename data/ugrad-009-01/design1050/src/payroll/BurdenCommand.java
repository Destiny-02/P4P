package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class BurdenCommand implements Command{
	public void display(EmployeeList employeeList){
		burdenCalculations(employeeList);
		burdenDisplay(employeeList);
	}
	private void burdenCalculations(EmployeeList employeeList){
		ArrayList<Employee> employees = employeeList.getEmployeeList();
		for(Employee employee: employees){
			employee.calculateGross();
		}
	}
	private void burdenDisplay(EmployeeList employeeList){
		ArrayList<Employee> employees = employeeList.getEmployeeList();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		System.out.println(employees.get(0).justWantDate());
		double sumAllGross = 0;
		for(Employee employee: employees){
			sumAllGross += Double.parseDouble(employee.getGross());
		}
		System.out.print(String.format("Total Burden: $%.2f", sumAllGross));
	}
}
