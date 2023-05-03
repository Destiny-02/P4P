
package payroll;
public class Payroll {
	public static void main(String[] args) {
		CommandParser command = new CommandParser(args);
		FileIO file = new FileIO(command.getFileString());
		file.openFile();
		FileReader reader = new FileReader(file);
		EmployeeList employeeList = new EmployeeList(reader);
		Processer processer = new Processer(command.getProcessingString());
		processer.printProcessedOutput(employeeList);
		file.closeFile();
	}
}
