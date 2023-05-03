package payroll;
public class Processing{
	public static Command returnProcessObject(String process){
		if (process.equals("Employees")){
			return new EmployeeList.Processes.EmployeesProcesses();
		} else if (process.equals("Payslips")){
			return new EmployeeList.Processes.PayslipsProcess();
		} else if (process.equals("Burden")){
			return new EmployeeList.Processes.BurdenProcess();
		} else if (process.equals("PAYE")){
			return new EmployeeList.Processes.PAYEProcess();
		} else{
			throw new EmployeeException("This process is not a valid process!");
		}
	}
}
