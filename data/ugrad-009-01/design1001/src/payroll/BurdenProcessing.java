package payroll;
public class BurdenProcessing extends PayrollOutput{
	private double _totalBurden;
	private Employee[] _employeeArray;
	BurdenProcessing(EmployeeList employeeArrayIn) throws PayrollUserException{
		_employeeArray = employeeArrayIn.getEmployeeArray();
		if(_employeeArray.equals(null) || _employeeArray.length==0){
			throw new PayrollUserException("Can't use process total Burden as no valid records are avalible");
		}
		for(Employee employee: _employeeArray){
			if(grossCalculator(employee)<0){
				throw new PayrollUserException(employee.name +" has negative earnings");
			}
			_totalBurden += grossCalculator(employee);
		}
	}
	protected void process(){
		printToday();
		System.out.println(_employeeArray[0].start + " to " + _employeeArray[0].end);
		System.out.println("Total Burden: $" + _totalBurden);
	}
}
