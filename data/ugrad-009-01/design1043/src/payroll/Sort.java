package payroll;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Sort {
	private ArrayList<Employee> _employeeList;
	Comparator<Employee> NAME_ORDER = Employee.NAME_ORDER;
	Comparator<Employee> TID_ORDER = Employee.TID_ORDER;
	public Sort(ArrayList<Employee> employeeList){
		_employeeList = employeeList;
	}
	final Collection<Employee> employees = _employeeList;
	public ArrayList<Employee> sortByName(){
		List<Employee> e = new ArrayList<Employee>(_employeeList);
		Collections.sort(e, NAME_ORDER);
		return (ArrayList<Employee>) e;
	}
	public ArrayList<Employee> sortByTID(ArrayList<Employee> _employeeList){
		List<Employee> e = new ArrayList<Employee>(_employeeList);
		Collections.sort(e, TID_ORDER);
		return (ArrayList<Employee>) e;
	}
}
