package payroll;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import payroll.employeeInfo.Date;
import payroll.employeeInfo.EmployeeRecordList;
public class Payroll {
	public static void main(String[] args) {
		if (args.length <= 1) {
			System.out.println("Not enought args passed to executable");
			System.exit(0);
		}
		try {
			InputStream input = new FileInputStream(args[0]);
			RecordReader recordReader = new RecordReader(input);
			EmployeeRecordList recordList = recordReader.ReadRecord();
			input.close();
			String processCommand = args[1];
			String processedRecordList = "";
			if (processCommand.equals("Payslips")) {
				processedRecordList = recordList.PayslipProcessing();
			} else if (processCommand.equals("Employees")) {
				processedRecordList = recordList.EmployeeProcessing();
			} else if (processCommand.equals("Burden")) {
				processedRecordList = recordList.BurdenProcessing();
			} else if (processCommand.equals("PAYE")) {
				processedRecordList = recordList.PAYEProcessing();
			} else {
				System.out.println("Command not recognised.");
			}
			System.out.println(new Date().toString());
			System.out.print(processedRecordList);
			System.out.print(recordReader.PrintErrorLog());
		} catch (FileNotFoundException e) {
			System.out.println("File \"" + args[0] + "\" not found.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IO error occured closing \"" + args[0] + "\"");
			System.exit(0);
		}
	}
}