package payroll;
import java.util.List;
public class PAYE extends Process {
	public void process(List<Employee> employeeList) {
		Double paye = 0d;
		for (Employee employee : employeeList) {
			paye += employee.getPayeCopy();
		}
		System.out.println(employeeList.get(1).getStart() + "to" + employeeList.get(1).getEnd());
		System.out.println("Total PAYE: $" + round(paye));
	}
}