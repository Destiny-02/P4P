package payroll;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class Payroll {
	private static String[] _records;
	private static EmployeeList _employeeArray;
	public static void main(String[] args) throws PayrollUserException {
		if(args.length!=2){
			throw new PayrollUserException("Incorrect number of arguements");
		}
		_records = fileReader(args[0]).toArray(new String[fileReader(args[0]).size()]);
		_employeeArray = new EmployeeList(_records);
		PayrollOutput output;
		switch(args[1]){
		case "Payslips":
			output = new PayslipProcessing(_employeeArray);
			output.process();
			break;
		case "Employees":
			output = new EmployeeProcessing(_employeeArray);
			output.process();
			break;
		case "Burden":
			output = new BurdenProcessing(_employeeArray);
			output.process();
			break;
		case "PAYE":
			output = new PAYEProcessing(_employeeArray);
			output.process();
			break;
		default:
			throw new PayrollUserException(args[1] + " is not a valid process input");
		}
	}
	private static ArrayList<String> fileReader(String fileName) throws PayrollUserException{
		File inputFile = new File(fileName);
		ArrayList<String> records = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if((line.length()==0) || (line.substring(0, 1).equals("#")) ){
					continue;
				}
				records.add(line);
			}
		} catch (FileNotFoundException e) {
			throw new PayrollUserException("File" + fileName + " can't be found.");
		} catch (IOException e) {
			throw new PayrollException("An input/output exception has occured for file "+ fileName);
		}
		return records;
	}
}
