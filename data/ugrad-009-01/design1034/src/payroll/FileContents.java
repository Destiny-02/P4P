package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class FileContents {
	private int _tid;
	private String _name;
	private String _employment;
	private String _rate;
	private String _ytd;
	private String _startDate;
	private String _endDate;
	private double _hours;
	private String _deduction;
	public ArrayList<Employee> getFileContents(String filepath) {
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		BufferedReader employeeFile = null;
		try {
			employeeFile = new BufferedReader(new FileReader(filepath));
			String currentLine;
			while ((currentLine = employeeFile.readLine()) != null) {
				String formattedLine = formatContents(currentLine);
				if (formattedLine.length() != 0) {
					fileContentsToArrayList(employeeList, formattedLine);
				}
			}
		} catch (IOException e) {
			System.out.println("An error has occurred processing input file.");
		}
		return employeeList;
	}
	private ArrayList<Employee> fileContentsToArrayList(ArrayList<Employee> employeeList, String formattedLine) throws IllegalArgumentException {
		String[] employeeDetails = formattedLine.split("\t");
		try {
			if (!employeeDetails[3].substring(0,1).equals("$") || !employeeDetails[4].substring(0,1).equals("$") ||
					!employeeDetails[8].substring(0,1).equals("$")) {
				throw new NumberFormatException();
			}
			_tid = Integer.parseInt(employeeDetails[0]);
			_name = employeeDetails[1];
			_employment = employeeDetails[2];
			_rate = employeeDetails[3].replaceAll("[$]", "");
			_ytd = employeeDetails[4].replaceAll("[$]", "");
			_startDate = employeeDetails[5];
			_endDate = employeeDetails[6];
			_hours = Double.parseDouble(employeeDetails[7]);
			_deduction = employeeDetails[8].replaceAll("[$]", "");
			if (_employment.equals("Salaried")) {
				employeeList.add(new SalariedEmployee(_tid, _name, _employment, _rate, _ytd, _startDate, _endDate, _hours, _deduction));
			} else if (_employment.equals("Hourly")) {
				employeeList.add(new HourlyPaidEmployee(_tid, _name, _employment, _rate, _ytd, _startDate, _endDate, _hours, _deduction));
			} else {
				throw new InstantiationException();
			}
		} catch (NumberFormatException nfe) {
			System.out.println("Format of a line does not meet the specifications given.");
		} catch (ArrayIndexOutOfBoundsException oob) {
			System.out.println("Missing employee detail in specified file");
		} catch (IllegalArgumentException iae) {
			System.out.println("Not a valid process, please select Payslips, Employees, Burden or PAYE.");
		} catch (ArithmeticException ae) {
			System.out.println("Nonsensical values resulted from information given (negative gross or greater deductions than earnings");
		} catch (InstantiationException ie) {
			System.out.println("An employee in the specified file is neither salaried or paid hourly.");
		}
		return employeeList;
		}
	private String formatContents(String rawLine) {
	if (rawLine.length() != 0 && rawLine.charAt(0) != '#') {
		return rawLine;
	}
	return "";
	}
}
