package payroll;
import java.util.Comparator;
public class IDComparator implements Comparator<Employee> {
	@Override
	public int compare(Employee o1, Employee o2) {
		if (o1.getIDCopy() > (o2.getIDCopy())){
			return 1;
		}
		else if (o1.getIDCopy() == (o2.getIDCopy())) {
			System.out.println("Error duplicate ID number: " + o1.getIDCopy());
			return 0;
		}
		else return -1;
	}
}