
package comparators;
import java.util.Comparator;
import employee.GeneralEmployee;
public class TIDComparator implements Comparator<GeneralEmployee>
{
	@Override
	public int compare(GeneralEmployee emp1, GeneralEmployee emp2)
	{
		int tID1 = emp1.getTID();
		int tID2 = emp2.getTID();
		if (tID1 < tID2)
		{
			return -1;
		}
		else if (tID1 == tID2)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
}
