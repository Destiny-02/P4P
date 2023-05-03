package payroll;
public class Payroll {
	public static void  main (String[] args){
		if(args.length==0|| args.length==1){
			System.out.println("Not enough input arguments! Please try again");
		} else{
			InputProcessing inputProcessor = new InputProcessing(args[0]);
			EmployeeRecords employeeData = new EmployeeRecords();
			employeeData=inputProcessor.getInputInfo();
			inputProcessor.runProcessType(args[1], employeeData);
		}
	}
}
