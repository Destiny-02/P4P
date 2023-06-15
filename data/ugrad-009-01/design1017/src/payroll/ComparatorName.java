package payroll;
import java.util.Comparator;
public class ComparatorName implements Comparator<Employee> {
	public int compare(Employee employeeOne, Employee employeeTwo) {
		int compareName = employeeOne.compareName(employeeTwo);
		if (compareName == 0) {
			return employeeOne.compareTID(employeeTwo);
		} else {
			return compareName;
		}
    }
}
