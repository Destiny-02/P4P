package payroll.employeeInfo;
import java.util.Comparator;
public class EmployeeRecord {
	private final String _givenName, _familyName;
	private final int _taxId;
	private final float _rate;
	private final float _ytd;
	private final Date _periodStart;
	private final Date _periodEnd;
	private final float _hoursWorked;
	private final float _deductions;
	private final EmploymentType _employeeType;
	protected static Comparator<EmployeeRecord> SORT_TAXID = new Comparator<EmployeeRecord>() {
		public int compare(EmployeeRecord o1, EmployeeRecord o2) {
			return ((Integer)o1._taxId).compareTo(o2._taxId);
		}
	};
	protected static Comparator<EmployeeRecord> SORT_NAME = new Comparator<EmployeeRecord>() {
		public int compare(EmployeeRecord o1, EmployeeRecord o2) {
			return o1._familyName.compareTo(o2._familyName) != 0 ?
					o1._familyName.compareTo(o2._familyName) :
					o1._givenName.compareTo(o2._givenName);
		}
	};
	public EmployeeRecord(
			String givenName,
			String familyName,
			int taxId,
			float rate,
			float ytd,
			EmploymentType employeeType,
			Date periodStart,
			Date periodEnd,
			float hoursWorked,
			float deductions) {
		this._givenName = givenName;
		this._familyName = familyName;
		this._taxId = taxId;
		this._rate = rate;
		this._ytd = ytd;
		this._employeeType = employeeType;
		this._periodStart = periodStart;
		this._periodEnd = periodEnd;
		this._hoursWorked = hoursWorked;
		this._deductions = deductions;
	}
	public float GetGrossIncomeOverPeriod() {
		float income = 0;
		float hoursWorkedInWeek = _hoursWorked;
		switch (_employeeType) {
		case Hourly:
			if (hoursWorkedInWeek >= 0) {
				income += Math.min(hoursWorkedInWeek, 40) * _rate;
				hoursWorkedInWeek -= 40;
			}
			if (hoursWorkedInWeek >= 0) {
				income += Math.min(hoursWorkedInWeek, 3) * _rate * 1.5f;
				hoursWorkedInWeek -= 3;
			}
			income += Math.max(hoursWorkedInWeek, 0) * _rate * 2.0f;
			break;
		case Salaried:
			income = _rate / 52.0f;
			break;
		default:
			break;
		}
		return income;
	}
	public float GetApproxAnnualGrossIncome() {
		float income = 0;
		switch (_employeeType) {
		case Hourly:
			income = this.GetGrossIncomeOverPeriod() * 52;
			break;
		case Salaried:
			income = _rate;
			break;
		default:
			break;
		}
		return income;
	}
	public String PayslipFormat() {
		float periodGross = this.GetGrossIncomeOverPeriod();
		float periodPAYE = roundToNearestCent(this.GetAnnualPAYETax() / 52.0f);
		float nett = roundToNearestCent(periodGross - periodPAYE - _deductions);
		float newYTD = _ytd + periodGross;
		return String.format("%d. %s %s, Period: %s to %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f\n",
				_taxId,
				_givenName,
				_familyName,
				_periodStart.toString(),
				_periodEnd.toString(),
				periodGross,
				periodPAYE,
				_deductions,
				nett,
				newYTD);
	}
	public String EmployeeFormat() {
		float rate = _rate;
		float periodGross = this.GetGrossIncomeOverPeriod();
		float newYTD = _ytd + periodGross;
		return String.format("%s, %s (%d) %s, $%.2f YTD:$%.2f\n",
				_familyName,
				_givenName,
				_taxId,
				_employeeType.toString(),
				rate,
				newYTD);
	}
	public float GetAnnualPAYETax() {
		float tax = 0;
		float grossIncome = GetApproxAnnualGrossIncome();
		if (grossIncome >= 0) {
			tax += roundToNearestCent(Math.min(14000, grossIncome) * 0.105f);
			grossIncome -= 14000;
		}
		if (grossIncome >= 0) {
			tax += roundToNearestCent(Math.min(34000, grossIncome) * 0.175f);
			grossIncome -= 34000;
		}
		if (grossIncome >= 0) {
			tax += roundToNearestCent(Math.min(22000, grossIncome) * 0.300f);
			grossIncome -= 22000;
		}
		tax += roundToNearestCent(Math.max(0, grossIncome) * 0.330f);
		return roundToNearestCent(tax);
	}
	private float roundToNearestCent(float value) {
		return Math.round((value * 100.0f)) / 100.0f;
	}
	public Date[] GetPeriodStartAndEnd() {
		return new Date[] { _periodStart, _periodEnd };
	}
}
