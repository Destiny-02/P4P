package payroll;
import payroll.Employee.EmployeeConstants;
public class Payslips extends PayrollProcess{
	public static final String CDELIMITER = ": $";
	public static final String FULLSTOP = ". ";
	public static final String COMMA = ", ";
	public static final char SPACE = ' ';
	private StringBuilder _outputString;
	private StringBuilder _errors;
	private enum consts{
		PERIOD("Period: ",FULLSTOP),
		GROSS("Gross: $",COMMA),
		PAYE("PAYE: $",COMMA),
		DEDUCTIONS("Deductions: $",SPACE),
		NETT("Nett: $",SPACE),
		YTD("YTD: $");
		private final String _str;
		private final String _str2;
		consts(String str){
			_str = str;
			_str2 = null;
		}
		consts(String str, String str2){
			_str = str;
			_str2 = str2;
		}
		consts(String str, char char2){
			_str = str;
			_str2 = String.valueOf(char2);
		}
		public String getString(String strToConcat){
			return (_str2!=null)?_str+strToConcat+_str2:_str+strToConcat;
		}
	}
	public Payslips(){
		_outputString = new StringBuilder();
	}
	public void process(AllEmployees all){
		Employee[] employees = all.getEmployeesByTID();
		double[] employeeEarnings = new double[5];
		for(Employee employee : employees){
			try{
			employeeEarnings = doCalculations(employee);
			_outputString.append(employee.getTID()+FULLSTOP);
			_outputString.append(employee.getNameWithDelimiter(' ',Order.FIRST_TO_LAST)+COMMA);
			_outputString.append(consts.PERIOD.getString(employee.getWorkingPeriod()));
			_outputString.append(consts.GROSS.getString(this.format(employeeEarnings[0])));
			_outputString.append(consts.PAYE.getString(this.format(employeeEarnings[1])));
			_outputString.append(consts.DEDUCTIONS.getString(this.format(employeeEarnings[2])));
			_outputString.append(consts.NETT.getString(this.format(employeeEarnings[3])));
			_outputString.append(consts.YTD.getString(this.format(employeeEarnings[4])));
			_outputString.append('\n');
			}catch(CalculationException ex){
			System.err.println("Employee TID "+employee.getTID()+" could not be processed: "+ex.getMessage());
			}
		}
		this.setOutput(_outputString.toString());
	}
	public double[] doCalculations(Employee employee) throws CalculationException{
		double gross;
		double paye;
		double deductions;
		double nett;
		double ytd;
		deductions = employee.getDeduction();
		deductions = Math.round(deductions*100)*0.01;
		double calc;
		if(employee.getEmploymentType().toLowerCase().equals(EmployeeConstants.SALARY.getType())){
			calc = Math.round(employee.getRate()/EmployeeConstants.WEEKSINYEAR.getUnits()*100)*0.01;
		}else if(employee.getEmploymentType().toLowerCase().equals(EmployeeConstants.HOURLY.getType())){
			calc = employee.getRate()*calculateOvertime(employee.getHoursWorked());
			calc = Math.round(calc*100)*0.01;
		}else{
			throw new CalculationException("Employee "+employee.getTID()+" had invalid employment type.");
		}
		gross = calc;
		ytd = employee.getYTD()+calc;
		Tax tax = new Tax(calc*52);
		paye = tax.calculateTax()/52*100;
		paye = Math.round(paye)*0.01;
		nett = gross-paye-deductions;
		return checkForNegativeCurrency(new double[]{gross,paye,deductions,nett,ytd});
	}
	public double[] checkForNegativeCurrency(double[] testcase) throws CalculationException{
		_errors = new StringBuilder();
		for(int i=0;i<testcase.length;i++){
			if(testcase[i]<0){
				switch(i){
				case 0:
					_errors.append("(Gross)");
					break;
				case 1:
					_errors.append("(PAYE)");
					break;
				case 2:
					_errors.append("(Deductions)");
					break;
				case 3:
					_errors.append("(Nett)");
					break;
				case 4:
					_errors.append("(YTD)");
					break;
				default:
				}
			}
		}
		if(_errors.length()!=0){
		throw new CalculationException("Invalid currency/currencies: "+_errors.toString());
		}
		return testcase;
	}
}