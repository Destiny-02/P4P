package payroll;
import java.lang.Integer;
import java.lang.Double;
import java.lang.Math;
import java.lang.String;
public class Employee {
	private int _taxID;
	private String _firstName;
	private String _lastName;
	private String _startDate;
	private String _endDate;
	private String _employmentType;
	private double _rate;
	private double _deductions;
	private double _hours;
	private double _YTD;
	private double _PAYE;
	private double _nett;
	private double _gross;
	private String _period;
	public Employee(String inputtedLine) {
		String[] data = inputtedLine.split("\t");
		_taxID = Integer.parseInt(data[0]);
		assignNames(data[1]);
		_employmentType = data[2];
		_rate = assignData(data[3]);
		_YTD = assignData(data[4]);
		_startDate = data[5];
		_endDate = data[6];
		convertOvertime(data[7]);
		_deductions = assignData(data[8]);
		createPeriod();
		calculateGross();
		calculatePAYE();
		calculateNett();
		updateYTD();
	}
	private double assignData(String number) {
		number = number.replace("$", "");
		return Double.parseDouble(number);
	}
	private void assignNames(String name) {
		String names[] = name.split(",");
		_lastName = names[0];
		_firstName = names[1].trim();
	}
	private void convertOvertime(String hours) {
		double normalHours = assignData(hours);
		if (normalHours <= 40) {
			_hours = normalHours;
		} else if (normalHours <= 43) {
			normalHours = 40 + ((normalHours - 40) * 1.5);
			_hours = Math.round(normalHours*4.0)/4.0;
		} else {
			normalHours = 40 + 4.5 + ((normalHours - 43) * 2.0);
			_hours = Math.round(normalHours*4.0)/4.0;
		}
	}
	private void createPeriod() {
		_period = (_startDate + " to " + _endDate);
	}
	private void calculateGross() {
		if (_employmentType.equals("Salaried")) {
			_gross = _rate / 52.0;
		} else {
			_gross =_rate * _hours;
		}
	}
	private void calculatePAYE() {
		double yearlyEarnings;
		if (_employmentType.equals("Hourly")) {
			yearlyEarnings = _rate * _hours * 52;
		} else {
			yearlyEarnings = _rate;
		}
		if (yearlyEarnings <= 14000) {
			_PAYE =yearlyEarnings * 0.105 / 52;
		} else if (yearlyEarnings <= 48000) {
			_PAYE = (1470 + ((yearlyEarnings - 14000) * 0.175)) / 52;
		} else if (yearlyEarnings <= 70000) {
			_PAYE = (1470 + 5950 + ((yearlyEarnings - 48000) * 0.3)) / 52;
		} else {
			_PAYE = (1470 + 5950 + 6600 + ((yearlyEarnings - 70000) * 0.33)) / 52;
		}
	}
	private void calculateNett() {
		_nett = _gross - _PAYE - _deductions;
		if (_nett < 0) {
			System.out.println("Employee ID:" + _taxID + " has earned less than their deductions.");
		}
	}
	private void updateYTD() {
		_YTD += _gross;
	}
	private boolean compareID(Employee E) {
		if (this._taxID >= E._taxID) {
			return true;
		} else {
			return false;
		}
	}
	private boolean compareName(Employee E) {
		if (this._lastName.compareToIgnoreCase(E._lastName) > 0) {
			return true;
		} else if (this._lastName.compareToIgnoreCase(E._lastName) < 0) {
			return false;
		} else {
			if (this._firstName.compareToIgnoreCase(E._firstName) >= 0) {
				return true;
			} else {
				return this.compareID(E);
			}
		}
	}
	public boolean compare(Employee E, String processType) {
		if (processType.equals("Employees")) {
			return this.compareName(E);
		} else {
			return this.compareID(E);
		}
	}
	public String returnGrossAsString() {
		String stringGross = String.valueOf(_gross);
		return stringGross;
	}
	public String returnPAYEAsString() {
		String stringPAYE = String.valueOf(_PAYE);
		return stringPAYE;
	}
	public String constructPayslip() {
		String payslip = String.format("%d. %s %s, Period: %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f",
				_taxID, _firstName, _lastName, _period, _gross, _PAYE, _deductions, _nett, _YTD);
		return payslip;
	}
	public String constructEmployeeDescription() {
		String description = String.format("%s, %s (%d) %s, $%.2f YTD:$%.2f", _lastName, _firstName, _taxID, _employmentType, _rate, _YTD);
		return description;
	}
	public String giveCopyOfPeriod() {
		String period = _period;
		return period;
	}
}
