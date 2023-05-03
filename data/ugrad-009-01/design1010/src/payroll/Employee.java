package payroll;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public abstract class Employee {
	private int _taxID;
	private String _firstName, _lastName, _employeeType, _startDate, _endDate;
	protected float _hoursWorked, _rate;
	private float _deductions, _YTD;
	private String[] _stringInput;
	public Employee(String stringInput) {
		_stringInput = stringInput.split("\t");
	}
	public void initialiseEmployee() throws PayrollException {
		if (_stringInput.length < 9) {
			throw new PayrollException("Missing Data");
		}
		if ((_stringInput[3].contains("$") == false) || (_stringInput[4].contains("$") == false) || (_stringInput[8].contains("$") == false)) {
			throw new PayrollException("Money amount in invalid format");
		}
		if (_stringInput[1].contains(", ") == false) {
			throw new PayrollException("Employee name is in invalid format - must be Lastname(comma)(space)Firstname");
		}
		String[] fullName = _stringInput[1].split(", ");
		_lastName = fullName[0];
		_firstName = fullName[1];
		_employeeType = _stringInput[2];
		if (!(_employeeType.equals("Salaried") || _employeeType.equals("Hourly"))) {
			throw new PayrollException("Employee type is invalid");
		}
		try {
			_taxID = Integer.parseInt(_stringInput[0]);
			_rate = Float.parseFloat(_stringInput[3].replace("$", ""));
			_YTD = Float.parseFloat(_stringInput[4].replace("$", ""));
			_hoursWorked = Float.parseFloat(_stringInput[7]);
			_deductions = Float.parseFloat(_stringInput[8].replace("$", ""));
		} catch (NumberFormatException nfe) {
			throw new PayrollException("A Number in the input is in an invalid format");
		}
		if (_rate < 0) {
			throw new PayrollException("Employee's Rate/Salary is negative - Invalid");
		}
		if (_deductions < 0) {
			throw new PayrollException("Employee's deductions are negative - Invalid");
		}
		if (_YTD < 0) {
			throw new PayrollException("Employee's YTD value is negative - Invalid");
		}
		_startDate = _stringInput[5];
		_endDate = _stringInput[6];
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = dateFormat.parse(_startDate);
			Date date2 = dateFormat.parse(_endDate);
			if (date1.compareTo(date2) > 0) {
				throw new PayrollException("Start date is before end date - Invalid");
			}
		} catch (ParseException pe) {
			throw new PayrollException("Date is in invalid format");
		}
	}
	public float calculateTax() {
		DecimalFormat rounder = new DecimalFormat("#.##");
		float rate = calculatePay()*52;
		float tax = 0;
		if (rate > 70000) {
			float fract = rate - 70000;
			tax += Float.parseFloat(rounder.format(fract * 0.33));
			rate = 70000;
		}
		if (rate > 48000) {
			float fract = rate - 48000;
			tax += Float.parseFloat(rounder.format(fract * 0.30));
			rate = 48000;
		}
		if (rate > 14000) {
			float fract = rate - 14000;
			tax += Float.parseFloat(rounder.format(fract * 0.175));
			rate = 14000;
		}
		if (rate > 0) {
			tax += Float.parseFloat(rounder.format(rate * 0.105));
		}
		return Float.parseFloat(rounder.format(tax/52));
	}
	public float calculateNet() throws PayrollException {
		float net = calculatePay() - calculateTax() - _deductions;
		if (net < 0) {
			throw new PayrollException("Net amount to pay is negative - Invalid");
		}
		return net;
	}
	public String startDateToEndDate() {
		return _startDate + " to " + _endDate;
	}
	public String buildEmployeeSummary() {
		return String.format("%s, %s (%d) %s, $%.2f YTD:$%.2f", _lastName, _firstName, _taxID, _employeeType, _rate, (_YTD + calculatePay()));
	}
	public String buildPayslip() throws PayrollException {
		return String.format("%d. %s %s, Period: %s to %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f", _taxID, _firstName, _lastName, _startDate, _endDate, calculatePay(), calculateTax(), _deductions, calculateNet(), (_YTD + calculatePay()));
	}
	public String getFirstName() {
		return _firstName;
	}
	public String getLastName() {
		return _lastName;
	}
	public int getTaxID() {
		return _taxID;
	}
	abstract float calculatePay();
}
