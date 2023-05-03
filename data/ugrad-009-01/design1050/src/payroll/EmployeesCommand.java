package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
public class EmployeesCommand implements Command{
	public void display(EmployeeList employeeList){
		employeesCalculations(employeeList);
		employeesDisplay(employeeList);
	}
	private void employeesCalculations(EmployeeList employeeList){
		ArrayList<Employee> employees = employeeList.getEmployeeList();
		for(Employee employee: employees){
			employee.calculateGross();
			employee.calculateYTD();
		}
	}
	private void employeesDisplay(EmployeeList employeeList){
		ArrayList<Employee> employees = employeeList.getEmployeeList();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.print(dateFormat.format(date));
		Collections.sort(employees, new TIDComparator());
		Collections.sort(employees, new GivenNameComparator());
		Collections.sort(employees, new FamilyNameComparator());
		for(Employee employee: employees){
			String str = employee.forEmployeesCommand();
			System.out.print("\n" + str);
		}
	}
}

