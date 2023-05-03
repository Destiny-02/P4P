package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
public class ProcessData {
	int _employeeCount = 1;
	private int _TID;
	private String[] _fullName;
	private String _employement;
	private float _rate;
	private float _YTD;
	private String _start;
	private String _end;
	private float _hours;
	private float _deductions;
	public Vector<Employee> processEmployees(String fileName) throws IOException {
		Vector<Employee> employeeList = new Vector<Employee>();
		BufferedReader stdin = new BufferedReader(new FileReader(fileName));
		String in = stdin.readLine();
		while (in != null) {
			if (in.contains("#") || in.isEmpty()) {
				in = stdin.readLine();
				continue;
			}
			String[] employeeData = in.split("\t");
			try {
				_TID = processTID(employeeData);
				_fullName = processNames(employeeData);
				_employement = processEmployment(employeeData);
				_rate = processRate(employeeData);
				_YTD = processYTD(employeeData);
				_start = processStart(employeeData);
				_end = processEnd(employeeData);
				_hours = processHours(employeeData);
				_deductions = processDeductions(employeeData);
			} catch (MissingDataException mde) {
				mde.printStackTrace();
			} catch (InvalidDataException ide) {
				ide.printStackTrace();
			}
			Employee employee = new Employee(_TID, _fullName, _employement, _rate, _YTD, _start, _end, _hours, _deductions);
			_employeeCount++;
			employeeList.add(employee);
			in = stdin.readLine();
		}
		stdin.close();
		try {
			samePayPeriod(employeeList);
		} catch (InvalidDataException ide) {
			ide.printStackTrace();
		}
		return employeeList;
	}
	private int processTID(String[] employeeData) throws MissingDataException, InvalidDataException {
		if (employeeData[0].isEmpty()) {
			throw new MissingDataException("No Tax ID Number entered for employee entry: "+_employeeCount+" in input file");
		} else if (Integer.parseInt(employeeData[0]) <= 0) {
			throw new InvalidDataException("Negative Tax ID Number entered for employee entry: "+_employeeCount+" in input file");
		} else {
			return Integer.parseInt(employeeData[0]);
		}
	}
	private String[] processNames(String[] employeeData) throws MissingDataException {
		if (employeeData[1].isEmpty() || employeeData[1].isEmpty()) {
			throw new MissingDataException("Fullname not entered for employee entry: "+_employeeCount+" in input file");
		} else if (employeeData[1].replaceAll(" ", "").split(",").length == 1){
			throw new MissingDataException("Only one name of employee entry: "+_employeeCount+" in input file provided. Please provide both Family AND Given name");
		} else {
			return employeeData[1].replaceAll(" ", "").split(",");
		}
	}
	private String processEmployment(String[] employeeData) throws MissingDataException, InvalidDataException {
		if (employeeData[2].isEmpty()) {
			throw new MissingDataException("No Employment Type entered for employee entry: "+_employeeCount+" in input file");
		} else {
			if (employeeData[2].equals("Salaried")) {
				return "Salaried";
			} else if (employeeData[2].equals("Hourly"))  {
				return "Hourly";
			} else {
				throw new InvalidDataException("Invalid Employement type (i.e. is not Salaried or Hourly)");
			}
		}
	}
	private float processRate(String[] employeeData) throws MissingDataException, InvalidDataException {
		if (employeeData[3].isEmpty()) {
			throw new MissingDataException("No Rate entered for employee entry: "+_employeeCount+" in input file");
		} else if (Float.parseFloat(employeeData[3].replace("$", "")) < 0){
			throw new InvalidDataException("Negative rate entered for employee entry: "+_employeeCount+" in input file");
		} else {
			return Float.parseFloat(employeeData[3].replace("$", ""));
		}
	}
	private float processYTD(String[] employeeData) throws MissingDataException, InvalidDataException {
		if (employeeData[4].isEmpty()) {
			throw new MissingDataException("No YTD entered for employee entry: "+_employeeCount+" in input file");
		} else if (Float.parseFloat(employeeData[4].replace("$", "")) < 0){
			throw new InvalidDataException("Negative TYD entered for employee entry: "+_employeeCount+" in input file");
		} else {
			return Float.parseFloat(employeeData[4].replace("$", ""));
		}
	}
	private String processStart(String[] employeeData) throws MissingDataException {
		if (employeeData[5].isEmpty()) {
			throw new MissingDataException("No start date specified for employee entry: "+_employeeCount+" in input file");
		} else {
			return employeeData[5];
		}
	}
	private String processEnd(String[] employeeData)throws MissingDataException {
		if (employeeData[6].isEmpty()) {
			throw new MissingDataException("No end data specified for employee entry: "+_employeeCount+" in input file");
		} else {
			return employeeData[6];
		}
	}
	private float processHours(String[] employeeData) throws MissingDataException, InvalidDataException {
		if (employeeData[7].isEmpty()) {
			throw new MissingDataException("No hours entered for employee entry: "+_employeeCount+" in input file");
		} else if (Float.parseFloat(employeeData[7]) < 0) {
			throw new InvalidDataException("Negative hours entered for employee entry: "+_employeeCount+" in input file");
		} else {
			return Float.parseFloat(employeeData[7]);
		}
	}
	private float processDeductions(String[] employeeData) throws MissingDataException, InvalidDataException {
		if (employeeData[8].isEmpty()) {
			throw new MissingDataException("No deductions entered for employee entry: "+_employeeCount+" in input file");
		} else if (Float.parseFloat(employeeData[8].replace("$", "")) < 0){
			throw new InvalidDataException("Negative deductions entered for employee entry: "+_employeeCount+" in input file");
		} else {
			return Float.parseFloat(employeeData[8].replace("$", ""));
		}
	}
	private void samePayPeriod(Vector<Employee> list) throws InvalidDataException {
		for (int i = 0; i < list.size() - 1; i++) {
			String[] e1payPeriod = list.get(i).getPayPeriod(list.get(i));
			String[] e2payPeriod = list.get(i+1).getPayPeriod(list.get(i+1));
			if (!e1payPeriod[0].equals(e2payPeriod[0])) {
				throw new InvalidDataException("Inconsistent start dates");
			}
			if (!e1payPeriod[1].equals(e2payPeriod[1])) {
				throw new InvalidDataException("Inconsistent end dates");
			}
		}
	}
}