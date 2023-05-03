package payroll;
import java.util.Arrays;
public class EmployeeProcessing extends PayrollOutput{
	private Employee[] _employeeArray;
	EmployeeProcessing(EmployeeList employeeArrayIn) throws PayrollUserException{
		_employeeArray = employeeArrayIn.getEmployeeArray();
		if(_employeeArray.equals(null) || _employeeArray.length==0){
			throw new PayrollUserException("Can't process employees as no valid records are avalible");
		}
		Arrays.sort(_employeeArray,Employee.employeeNameComparator);
	}
	protected void process() throws PayrollUserException{
		printToday();
		for(Employee e: _employeeArray){
			System.out.printf("%s (%d) %s, $%.2f YTD:$%.2f\n", e.name, e.taxID, e.employment, e.rate, e.YTD +grossCalculator(e));
		}
	}
}
