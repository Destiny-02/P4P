package payroll;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
public class Employees extends Process{
	public void process(Hashtable<Integer, Employee> employeeList){
		String date = String.format("%tF",
                Calendar.getInstance(TimeZone.getDefault()));
		System.out.println(date);
		Set<Integer> TIDSet = employeeList.keySet();
		List<Integer> TIDList = new ArrayList<Integer>(TIDSet);
		List<String> names = new ArrayList<String>();
		for(int i = 0; i < TIDList.size(); i++){
			Employee employee = employeeList.get(TIDList.get(i));
			names.add(employee.Employees(employee));
		}
		Collections.sort(names);
		for(int i = 0; i < names.size(); i++){
			System.out.println(names.get(i));
		}
	}
}
