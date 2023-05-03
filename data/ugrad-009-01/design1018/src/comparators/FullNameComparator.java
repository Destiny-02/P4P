
package comparators;
import java.util.Comparator;
import employee.GeneralEmployee;
public class FullNameComparator implements Comparator<GeneralEmployee>{
	public int compare(GeneralEmployee emp1, GeneralEmployee emp2) {
		String name1 = emp1.getFullName();
		String name2 = emp2.getFullName();
		return name1.compareTo(name2);
	}
}