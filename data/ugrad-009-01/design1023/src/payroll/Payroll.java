package payroll;
public class Payroll {
	public static void main(String[] args) {
		FileData file = new FileData();
		EmployeeRecord records = new EmployeeRecord(file.readFile(args));
		ProcessedRecord processedRecord = new ProcessedRecord(records);
		Command cmd = new Command();
		Process process = cmd.determineProcess(args);
		process.execute(processedRecord);
		process.display();
	}
}
