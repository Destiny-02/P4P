package payroll;
import java.util.ArrayList;
public class EmployeesProc {
	public void employeeProcess(ArrayList<Employee> employeeList) {
		Today date = new Today();
		date.printTodayDate();
		ArrayList<Employee> _sortedList;
		Sort _sorter = new Sort(employeeList);
		_sortedList = _sorter.sortByName();
		for(int i = 0; i < _sortedList.size(); i++){
			Employee _employee = _sortedList.get(i);
			Print p = new Print();
			System.out.println("");
			p.printEmployees(_employee);
		}
	}
}
