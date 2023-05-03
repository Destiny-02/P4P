package payroll;
import java.util.List;
public class Burden extends Process {
	public void process(List<Employee> employeeList) {
		Double burden = 0d;
		for (Employee employee : employeeList) {
			burden += employee.getGrossCopy();
		}
		System.out.println(employeeList.get(1).getStart() + "to" + employeeList.get(1).getEnd());
		System.out.println("Total Burden: $" + round(burden));
	}
}