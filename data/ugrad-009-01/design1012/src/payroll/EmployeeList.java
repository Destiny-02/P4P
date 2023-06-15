package payroll;
import java.util.ArrayList;
public class EmployeeList {
	private ArrayList<Employee> _EmployeeList = new ArrayList<Employee>();
	void addToList(Employee employee){
		_EmployeeList.add(employee);
	}
	public  ArrayList<Employee> copyList(){
		return _EmployeeList;
	}
}
