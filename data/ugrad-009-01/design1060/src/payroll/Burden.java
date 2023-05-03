package payroll;
public class Burden implements Form{
	private Double _burdenTotal;
	private EmployeeRecords _employeeRecords;
	private EmployeeData _firstEmployee;
	Burden(EmployeeRecords employeeRecords){
		_employeeRecords = employeeRecords;
	}
	public void processForm(){
		Double tempTotal = (double) 0;
		boolean flag = true;
		for (Integer key : _employeeRecords.getKeySet()){
			tempTotal += _employeeRecords.getEmployee(key).getGrossAmount();
			if (flag == true){
				_firstEmployee = _employeeRecords.getEmployee(key);
				flag = false;
			}
		}
		_burdenTotal = tempTotal;
	}
	Double getBurdenTotal(){
		return _burdenTotal;
	}
	EmployeeData getFirstEmployee(){
		return _firstEmployee;
	}
}

