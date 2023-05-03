package payroll;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import payroll.employeeInfo.Date;
import payroll.employeeInfo.EmployeeRecord;
import payroll.employeeInfo.EmployeeRecordList;
import payroll.employeeInfo.EmploymentType;
public class RecordReader {
	private InputStream _input;
	private ErrorLog _errorLog;
	public RecordReader(InputStream input) {
		_input = input;
		_errorLog = new ErrorLog();
	}
	public EmployeeRecordList ReadRecord() {
		Scanner scan = new Scanner(_input);
		EmployeeRecordList recordList = new EmployeeRecordList();
		int currentLine = 0;
		while (scan.hasNextLine()) {
			currentLine++;
			EmployeeRecord r = readRecordLine(scan.nextLine(), currentLine);
			if (r != null)
			{
				if (recordList.AddEmployeeRecord(r)) {
					_errorLog.Add("The TaxID specified has already been used in this file and should be unique."
							+ " This lines input has been ignored for processing and should be corrected.", currentLine);
				}
			}
		}
		scan.close();
		return recordList;
	}
	private EmployeeRecord readRecordLine(String line, int lineNum) {
		EmployeeRecord employeeRecord;
		if (line.startsWith("#") || line.length() <= 0) {
			return null;
		}
		Scanner sc = new Scanner(line);
		Integer taxId = readTaxId(sc, lineNum);
		String name[] = readName(sc, lineNum);
		EmploymentType et = readEmploymentType(sc, lineNum);
		Float rate = readCashAmount(sc, lineNum);
		Float ytd = readCashAmount(sc, lineNum);
		Date start = readDate(sc, lineNum);
		Date end = readDate(sc, lineNum);
		Float hours = readFloat(sc, lineNum);
		Float deductions = readCashAmount(sc, lineNum);
		if (taxId == null || name == null || et == null || rate == null || ytd == null
				|| start == null || end == null || hours == null || deductions == null) {
			_errorLog.Add("Error in line. Line ignored.", lineNum);
			return null;
		}
		employeeRecord = new EmployeeRecord(
				name[1],
				name[0],
				taxId,
				rate,
				ytd,
				et,
				start,
				end,
				hours,
				deductions);
		sc.close();
		return employeeRecord;
	}
	private Integer readTaxId(Scanner scan, int line) {
		int taxId;
		try {
			taxId = scan.nextInt();
			if (taxId < 0) {
				_errorLog.Add("Tax Id is negative, this was accepted for processing but may be incorrect.", line);
			}
			return taxId;
		} catch (InputMismatchException e) {
			_errorLog.Add("TaxID could not be parsed to int type.", line);
		}
		return null;
	}
	private String[] readName(Scanner scan, int line) {
		String[] names = new String[2];
		names[0] = scan.next().replace(",", "");
		names[1] = scan.next().replace(",", "");
		if (names[0].length() == 0 || names[1].length() == 0) {
			_errorLog.Add("Name missing please check name is formatted as \"FamilyName, GivenName\".", line);
			return null;
		}
		return names;
	}
	private EmploymentType readEmploymentType(Scanner scan, int line) {
		String e = scan.next();
		if (e.toLowerCase().equals("salaried")) {
			return EmploymentType.Salaried;
		} else if (e.toLowerCase().equals("hourly")) {
			return EmploymentType.Hourly;
		} else {
			_errorLog.Add("Employment type is neither \"Salaried\" or \"Hourly\", input line ignored for processing.", line);
		}
		return null;
	}
	private Float readCashAmount(Scanner scan, int line) {
		String cash = scan.next();
		if (cash.contains("$")) {
			cash = cash.replace("$", "");
		} else {
			_errorLog.Add("Missing '$' symbol, for processing treated as cash value.", line);
		}
		try {
			float value = Math.round(Float.parseFloat(cash) * 100.0f) / 100.0f;
			if (value < 0) {
				_errorLog.Add("Cash value is negative. This was accepted for processing purposes but may need to be corrected", line);
			}
			return value;
		} catch (NumberFormatException e) {
			_errorLog.Add("Cash value could not be parsed to float type.", line);
		}
		return null;
	}
	private Date readDate(Scanner scan, int line) {
		String date = scan.next();
		String[] dateComponents = date.split("-");
		if (dateComponents.length != 3) {
			_errorLog.Add("Date does not have 3 components.", line);
		}
		try {
			return new Date(
					Integer.parseInt(dateComponents[0]),
					Integer.parseInt(dateComponents[1]),
					Integer.parseInt(dateComponents[2]));
		} catch (NumberFormatException e) {
			_errorLog.Add("Could not parse date component to int type", line);
		}
		return null;
	}
	private Float readFloat(Scanner scan, int line) {
		try {
			float value = scan.nextFloat();
			if (value < 0) {
				_errorLog.Add("Float value was less than zero. This was accepted for processing purposes but may need to be corrected", line);
			}
			return value;
		} catch (InputMismatchException e) {
			_errorLog.Add("Could not parse value to float type", line);
		}
		return null;
	}
	public ErrorLog GetRecordReadersErrorLog() {
		return _errorLog;
	}
	public String PrintErrorLog() {
		return _errorLog.toString();
	}
}
