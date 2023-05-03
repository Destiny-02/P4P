package payroll;
import java.util.Comparator;
public class TaxIDComparator implements Comparator<Employable> {
	public int compare(Employable emp1, Employable emp2) {
		return Integer.compare(emp1.getID(), emp2.getID());
	}
}
