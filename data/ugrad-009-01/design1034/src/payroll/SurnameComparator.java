package payroll;
import java.util.Comparator;
public class SurnameComparator implements Comparator<Employee> {
	public int compare(Employee emp1, Employee emp2) {
		return (emp1.getLastName()).compareTo(emp2.getLastName());
	}
}
