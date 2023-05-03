package payroll;
import java.text.DecimalFormat;
public class Employee {
	private int _TaxID;
	private String _LastName;
	private String _FirstName;
	private String _Type;
	private double _Rate;
	private double _YTD;
	private String _StartDate;
	private String _EndDate;
	private double _Hours;
	private double _Deductions;
	private Employee _next;
	public Employee(String[] info) {
		String[] name = info[1].split(", ");
		info[3] = info[3].replaceAll("[$]", "");
		info[4] = info[4].replaceAll("[$]", "");
		info[8] = info[8].replaceAll("[$]", "");
		this._TaxID = Integer.parseInt(info[0]);
		this._LastName = name[0];
		this._FirstName = name[1];
		this._Type = info[2];
		this._Rate = Round(Double.parseDouble(info[3]));
		this._StartDate = info[5];
		this._EndDate = info[6];
		this._Hours = Round(Double.parseDouble(info[7]));
		this._Deductions = Round(Double.parseDouble(info[8]));
		this._YTD = Round(Double.parseDouble(info[4]) + Gross());
	}
	public Employee GetNext() {
		return this._next;
	}
	public int GetTaxID() {
		return this._TaxID;
	}
	public String GetFirstName() {
		return this._FirstName;
	}
	public String GetLastName() {
		return this._LastName;
	}
	public void SetNext(Employee NextEmployee) {
		this._next = NextEmployee;
	}
	private double Round(double a) {
		DecimalFormat df = new DecimalFormat("#.00");
	    String numberFormated = df.format(a);
	    return Double.parseDouble(numberFormated);
	}
	public static String StringRound(double a) {
		if ((a < 1) && (a > -1)) {
			DecimalFormat df = new DecimalFormat("0.00");
			return df.format(a);
		} else {
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format(a);
		}
	}
	private double Wage() {
		if (this._Hours <= 40) {
			return Round(this._Hours * this._Rate);
		} else if (40 < this._Hours && this._Hours <= 43) {
			return (Round(40 * this._Rate) + Round((this._Hours - 40) * this._Rate * 1.5));
		} else {
			return (Round(40 * this._Rate) + Round(3 * this._Rate * 1.5) + Round((this._Hours - 40) * this._Rate * 2));
		}
	}
	public double PAYE() {
		if (this._Type.compareTo("Salaried") == 0) {
			double rate = this._Rate;
			if (rate <= 14000) {
				return Round((rate / 52) * 0.105);
			} else if ((rate <= 48000) && (rate > 14000)) {
				return Round((((rate - 14000) / 52) * 0.175) + 28.27);
			} else if ((rate <= 70000) && (rate > 48000)) {
				return Round((((rate - 48000) / 52) * 0.30) + 114.42 + 28.27);
			} else {
				return Round((((rate - 70000) / 52) * 0.33) + 126.92 + 114.42 + 28.27);
			}
		} else if (this._Type.compareTo("Hourly") == 0) {
			double weeksEarning = Wage();
			double yearlyEarning = weeksEarning * 52;
			if (yearlyEarning <= 14000) {
				return Round(weeksEarning * 0.105);
			} else if ((yearlyEarning <= 48000) && (yearlyEarning > 14000)) {
				return Round(((weeksEarning - 269.23) * 0.175) + 28.27);
			} else if ((yearlyEarning <= 70000) && (yearlyEarning > 48000)) {
				return Round(((weeksEarning - 923.08) * 0.30) + 114.42 + 28.27);
			} else {
				return Round(((weeksEarning - 1346.15) * 0.33) + 126.92 + 114.42 + 28.27);
			}
		}
		return 0;
	}
	public double Gross() {
		if (this._Type.compareTo("Salaried") == 0) {
			double _Rate = this._Rate / 52;
			return Round(_Rate);
		} else if (this._Type.compareTo("Hourly") == 0) {
			return Wage();
		}
		return 0;
	}
	public double Nett() {
		return Round(Gross() - PAYE() - this._Deductions);
	}
	public String PrintPeriod() {
		return (this._StartDate + " to " + this._EndDate);
	}
	public String PrintPayslip() {
		return (this._TaxID + ". " + this._FirstName + " " + this._LastName + ", Period: " + this._StartDate + " to " + this._EndDate + ". Gross: $" + StringRound(this.Gross()) + ", PAYE: $" + StringRound(this.PAYE()) + ", Deductions: $" + StringRound(this._Deductions) + " Nett: $" + StringRound(this.Nett()) + " YTD: $" + StringRound(this._YTD));
	}
	public String PrintEmployee() {
		return (this._LastName + ", " + this._FirstName + " (" + this._TaxID + ") " + this._Type + ", $" + StringRound(this._Rate) + " YTD:$" + StringRound(this._YTD));
	}
}
