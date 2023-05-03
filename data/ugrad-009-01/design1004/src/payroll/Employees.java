package payroll;
import java.util.List;
public class Employees extends Process {
	public void process(List<Employee> employeeList) {
		for (Employee employee : employeeList) {
			System.out.println(employee.employeesLine());
		}
	}
}