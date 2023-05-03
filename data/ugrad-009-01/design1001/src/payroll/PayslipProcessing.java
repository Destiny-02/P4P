package payroll;
import java.util.Arrays;
public class PayslipProcessing extends PayrollOutput{
	private Employee[] _employeeArray;
	PayslipProcessing(EmployeeList employeeArrayIn) throws PayrollUserException{
		_employeeArray = employeeArrayIn.getEmployeeArray();
		if(_employeeArray.equals(null) || _employeeArray.length==0){
			throw new PayrollUserException("Can't process employees as no valid records are avalible");
		}
		Arrays.sort(_employeeArray);
	}
	protected void process() throws PayrollUserException{
		printToday();
		for(Employee e: _employeeArray){
			String newName = reformatName(e.name);
			double gross = grossCalculator(e);
			double PAYE = PAYECalculator(e);
			double nett = gross - PAYE - e.deductions;
			double newYTD = e.YTD + gross;
			System.out.printf("%d. %s, Period: %s to %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f "
					+ "Nett: $%.2f YTD: $%.2f\n", e.taxID, newName, e.start, e.end, gross, PAYE,
					e.deductions,nett, newYTD );
		}
	}
	private String reformatName(String name) throws PayrollUserException{
		String names[] = name.split(", ");
		if(names.length!=2){
			throw new PayrollUserException(name+" is an invalid name token");
		}
		return (names[1] + " " + names[0]);
	}
}
