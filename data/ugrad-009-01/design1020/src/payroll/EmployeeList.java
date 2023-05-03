package payroll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class EmployeeList{
	private final String _processType;
	private ArrayList<Employee> _myEmployeeList;
	private double _burden;
	private double _PAYE;
	public EmployeeList(String processType){
		_processType = processType;
		_myEmployeeList = new ArrayList<Employee>();
	}
	public void addEmployee(Employee myEmployee){
		_myEmployeeList.add(myEmployee);
	}
	public void FinaliseData(){
		if((_processType.toLowerCase()).equals("payslips")){
			Collections.sort(_myEmployeeList, EmployeeIDComparator);
		}
		else if((_processType.toLowerCase()).equals("employees")){
			Collections.sort(_myEmployeeList, EmployeeNameComparator);
		}
		else if((_processType.toLowerCase()).equals("burden")){
			calculateBurden();
		}
		else if((_processType.toLowerCase()).equals("paye")){
			calculatePAYE();
		}
	}
	public void calculateBurden(){
		_burden = 0;
		for(Employee myEmployee: _myEmployeeList){
			_burden = _burden + myEmployee.getGross();
		}
	}
	public void calculatePAYE(){
		_PAYE = 0;
		for(Employee myEmployee: _myEmployeeList){
			_PAYE = _PAYE + myEmployee.getPAYE();
		}
	}
	public Comparator<Employee> EmployeeIDComparator = new Comparator<Employee>(){
		public int compare(Employee e1, Employee e2){
			int EmployeeID1 = e1.getTaxID();
			int EmployeeID2 = e2.getTaxID();
			return EmployeeID1-EmployeeID2;
		}
	};
	public Comparator<Employee> EmployeeNameComparator = new Comparator<Employee>(){
		public int compare(Employee e1, Employee e2){
			String EmployeeName1 = e1.getLastName();
			String EmployeeName2 = e2.getLastName();
			return EmployeeName1.compareTo(EmployeeName2);
		}
	};
	public ArrayList<Employee> getEmployeeList(){
		return _myEmployeeList;
	}
	public double getBurden(){
		return _burden;
	}
	public double getPAYE(){
		return _PAYE;
	}
	public void getOutput(){
		Output myoutput = new Output(_processType, _myEmployeeList, _burden, _PAYE);
		myoutput.printOutput();
	}
}
