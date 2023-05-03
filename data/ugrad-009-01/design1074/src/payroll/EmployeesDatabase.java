package payroll;
import java.util.Collections;
import java.util.LinkedList;
public class EmployeesDatabase{
	private LinkedList<Employee> _employeesRoll=new LinkedList<Employee>();
	public EmployeesDatabase(){}
	public void putData(Employee e){
		this._employeesRoll.add(e);
	}
	public LinkedList<Employee> getStaffRoll(){
		return this._employeesRoll;
	}
	public void TIDsort(){
		Collections.sort(_employeesRoll);
	}
	public void nameSort(){
		Collections.sort(_employeesRoll, new Employee().new EmployeeComparator());
	}
}
