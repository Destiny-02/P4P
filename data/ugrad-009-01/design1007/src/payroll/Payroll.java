package payroll;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
public class Payroll {
	private String _fileName, _processType;
	private File _inputFile;
	private List<Employee> _employeeRecord;
	private Printer _printer;
	private FileReader _fr;
	private BufferedReader _br;
	public static void main(String[] args){
		Payroll payroll = new Payroll();
		payroll.initialise(args);
		payroll.readFile();
		payroll.printOutput();
	}
	private void initialise(String[] args) {
			_fileName = args[0];
			_processType = args[1];
		_inputFile = new File(_fileName);
		_employeeRecord = new Vector<Employee>();
		_printer = null;
	}
	private void readFile() {
		String line = "";
		try {
			_fr = new FileReader(_inputFile);
			_br = new BufferedReader(_fr);
			while ((line = _br.readLine()) != null) {
				createAndAddEmployee(_employeeRecord, line);
			}
			_br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File: " + _fileName + " not found");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Invalid String");
		}
	}
	private void printOutput() {
		switch (_processType) {
		case "Payslips":
		case "Employees":
			_employeeRecord.sort(new EmployeeComparator(_processType));
			_printer = new DetailsPrinter(_employeeRecord, _processType);
			break;
		case "Burden":
		case "PAYE":
			_printer = new TotalsPrinter(_employeeRecord, _processType);
			break;
		}
		_printer.print();
	}
	private void createAndAddEmployee(List<Employee> employeeRecord, String line) {
		Employee emp;
		char[] charArray = line.toCharArray();
		String[] stringArray = line.split("\\s++|,");
		if (stringArray[0].equals("")) {
		} else if (charArray[0] == '#') {
		} else {
			emp = new Employee(stringArray);
			employeeRecord.add(emp);
		}
	}
}
