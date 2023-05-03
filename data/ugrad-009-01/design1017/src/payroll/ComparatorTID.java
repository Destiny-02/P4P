package payroll;
import java.util.Comparator;
public class ComparatorTID implements Comparator<Employee> {
	public int compare(Employee employeeOne, Employee employeeTwo) {
		return employeeOne.compareTID(employeeTwo);
    }
}
