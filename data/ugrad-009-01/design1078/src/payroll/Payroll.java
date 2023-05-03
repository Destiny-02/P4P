package payroll;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import payroll.Processing.Processes;
public class Payroll {
	public static void main(String[] args) throws InvalidEmployeeRecordException {
		String fileName = args[0];
		String proessingName = args[1];
		Processing processing = new Processing();
		Processes processes;
		if (processing.isValidProcess(proessingName.toUpperCase())){
			processes = Processes.valueOf(proessingName.toUpperCase());
		} else {
			processes = null;
			throw new InvalidEmployeeRecordException("Invalid type of Processing: "+ proessingName +
					", please enter: Payslips, Employees, PAYE or Burden.");
		}
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		EmployeeRecordReader readRecords = new EmployeeRecordReader();
		employeeList = readRecords.readRecords(fileName, employeeList);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		processing.displayProcess(employeeList, processes);
	}
}