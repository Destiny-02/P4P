package payroll;
import java.util.Comparator;
public class NameComparator implements Comparator<Employee> {
	@Override
	public int compare(Employee o1, Employee o2) {
		int returnValue = o1.getLastNameCopy().compareTo(o2.getLastNameCopy());
		if (returnValue == 0) {
			returnValue = o1.getFirstNameCopy().compareTo(o2.getFirstNameCopy());
		}
		return returnValue;
	}
}
