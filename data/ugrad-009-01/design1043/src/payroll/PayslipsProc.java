package payroll;
import java.util.ArrayList;
public class PayslipsProc {
	public void payslips(ArrayList<Employee> employeeList){
		Today date = new Today();
		date.printTodayDate();
		ArrayList<Employee> _sortedList;
		Sort _sorter = new Sort(employeeList);
		_sortedList = _sorter.sortByTID(employeeList);
		for(int i = 0; i < _sortedList.size(); i++){
			Employee _employee = _sortedList.get(i);
			Print p = new Print();
			System.out.println("");
			p.printPayslip(_employee);
		}
	}
}
