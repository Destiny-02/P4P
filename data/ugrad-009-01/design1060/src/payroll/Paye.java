package payroll;
public class Paye implements Form{
	private Double _payeTotal;
	private final EmployeeRecords _employeeRecords;
	private EmployeeData _firstEmployee;
	Paye(EmployeeRecords employeeRecords) {
		_employeeRecords = employeeRecords;
	}
	public void processForm(){
		Double tempTotal = (double) 0;
		boolean flag = true;
		for (Integer key : _employeeRecords.getKeySet()){
			tempTotal += _employeeRecords.getEmployee(key).getTaxAmount();
			if (flag == true){
				_firstEmployee = _employeeRecords.getEmployee(key);
				flag = false;
			}
		}
		_payeTotal = (double)tempTotal;
	}
	Double getPayeTotal(){
		return _payeTotal;
	}
	EmployeeData getFirstEmployee(){
		return _firstEmployee;
	}
}
