package payroll.input;
import payroll.Payroll;
import payroll.employees.EmployeeList;
import payroll.employees.EmployeeRecord;
import payroll.employees.EmployeeRecord.payBy;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class FileReader {
	payBy _paidBy;
	String _filename;
	EmployeeList _employeeList;
	java.io.FileReader _fileReader;
	Scanner _scanner;
	public boolean doesFileExist(String filename) {
		_filename = filename;
		try {
			_fileReader = new java.io.FileReader(_filename);
			_scanner = new Scanner(_fileReader);
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	public void readInTo(EmployeeList employeeList) {
		_employeeList = employeeList;
		while (_scanner.hasNext()) {
			String line = _scanner.nextLine();
			if (line.matches("#.*") || line.trim().length() < 1) {
				continue;
			} else {
				String[] data = line.split("\t");
				int TID = Integer.parseInt(data[0]);
				checkTID(TID, data[1]);
				String[] names = data[1].split(",");
				String given = names[1].trim();
				String family = names[0].trim();
				payBy paidBy;
				if (data[2].compareTo("Salaried") == 0) {
					paidBy = payBy.SALARY;
				} else {
					paidBy = payBy.HOURLY;
				}
				double rate = Payroll.roundCent(Double.parseDouble(data[3].substring(1)));
				double ytd = Payroll.roundCent(Double.parseDouble(data[4].substring(1)));
				String start = data[5];
				String end = data[6];
				double hours = Double.parseDouble(data[7]);
				double deduction = Payroll.roundCent(Double.parseDouble(data[8].substring(1)));
				_employeeList
						.add(new EmployeeRecord(TID, given, family, paidBy, rate, ytd, start, end, hours, deduction));
			}
		}
	}
	void checkTID(int TID, String name) {
		for (EmployeeRecord er : _employeeList) {
			if (er.taxID() == TID) {
				System.out.println("\n***ERROR***");
				System.out.println(name + " and " + er.lastFirstName() + " have the same tax ID: " + TID);
				System.out.println("***ERROR***");
			}
		}
	}
}