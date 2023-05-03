package payroll;
public class ProcessorFactory{
	public Processor getUserProcessingOption(String option){
		option = option.toLowerCase();
		if (option.equals("payslips")){
			return new PayslipsProcessor();
		} else if (option.equals("employees")){
			return new EmployeesProcessor();
		} else if (option.equals("burden")){
			return new BurdenProcessor();
		} else if (option.equals("paye")){
			return new PAYEProcessor();
		}
		return null;
	}
}

