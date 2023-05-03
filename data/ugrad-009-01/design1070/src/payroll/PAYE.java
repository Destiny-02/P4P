package payroll;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
public class PAYE extends Process{
	void process(Hashtable<Integer, Employee> employeeList) {
		String date = String.format("%tF",
                Calendar.getInstance(TimeZone.getDefault()));
		System.out.println(date);
		float PAYE = 0;
		Set<Integer> TIDSet = employeeList.keySet();
		List<Integer> TIDList = new ArrayList<Integer>(TIDSet);
		Employee employee = employeeList.get(TIDList.get(0));
		System.out.println(employee.PAYE(employee));
		for(int i = 0; i < TIDList.size(); i++){
			employee = employeeList.get(TIDList.get(i));
			PAYE = PAYE + employee.CalculatePAYE(employee);
		}
		System.out.print("Total PAYE: $");
		System.out.println(Round(PAYE));
	}
}
