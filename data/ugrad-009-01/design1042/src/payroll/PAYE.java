package payroll;
import payroll.Employee.EmployeeConstants;
public class PAYE extends PayrollProcess{
	private String op;
	private StringBuilder _errors;
	public PAYE(){
	}
	public void process(AllEmployees all){
		Employee[] employees = all.getEmployeesByName();
		op = employees[0].getWorkingPeriod()+'\n';
		double paye = 0;
		for(Employee employee : employees){
			try{
			paye += doCalculation(employee);
			}catch(CalculationException ex){
				System.err.println("Employee TID "+employee.getTID()+" could not be processed: "+ex.getMessage());
			}
		}
		op += "Total PAYE: $"+format(paye)+"\n";
		this.setOutput(op);
	}
	public double doCalculation(Employee employee) throws CalculationException{
		double calc;
		double paye;
		if(employee.getEmploymentType().toLowerCase().equals(EmployeeConstants.SALARY.getType())){
			calc = Math.round(employee.getRate()/EmployeeConstants.WEEKSINYEAR.getUnits()*100)*0.01;
		}else if(employee.getEmploymentType().toLowerCase().equals(EmployeeConstants.HOURLY.getType())){
			calc = employee.getRate()*calculateOvertime(employee.getHoursWorked());
			calc = Math.round(calc*100)*0.01;
		}else{
			throw new CalculationException("Employee "+employee.getTID()+" had invalid employment type.");
		}
		Tax tax = new Tax(calc*52);
		paye = tax.calculateTax()/52*100;
		paye = Math.round(paye)*0.01;
		checkForNegativeCurrency(calc,paye);
		return paye;
	}
	public void checkForNegativeCurrency(double testGross,double testPAYE) throws CalculationException{
		_errors = new StringBuilder();
			if(testGross<0){
				_errors.append("(Gross)");
			}
			if(testPAYE<0){
				_errors.append("(PAYE)");
			}
		if(_errors.length()!=0){
		throw new CalculationException("Invalid currency/currencies: "+_errors.toString());
		}
	}
}