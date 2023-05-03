package payroll;
public class ProcessingFactory {
	public Processing getProcessingObject(String cmd)throws PayrollUserException{
		switch(cmd){
		case "Payslips":
			return new PayslipsProcessing();
		case "Employees":
			return new EmployeesProcessing();
		case "Burden":
			return new BurdenProcessing();
		case "PAYE":
			return new PAYEProcessing();
		default:
			throw new PayrollUserException("\nError: Input Argument Error. " + "\""+ cmd +"\""+ " is not a valid operation.");
		}
	}
}
