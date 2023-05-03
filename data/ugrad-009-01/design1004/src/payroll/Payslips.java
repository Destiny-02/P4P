package payroll;
import java.util.List;
public class Payslips extends Process {
	public void process (List<Employee> employeeList) {
		for (Employee employee : employeeList) {
			System.out.println(employee.payslipsLine());
		}
	}
}