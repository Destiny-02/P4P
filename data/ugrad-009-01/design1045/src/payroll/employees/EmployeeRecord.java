package payroll.employees;
import payroll.Payroll;
public class EmployeeRecord {
	public enum payBy {
		HOURLY, SALARY
	};
	payBy _paidBy;
	int _tID;
	String _familyName;
	String _givenName;
	double _rate;
	double _yTD;
	String _start;
	String _end;
	double _hours;
	double _deductions;
	public static final double WeeksPerYear = 52;
	public EmployeeRecord(int tID, String given, String family, payBy paidBy, double rate, double ytd, String start,
			String end, double hours, double deduction) {
		_paidBy = paidBy;
		_tID = tID;
		_familyName = family;
		_givenName = given;
		_rate = rate;
		_yTD = ytd;
		_start = start;
		_end = end;
		_hours = Math.round(hours * 4) / 4.0;
		_deductions = deduction;
	}
	public double calcGross() {
		switch (_paidBy) {
		case HOURLY:
			double gross = 0;
			double hours = _hours;
			if (hours > 43) {
				gross += Payroll.roundCent((hours - 43) * 2.0 * _rate);
				hours = 43;
			}
			if (hours > 40) {
				gross += Payroll.roundCent((hours - 40) * 1.5 * _rate);
				hours = 40;
			}
			gross += Payroll.roundCent(hours * _rate);
			return gross;
		case SALARY:
			return Payroll.roundCent(_rate / WeeksPerYear);
		default:
			System.out.println("\n***ERROR***");
			System.out.println(
					"Gross calculation couldn't determine whether salary or hourly. Something went wrong, probably the programmer's fault. Show this to them");
			System.out.println("***ERROR***");
			return 0;
		}
	}
	public double calcPAYE() {
		double tax = 0;
		double income = calcGross() * WeeksPerYear;
		if (income > 70000) {
			tax += Payroll.roundCent((income - 70000) * 0.33);
			income = 70000;
		}
		if (income > 48000) {
			tax += Payroll.roundCent((income - 48000) * 0.3);
			income = 48000;
		}
		if (income > 14000) {
			tax += Payroll.roundCent((income - 14000) * 0.175);
			income = 14000;
		}
		tax += Payroll.roundCent(income * 0.105);
		tax /= WeeksPerYear;
		return Payroll.roundCent(tax);
	}
	public double calcNett() {
		double Nett = (calcGross() - calcPAYE() - _deductions);
		if (Nett < 0) {
			System.out.println("\n***ERROR***");
			System.out.println("Employee: " + firstLastName() + " with tax ID number: " + _tID
					+ " has a negative Nett, check the rate, deductions, hours and method to pay by is correct");
			System.out.println("***ERROR***");
		}
		return Nett;
	}
	public String paidBy() {
		if (_paidBy == payBy.HOURLY) {
			return "Hourly";
		} else {
			return "Salaried";
		}
	}
	public double calcYTD() {
		return (_yTD + calcGross());
	}
	public int taxID() {
		return _tID;
	}
	public String lastFirstName() {
		return _familyName + ", " + _givenName;
	}
	public String firstLastName() {
		return _givenName + " " + _familyName;
	}
	public String periodStart() {
		return _start;
	}
	public String periodEnd() {
		return _end;
	}
	public double deductions() {
		return _deductions;
	}
	public double rate() {
		return _rate;
	}
}