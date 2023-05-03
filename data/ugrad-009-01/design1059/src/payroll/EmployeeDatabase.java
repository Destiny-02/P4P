package payroll;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class EmployeeDatabase {
	private List<Employee> _employeelist = new ArrayList<Employee>();
	private int _numberofemployees = 0;
	public EmployeeDatabase(String filedestination)
			throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(filedestination))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				while (((line.startsWith("#")) || (line.isEmpty()))
						&& (scanner.hasNextLine())) {
					line = scanner.nextLine();
				}
				if (!scanner.hasNextLine()) {
					break;
				}
				String[] splitline = line.split("\t");
				if (!errorCheck(splitline)) {
					if (splitline[2].equals("Hourly")) {
						HourlyEmployee employee = new HourlyEmployee(splitline);
						_employeelist.add(employee);
					} else {
						SalariedEmployee employee = new SalariedEmployee(
								splitline);
						_employeelist.add(employee);
					}
					_numberofemployees++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private boolean errorCheck(String[] splitline) {
		boolean output = false;
		if ((dollarSignErrorCheck(splitline)
				|| (dateFormatErrorCheck(splitline))
				|| (taxIDErrorCheck(splitline)) || (nameErrorCheck(splitline))
				|| (negativeNumbersErrorCheck(splitline)) || (dateErrorCheck(splitline)))) {
			output = true;
		}
		return output;
	}
	private boolean dollarSignErrorCheck(String[] splitline) {
		boolean output = false;
		if (!((splitline[3].startsWith("$")) && (splitline[4].startsWith("$")) && (splitline[8]
				.startsWith("$")))) {
			System.out.println("ERROR: For Employee " + splitline[1]
					+ " - Money amount does not begin with $");
			output = true;
		}
		return output;
	}
	private boolean dateFormatErrorCheck(String[] splitline) {
		boolean output = false;
		String[] datecheckstart = splitline[5].split("-");
		String[] datecheckend = splitline[6].split("-");
		if ((datecheckend.length != 3) && (datecheckstart.length != 3)) {
			System.out.println("ERROR: For Employee " + splitline[1]
					+ " - Dates in wrong format");
			output = true;
		}
		else if ((Integer.parseInt(datecheckstart[1]) < 1)
				|| (Integer.parseInt(datecheckstart[1]) > 12)
				|| (Integer.parseInt(datecheckend[1]) < 1)
				|| (Integer.parseInt(datecheckend[1]) > 12)) {
			System.out.println("ERROR: For Employee " + splitline[1]
					+ " - Dates in wrong format");
			output = true;
		}
		else if ((Integer.parseInt(datecheckstart[2]) < 1)
				|| (Integer.parseInt(datecheckstart[2]) > 31)
				|| (Integer.parseInt(datecheckend[2]) < 1)
				|| (Integer.parseInt(datecheckend[2]) > 31)) {
			System.out.println("ERROR: For Employee " + splitline[1]
					+ " - Dates in wrong format");
			output = true;
		}
		return output;
	}
	private boolean taxIDErrorCheck(String[] splitline) {
		boolean output = false;
		for (int i = 0; i < _numberofemployees; i++) {
			Employee employeecompare = _employeelist.get(i);
			if (splitline[0].equals(employeecompare.gettaxID())) {
				System.out.println("ERROR: For Employee " + splitline[1]
						+ " - TaxID not unique");
				output = true;
			}
		}
		return output;
	}
	private boolean nameErrorCheck(String[] splitline) {
		boolean output = false;
		String[] namecheck = splitline[1].split(", ");
		if (namecheck.length != 2) {
			System.out.println("ERROR: For Employee " + splitline[1]
					+ " - Name in wrong format");
			output = true;
		}
		return output;
	}
	private boolean negativeNumbersErrorCheck(String[] splitline) {
		boolean output = false;
		String a = splitline[3].replace("$", "");
		String b = splitline[4].replace("$", "");
		String c = splitline[7];
		String d = splitline[8].replace("$", "");
		if (((a.startsWith("-")) || (b.startsWith("-")) || (c.startsWith("-")) || (d
				.startsWith("-")))) {
			System.out.println("ERROR: For Employee " + splitline[1]
					+ " - Negative numbers");
			output = true;
		}
		return output;
	}
	private boolean dateErrorCheck(String[] splitline) {
		boolean output = false;
		String[] datecheckstart = splitline[5].split("-");
		String[] datecheckend = splitline[6].split("-");
		if (!(Integer.parseInt(datecheckend[0]) > Integer
				.parseInt(datecheckstart[0]))) {
			if (Integer.parseInt(datecheckend[0]) < Integer
					.parseInt(datecheckstart[0])) {
				System.out.println("ERROR: For Employee " + splitline[1]
						+ " - End date before start date");
				output = true;
			}
			else if (Integer.parseInt(datecheckend[1]) < Integer
					.parseInt(datecheckstart[1])) {
				System.out.println("ERROR: For Employee " + splitline[1]
						+ " - End date before start date");
				output = true;
			}
			else if (Integer.parseInt(datecheckend[1]) == Integer
					.parseInt(datecheckstart[1])) {
				if (Integer.parseInt(datecheckend[2]) < Integer
						.parseInt(datecheckstart[2])) {
					System.out.println("ERROR: For Employee " + splitline[1]
							+ " - End date before start date");
					output = true;
				}
			}
		}
		return output;
	}
	public void taxidsort() {
		Employee employeej;
		Employee employeek;
		Employee copy;
		for (int j = 1; j < _numberofemployees; j++) {
			employeej = _employeelist.get(j);
			for (int k = 0; k < j; k++) {
				employeek = _employeelist.get(k);
				int taxIDj = Integer.parseInt(employeej.gettaxID());
				int taxIDk = Integer.parseInt(employeek.gettaxID());
				if (taxIDj < taxIDk) {
					copy = _employeelist.get(j);
					_employeelist.set(j, employeek);
					_employeelist.set(k, copy);
				}
			}
		}
	}
	public void selectionSort() {
		Employee employeej;
		Employee employeek;
		Employee copy;
		String[] splitj;
		String[] splitk;
		for (int j = 1; j < _numberofemployees; j++) {
			employeej = _employeelist.get(j);
			for (int k = 0; k < j; k++) {
				employeek = _employeelist.get(k);
				splitj = employeej.getname().split(", ");
				splitk = employeek.getname().split(", ");
				if ((splitj[0].compareTo(splitk[0]) < 0)) {
					copy = _employeelist.get(j);
					_employeelist.set(j, employeek);
					_employeelist.set(k, copy);
				} else if ((splitj[0].compareTo(splitk[0]) == 0)) {
					if ((splitj[1].compareTo(splitk[1]) < 0)) {
						copy = _employeelist.get(j);
						_employeelist.set(j, employeek);
						_employeelist.set(k, copy);
					} else if (splitj[1].compareTo(splitk[1]) == 0) {
						int taxIDj = Integer.parseInt(employeej.gettaxID());
						int taxIDk = Integer.parseInt(employeek.gettaxID());
						if (taxIDj < taxIDk) {
							copy = _employeelist.get(j);
							_employeelist.set(j, employeek);
							_employeelist.set(k, copy);
						}
					}
				}
			}
		}
	}
	public List<Employee> getEmployeeList() {
		return _employeelist;
	}
	public int getNumberOfEmployees() {
		return _numberofemployees;
	}
	public Employee getEmployee(int index) {
		return _employeelist.get(index);
	}
	public void setEmployee(int index, Employee employee) {
		_employeelist.set(index, employee);
	}
}
