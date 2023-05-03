package payroll;
import payroll.Employee.EmployeeConstants;
public class Burden extends PayrollProcess{
	private String op;
	public Burden(){
		op = "";
	}
	public void process(AllEmployees all){
		Employee[] employees = all.getEmployeesByName();
		op = employees[0].getWorkingPeriod()+'\n';
		double burden = 0;
		for(Employee employee : employees){
			try{
			burden += doCalculation(employee);
			}catch(CalculationException ex){
				System.err.println("Employee TID "+employee.getTID()+" could not be processed: "+ex.getMessage());
			}
		}
		op += "Total Burden: $"+format(burden)+"\n";
		this.setOutput(op);
	}
	public double doCalculation(Employee employee) throws CalculationException{
		double calc;
		if(employee.getEmploymentType().toLowerCase().equals(EmployeeConstants.SALARY.getType())){
			calc = Math.round(employee.getRate()/EmployeeConstants.WEEKSINYEAR.getUnits()*100)*0.01;
		}else if(employee.getEmploymentType().toLowerCase().equals(EmployeeConstants.HOURLY.getType())){
			calc = employee.getRate()*calculateOvertime(employee.getHoursWorked());
			calc = Math.round(calc*100)*0.01;
		}else{
			throw new CalculationException("Employee "+employee.getTID()+" had invalid employment type.");
		}
		return checkForNegativeCurrency(calc);
	}
	public double checkForNegativeCurrency(double testGross) throws CalculationException{
			if(testGross<0){
				throw new CalculationException("Invalid currency: (Gross)");
			}
			return testGross;
	}
}