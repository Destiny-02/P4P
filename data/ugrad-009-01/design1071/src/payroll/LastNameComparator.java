package payroll;
import java.util.Comparator;
public class LastNameComparator implements Comparator<Employable> {
	public int compare(Employable emp1, Employable emp2) {
		return emp1.getLastName().compareTo(emp2.getLastName());
	}
}
