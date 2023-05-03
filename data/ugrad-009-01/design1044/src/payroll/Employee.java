package payroll;
public abstract class Employee implements Comparable<Employee> {
	private int _TID;
	private String _employemntType;
	private String _familyName;
	private String _givenNames;
	private double _rate;
	private double _YTD;
	private PayrollDate _startDate;
	private PayrollDate _endDate;
	private double _hours;
	private double _deduction;
	private double _gross;
	private double _PAYE;
	private double _nett;
	private int _line;
	private final String[] _employeeData;
	protected double getGross() {
		return _gross;
	}
	protected double getPAYE() {
		return _PAYE;
	}
	protected int getTID() {
		return _TID;
	}
	protected String getFamilyName() {
		return _familyName;
	}
	protected String getGivenNames() {
		return _givenNames;
	}
	protected int getLineNumber() {
		return _line;
	}
	protected String[] getEmployeeData() {
		return _employeeData;
	}
	public Employee(String[] employeeData, int line, String filePath)
			throws PayrollDateFormatException, EmployeeDataException {
		_line = line;
		_employeeData = employeeData;
		try {
			_TID = Integer.valueOf(employeeData[0]);
		} catch (Exception e) {
			throw new EmployeeDataException(employeeData, line, filePath, "Invalid TID: \"" + employeeData[0] + "\"");
		}
		if (_TID < 1) {
			throw new EmployeeDataException(employeeData, line, filePath, "TID cannot be negative");
		}
		_employemntType = employeeData[2];
		if (!(_employemntType.equalsIgnoreCase("Salaried") || _employemntType.equalsIgnoreCase("Hourly"))) {
			throw new EmployeeDataException(employeeData, line, filePath,
					"Invalid employment type: \"" + employeeData[2] + "\"");
		}
		String[] names = employeeData[1].split(",");
		if (names.length != 2) {
			throw new EmployeeDataException(employeeData, line, filePath,
					"Invalid name/s: \"" + employeeData[1] + "\"");
		}
		_familyName = names[0];
		_givenNames = names[1].substring(1);
		if (!employeeData[3].contains("$")) {
			throw new EmployeeDataException(employeeData, line, filePath, "Invalid rate: \"" + employeeData[3] + "\"");
		}
		try {
			_rate = Double.valueOf(employeeData[3].substring(1));
		} catch (Exception e) {
			throw new EmployeeDataException(employeeData, line, filePath, "Invalid rate: \"" + employeeData[3] + "\"");
		}
		if (!employeeData[4].contains("$")) {
			throw new EmployeeDataException(employeeData, line, filePath, "Invalid YTD: \"" + employeeData[4] + "\"");
		}
		try {
			_YTD = Double.valueOf(employeeData[4].substring(1));
		} catch (Exception e) {
			throw new EmployeeDataException(employeeData, line, filePath, "Invalid YTD: \"" + employeeData[4] + "\"");
		}
		try {
			String date[] = employeeData[5].split("-");
			_startDate = new PayrollDate(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2]));
		} catch (Exception e) {
			throw new EmployeeDataException(employeeData, line, filePath, "Invalid date: \"" + employeeData[5] + "\"");
		}
		try {
			String date[] = employeeData[6].split("-");
			_endDate = new PayrollDate(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2]));
		} catch (Exception e) {
			throw new EmployeeDataException(employeeData, line, filePath, "Invalid date: \"" + employeeData[6] + "\"");
		}
		try {
			_hours = Double.valueOf(employeeData[7]);
		} catch (Exception e) {
			throw new EmployeeDataException(employeeData, line, filePath, "Invalid hours: \"" + employeeData[7] + "\"");
		}
		if (_hours < 0) {
			throw new EmployeeDataException(employeeData, line, filePath,
					"Hours cannot be negative: \"" + employeeData[7] + "\"");
		}
		try {
			_deduction = Double.valueOf(employeeData[8].substring(1));
		} catch (Exception e) {
			throw new EmployeeDataException(employeeData, line, filePath,
					"Invalid deduction: \"" + employeeData[8] + "\"");
		}
		if (_deduction < 0) {
			throw new EmployeeDataException(employeeData, line, filePath,
					"Deduction cannot be negative: \"" + employeeData[8] + "\"");
		}
		_gross = calculateGross(_hours, _rate);
		_PAYE = PAYETax.calculatePAYE(_gross);
		_nett = _gross - _PAYE - _deduction;
		if (_nett < 0) {
			throw new EmployeeDataException(employeeData, line, filePath,
					String.format("Nett pay ($%.2f) cannot be negative", _nett));
		}
		_YTD += _gross;
	}
	public abstract double calculateGross(double hours, double rate);
	public void printPayslipLine() {
		System.out.printf("\n%d. ", _TID);
		System.out.printf("%s %s, ", _givenNames, _familyName);
		System.out.printf("Period: %s to %s. ", _startDate.toString(), _endDate.toString());
		System.out.printf("Gross: $%.2f, ", _gross);
		System.out.printf("PAYE: $%.2f, ", _PAYE);
		System.out.printf("Deductions: $%.2f ", _deduction);
		System.out.printf("Nett: $%.2f ", _nett);
		System.out.printf("YTD: $%.2f", _YTD);
	}
	public void printEmployeeListLine() {
		System.out.printf("\n%s, %s (%d) %s, $%.2f YTD:$%.2f", _familyName, _givenNames, _TID, _employemntType, _rate,
				_YTD);
	}
	public PayrollDate[] period() {
		PayrollDate period[] = new PayrollDate[2];
		period[0] = _startDate;
		period[1] = _endDate;
		return period;
	}
}
