package payroll;
public class PAYEProcessing extends PayrollOutput{
	private double _totalPAYE = 0.0;
	private Employee[] _employeeArray;
	PAYEProcessing(EmployeeList employeeArrayIn) throws PayrollUserException{
		_employeeArray = employeeArrayIn.getEmployeeArray();
		if(_employeeArray.equals(null) || _employeeArray.length==0){
			throw new PayrollUserException("Can't use process total PAYE as no valid records are avalible");
		}
		for(Employee employee: _employeeArray){
			if(PAYECalculator(employee)<0){
				throw new PayrollUserException(employee.name +" has negative earnings");
			}
			_totalPAYE += PAYECalculator(employee);
		}
	}
	protected void process(){
		printToday();
		System.out.println(_employeeArray[0].start + " to " + _employeeArray[0].end);
		System.out.println("Total PAYE: $" + _totalPAYE);
	}
}
