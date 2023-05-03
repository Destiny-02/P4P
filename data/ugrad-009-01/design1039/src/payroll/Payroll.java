package payroll;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class Payroll {
	public static void main(String[] args) {
		String fileName = args[0];
		String line = null;
		int lineLength = 0;
		try {
			LineNumberReader lnr;
			lnr = new LineNumberReader(new FileReader(new File(fileName)));
			lnr.skip(Long.MAX_VALUE);
			lineLength = (lnr.getLineNumber() + 1);
			lnr.close();
			String[] allEmployeeStrings = new String[lineLength];
			int employeeCount = 0;
			FileReader fileReader =
					new FileReader(fileName);
			BufferedReader bufferedReader =
					new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				if ((line.indexOf("#") == -1) && (line.trim().length() > 0)) {
					allEmployeeStrings[employeeCount] = line;
					employeeCount++;
				}
			}
			bufferedReader.close();
			Employee[] employeeInfo = new Employee[employeeCount];
			for (int i = 0; i < employeeCount; i++) {
				Employee currentEmployee = new Employee();
				Scanner scanner = new Scanner(allEmployeeStrings[i]);
				currentEmployee.fillInfoFromScanner(scanner);
				currentEmployee.checkNegative();
				employeeInfo[i] = currentEmployee;
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			if (args[1].equals("Payslips")) {
				PayslipsProcessing[] payslipsArray = new PayslipsProcessing[employeeCount];
				for (int i = 0; i < employeeCount; i++) {
					Employee currentEmployee = new Employee();
					currentEmployee = employeeInfo[i];
					PayslipsProcessing currentPayslip = new PayslipsProcessing(currentEmployee);
					payslipsArray[i] = currentPayslip;
				}
				payslipsArray[0].reorganize(payslipsArray);
				for (int i = 0; i < employeeCount; i++){
					payslipsArray[i].publishInfo();
				}
			}
			else if (args[1].equals("Employees")) {
				EmployeesProcessing[] emProcessingArray = new EmployeesProcessing[employeeCount];
				for (int i = 0; i < employeeCount; i++) {
					Employee currentEmployee = new Employee();
					currentEmployee = employeeInfo[i];
					EmployeesProcessing currentEmProcessing = new EmployeesProcessing(currentEmployee);
					emProcessingArray[i] = currentEmProcessing;
				}
				emProcessingArray[0].reorganize(emProcessingArray);
				for (int i = 0; i < employeeCount; i++) {
					emProcessingArray[i].publishInfo();
				}
			}
			else if (args[1].equals("Burden")) {
				BurdenAndPAYEProcessing burden = new BurdenAndPAYEProcessing(employeeInfo[0]);
				burden.setType("Burden");
				burden.calculateVariable(employeeInfo);
				burden.publishInfo();
			}
			else if (args[1].equals("PAYE")) {
				BurdenAndPAYEProcessing PAYE = new BurdenAndPAYEProcessing(employeeInfo[0]);
				PAYE.setType("PAYE");
				PAYE.calculateVariable(employeeInfo);
				PAYE.publishInfo();
			}
			else {
				throw new IllegalArgumentException();
			}
		}
		catch(FileNotFoundException ex) {
			System.out.println(
					"Unable to open file '" + fileName + "'");
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" + fileName + "'");
		}
		catch(IllegalArgumentException ex) {
			System.out.println("ERROR: Not a valid type of processing");
		}
		catch(IllegalStateException ex) {
			System.out.println("ERROR: Negative value detected in input file '" + fileName + "'");
		}
	}
}