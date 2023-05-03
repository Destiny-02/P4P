package payroll;
import payroll.Employee.EmployeeConstants;
public class Employees extends PayrollProcess{
	public static final String CDELIMITER = " $";
	public static final char COMMA = ',';
	public static final char SPACE = ' ';
	private StringBuilder _outputString;
	private StringBuilder _errors;
	public Employees(){
		_outputString = new StringBuilder();
	}
	public void process(AllEmployees all){
		Employee[] employees = all.getEmployeesByName();
		double calc=0;
		for(Employee employee : employees){
			try{
			calc = doCalculations(employee);
			_outputString.append(employee.getNameWithDelimiter(", ",Order.LAST_TO_FIRST)+SPACE);
			_outputString.append("("+employee.getTID()+") ");
			_outputString.append(employee.getEmploymentType()+COMMA);
			_outputString.append(CDELIMITER+format(employee.getRate())+SPACE);
			_outputString.append("YTD:$"+format(calc));
			_outputString.append('\n');
			}catch(CalculationException ex){
				System.err.println("Employee TID "+employee.getTID()+" could not be processed: "+ex.getMessage());
				calc=0;
			}
		}
		this.setOutput(_outputString.toString());
	}
	public double doCalculations(Employee employee) throws CalculationException{
		double ytd;
		double calc;
		if(employee.getEmploymentType().toLowerCase().equals(EmployeeConstants.SALARY.getType())){
			calc = Math.round(employee.getRate()/EmployeeConstants.WEEKSINYEAR.getUnits()*100)*0.01;
		}else if(employee.getEmploymentType().toLowerCase().equals(EmployeeConstants.HOURLY.getType())){
			calc = employee.getRate()*calculateOvertime(employee.getHoursWorked());
			calc = Math.round(calc*100)*0.01;
		}else{
			throw new CalculationException("Employee "+employee.getTID()+" had invalid employment type.");
		}
		ytd = employee.getYTD()+calc;
		checkForNegativeCurrency(calc,ytd);
		return ytd;
	}
	public void checkForNegativeCurrency(double testGross,double testYTD) throws CalculationException{
		_errors = new StringBuilder();
			if(testGross<0){
				_errors.append("(Gross)");
			}
			if(testYTD<0){
				_errors.append("(YTD)");
			}
		if(_errors.length()!=0){
		throw new CalculationException("Invalid currency/currencies: "+_errors.toString());
		}
	}
}