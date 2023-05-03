package payroll;
import java.text.DecimalFormat;
public class Employee {
	private final int _ID;
	private final String _lastName;
	private final String _firstName;
	private final Boolean _isSalaried;
	private final double _rate;
	private final double _YTD;
	private final String _start;
	private final String _end;
	private final float _hours;
	private final double _deductions;
	private final double _gross;
	private final double _PAYE;
	private final double _nett;
	public Employee(String[] employeeInfo) {
		_ID = Integer.parseInt(employeeInfo[0]);
		_lastName = employeeInfo[1].trim();
		_firstName = employeeInfo[2].trim();
		if (employeeInfo[3].equals("Salaried")) {
			_isSalaried = true;
		}
		else {
			_isSalaried = false;
		}
		_rate = Float.parseFloat(employeeInfo[4]);
		_start = employeeInfo[6].trim();
		_end = employeeInfo[7].trim();
		_hours = Float.parseFloat(employeeInfo[8]);
		_deductions = Float.parseFloat(employeeInfo[9]);
		_gross = calculateGross();
		_YTD = Float.parseFloat(employeeInfo[5]) + _gross;
		_PAYE = IncomeTax.calculateTax(_gross, _isSalaried);
		_nett = _gross - _PAYE - _deductions;
	}
	public int getIDCopy() {
		int IDCopy = _ID;
		return IDCopy;
	}
	public String getLastNameCopy () {
		String lastNameCopy = _lastName;
		return lastNameCopy;
	}
	public String getFirstNameCopy () {
		String firstNameCopy = _firstName;
		return firstNameCopy;
	}
	public double getGrossCopy() {
		double grossCopy = _gross;
		return grossCopy;
	}
	public double getPAYECopy() {
		double PAYECopy = _PAYE;
		return PAYECopy;
	}
	public double calculateGross() {
		if (_isSalaried) {
			return _rate/52;
		}
		double[][] hourlyRates = {{40, 1}, {3, 1.5}, {1000, 2}};
		double hoursLeft = _hours;
		double gross = 0;
		for (int index = 0; index < hourlyRates.length; index++) {
			if ((hoursLeft/hourlyRates[index][0]) >= 1) {
				gross += hourlyRates[index][0] * hourlyRates[index][1] * _rate;
				hoursLeft -= hourlyRates[index][0];
			}
			else {
				gross += hourlyRates[index][1] * hoursLeft * _rate;
				hoursLeft = 0;
			}
		}
		return gross;
	}
	public String generateReport(String reportType, Double total) {
		String rate = checkRoundFormat(_rate);
		String gross = checkRoundFormat(_gross);
		String PAYE = checkRoundFormat(_PAYE);
		String deductions = checkRoundFormat(_deductions);
		String nett = checkRoundFormat(_nett);
		String YTD = checkRoundFormat(_YTD);
		if (reportType.equals("Payslips")) {
			String employeePayslip = "" + _ID + ". " + _firstName + " " + _lastName + ", " + "Period: " + _start + " to " + _end + ". " + "Gross: $" + gross + ", PAYE: $" + PAYE + ", Deductions: $" + deductions + " Nett: $" + nett + " YTD: $" + YTD;
			return employeePayslip;
		}
		if (reportType.equals("Employees")) {
			String rateType;
			if (_isSalaried) {
				rateType = "Salaried";
			}
			else {
				rateType = "Hourly";
			}
			String employeeReport =_lastName + ", " + _firstName + " (" + _ID + ") " + rateType + ", $" + rate + " YTD:$" + YTD;
			return employeeReport;
		}
		if (reportType.equals("Burden")) {
			String formattedBurden = checkRoundFormat(total);
			String burdenReport = _start + " to " + _end + "\nTotal Burden: $" + formattedBurden;
			return burdenReport;
		}
		if (reportType.equals("PAYE")) {
			String formattedPAYE = checkRoundFormat(total);
			String PAYEReport = _start + " to " + _end + "\nTotal PAYE: $" + formattedPAYE;
			return PAYEReport;
		}
		else return "ERROR process name has no match";
	}
	public String checkRoundFormat(double input) {
		if (input < 0) {
			System.out.println("ERROR record value is negative for ID: " + _ID);
		}
		DecimalFormat df = new DecimalFormat("0.00");
		String output = df.format(Math.round(input * 100.0) / 100.0);
		return output.trim();
	}
}
