package payroll;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class Payroll {
	public static void main(String[] args) {
		if (args.length != 2) {
			throw new RuntimeException("Please try again and enter two arguments, the file name and the process");
		}
		try {
			System.setIn(new FileInputStream(args[0]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		EmployeeRecord employeeRecord = new EmployeeRecord();
		employeeRecord.readInRecord();
		CheckRecord checkRecord = new CheckRecord();
		checkRecord.dataErrorCheck(employeeRecord);
		Processing processing = new Processing();
		processing.determineProcess(args[1], employeeRecord);
	}
}