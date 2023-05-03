package payroll;
public class Employee {
	private static final int TAXID, LASTNAME, FIRSTNAME, EMPLOYMENT, RATE, YTD, STARTDATE, ENDDATE, DEDUCTION, HOURS;
	private int _taxID;
	private String[] _stringArray;
	private String _lastName, _firstName, _employment, _startDate, _endDate, _payperiod;
	private double _rate, _yearToDate, _deduction, _hours, _gross, _nett, _paye;
	private Processor processor;
	public Employee(String[] stringArray) {
		_stringArray = stringArray;
		processor = new Processor();
		determineTaxID();
		determineLastName();
		determineFirstName();
		determineEmployment();
		determineRate();
		determineYTD();
		determineStartDate();
		determineEndDate();
		determineHours();
		determineDeduction();
		determineDate();
		_gross = processor.calculateGross(_rate, _hours, _employment);
		_paye = processor.calculatePAYE(_gross);
		_nett = processor.calculateNett(_gross, _paye, _deduction);
		_yearToDate = processor.calculateYTD(_yearToDate, _gross);
	}
	static {
		TAXID = 0;
		LASTNAME = 1;
		FIRSTNAME = 3;
		EMPLOYMENT = 4;
		RATE = 5;
		YTD = 6;
		STARTDATE = 7;
		ENDDATE = 8;
		HOURS = 9;
		DEDUCTION = 10;
	}
	private void determineTaxID() {
		_taxID = Integer.parseInt(_stringArray[TAXID]);
	}
	private void determineLastName() {
		_lastName = _stringArray[LASTNAME];
	}
	private void determineFirstName() {
		_firstName = _stringArray[FIRSTNAME];
	}
	private void determineEmployment() {
		_employment = _stringArray[EMPLOYMENT];
	}
	private void determineRate() {
		String rate = _stringArray[RATE].replace("$", "");
		_rate = Double.parseDouble(rate);
	}
	private void determineYTD() {
		String ytd = _stringArray[YTD].replace("$", "");
		_yearToDate = Double.parseDouble(ytd);
	}
	private void determineStartDate() {
		_startDate = _stringArray[STARTDATE];
	}
	private void determineEndDate() {
		_endDate = _stringArray[ENDDATE];
	}
	private void determineHours() {
		_hours = Double.parseDouble(_stringArray[HOURS]);
	}
	private void determineDeduction() {
		String deduction = _stringArray[DEDUCTION].replace("$", "");
		_deduction = Double.parseDouble(deduction);
	}
	private void determineDate() {
		_payperiod = _startDate + " to " + _endDate;
	}
	public String getPayperiod() {
		return _payperiod;
	}
	public String format (double value) {
		return "$" + String.format("%.2f", value);
	}
	public double getTotalsPrintInfo(String processType) {
		double info = 0;
		if (processType.equals("Burden")) {
			info = _gross;
		} else if (processType.equals("PAYE")) {
			info = _paye;
		}
		return info;
	}
	public String getDetailedPrintInfo(String processType) {
		String info = "";
		if (processType.equals("Payslips")) {
			info += _taxID + "." + " " + _firstName + " " + _lastName + ", Period: " + _payperiod + ". Gross: "
					+ format(_gross) + ", PAYE: " + format(_paye) + ", Deductions: " + format(_deduction) +
					" Nett: " + format(_nett) + " YTD: " + format(_yearToDate);
		} else if (processType.equals("Employees")) {
			info += _lastName + ", " + _firstName + " (" + _taxID + ") " + _employment + ", " + format(_rate) +
					" YTD:" + format(_yearToDate);
		}
		return info;
	}
	public int compareTaxID(Employee emp) {
		if (_taxID > emp._taxID) {
			return 1;
		} else if (_taxID < emp._taxID) {
			return -1;
		} else{
			return 0;
		}
	}
	public int compareName(Employee emp) {
		if(_lastName.equals(emp._lastName)) {
			if(_firstName.equals(emp._firstName)) {
				return compareTaxID(emp);
			} else {
				return _firstName.compareTo(emp._firstName);
			}
		} else {
			return _lastName.compareTo(emp._lastName);
		}
	}
}

