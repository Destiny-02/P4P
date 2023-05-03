package payroll;
public class EmployeeList {
	private Employee[] _employeeArray;
	EmployeeList(String[] records) throws PayrollUserException{
		_employeeArray = new Employee[records.length];
		for(int i = 0; i< records.length; i++){
			_employeeArray[i] = new Employee(records[i]);
		}
	}
	public Employee[] getEmployeeArray(){
		return _employeeArray;
	}
}
