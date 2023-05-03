package payroll;
public class Payroll {
	public static void main(String[] args) {
		if (args.length == 2){
			ProcessInput processInput = new ProcessInput(args[0], args[1]);
			Form form = processInput.createCurrentForm();
			form.processForm();
			PrintOutput outputForm = new PrintOutput(form, processInput.getEmployeeRecords());
			outputForm.printForm();
			if (processInput.anyErrors()){
				ErrorLog errorLog = processInput.getErrorLog();
				errorLog.printErrorLog();
			}
		} else {
			System.out.println("You have not provided valid arguments.\nPlease provide a file directory and a proper process.");
		}
	}
}
