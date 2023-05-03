package payroll;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
public class Payslips extends Process{
	void process(Hashtable<Integer, Employee> employeeList) {
		String date = String.format("%tF",
                Calendar.getInstance(TimeZone.getDefault()));
		System.out.println(date);
		Set<Integer> TIDSet = employeeList.keySet();
		List<Integer> TIDList = new ArrayList<Integer>(TIDSet);
		Collections.sort(TIDList);
		for(int i = 0; i < TIDList.size(); i++){
			Employee employee = employeeList.get(TIDList.get(i));
			System.out.println(employee.Payslips(employee));
		}
	}
}
