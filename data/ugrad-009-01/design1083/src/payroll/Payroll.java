package payroll;
public class Payroll {
	public static void main(String[] args){
		EmployeeList employeeList = new EmployeeList(args);
		Processor preferredProcessor = new ProcessorFactory().getUserProcessingOption(args[1]);
		preferredProcessor.process(employeeList);
		preferredProcessor.displayProcessedList();
	}
}	
