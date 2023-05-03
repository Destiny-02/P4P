package payroll;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
public class Burden extends Process{
	void process(Hashtable<Integer, Employee> employeeList) {
		String date = String.format("%tF",
                Calendar.getInstance(TimeZone.getDefault()));
		System.out.println(date);
		float burden = 0;
		Set<Integer> TIDSet = employeeList.keySet();
		List<Integer> TIDList = new ArrayList<Integer>(TIDSet);
		Employee employee = employeeList.get(TIDList.get(0));
		System.out.println(employee.Burden(employee));
		for(int i = 0; i < TIDList.size(); i++){
			employee = employeeList.get(TIDList.get(i));
			burden = burden + employee.CalculateGross(employee);
		}
		System.out.print("Total Burden: $");
		System.out.println(Round(burden));
	}
}
