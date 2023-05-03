package payroll;
import java.text.DecimalFormat;
import java.util.Comparator;
public abstract class Employee implements Comparable<Employee>{
	private final int _TID;
	private final String _lastName;
	private final String _firstName;
	private String _employment;
	private double _rate;
	private double _YTD;
	private String _periodStart;
	private String _periodEnd;
	private double _hours;
	private double _deduction;
	public Employee(int TID, String lastName, String firstName, String employment, double rate, double YTD, String periodStart, String periodEnd, double hours, double deduction) {
		_TID = TID;
		_lastName = lastName;
		_firstName = firstName;
		_employment = employment;
		_rate = rate;
		_YTD = YTD;
		_periodStart = periodStart;
		_periodEnd = periodEnd;
		_hours = hours;
		_deduction = deduction;
	}
	public abstract double Gross() throws EmployeeException;
	public double PAYE () throws EmployeeException{
		double gross = this.Gross();
		DecimalFormat df = new DecimalFormat("###.##");
		double annualGross = Double.parseDouble((df.format(gross * 52)));
		double annualTax = 0;
		if (gross == 0) {
			return 0;
		}
		else if (annualGross <= 14000) {
			annualTax = Double.parseDouble((df.format(annualGross * 0.105)));
			return Double.parseDouble((df.format(annualTax / 52)));
		}
		else if (annualGross <= 48000) {
			annualTax = Double.parseDouble((df.format(14000 * 0.105 + (annualGross - 14000) * 0.175)));
			return Double.parseDouble((df.format(annualTax / 52)));
		}
		else if (annualGross <= 70000) {
			annualTax = Double.parseDouble((df.format(14000 * 0.105 + 34000 * 0.175 + (annualGross - 48000) * 0.30)));
			return Double.parseDouble((df.format(annualTax / 52)));
		}
		else {
			annualTax = Double.parseDouble((df.format(14000 * 0.105 + 34000 * 0.175 + 22000 * 0.30 + (annualGross - 70000) * 0.33)));
			return Double.parseDouble((df.format(annualTax / 52)));
		}
	}
	public double Nett() throws EmployeeException{
		DecimalFormat df = new DecimalFormat("###.##");
		double nett = 0;
		nett = Double.parseDouble((df.format(this.Gross() - this.PAYE() - _deduction)));
		if (nett < 0) {
			throw new EmployeeException("Negative nett, deduction larger than employee earnings");
		}
		else {
			return nett;
		}
	}
	public int compareTo(Employee otherEmp) {
		if (_TID < otherEmp._TID) {
			return -1;
		}
		else if (_TID > otherEmp._TID) {
			return 1;
		}
		else {
			return 0;
		}
	}
	public String givePayPeriod () {
		return _periodStart + " to " + _periodEnd;
	}
	public String giveTID () {
		return Integer.toString(_TID);
	}
	public String giveRateStr () {
		return Double.toString(_rate);
	}
	public double giveRate () {
		return _rate;
	}
	public double giveHours () {
		return _hours;
	}
	public static class FamilyNameComparator implements Comparator<Employee>{
		public int compare(Employee emp1, Employee emp2) {
			if (emp1._lastName != emp2._lastName) {
				return (emp1._lastName).compareTo(emp2._lastName);
			}
			else {
				if (emp1._firstName != emp2._firstName) {
					return (emp1._firstName).compareTo(emp2._firstName);
				}
				else {
					return emp1.compareTo(emp2);
				}
			}
		}
	}
	public String Payslip() throws EmployeeException{
		double gross = this.Gross();
		double PAYE = this.PAYE();
		double Nett = this.Nett();
		return (_TID + ". " + _firstName + " " + _lastName + ", Period: "
		+ this.givePayPeriod() + ". Gross: " + new Money(gross).moneyFormat2dp() + ", ") +
		"PAYE: " + new Money(PAYE).moneyFormat2dp() + ", Deductions: " + new Money(_deduction).moneyFormat2dp() +
		 " Nett: " + new Money(Nett).moneyFormat2dp() + " YTD: " + new Money(_YTD + gross).moneyFormat2dp();
	}
	public String EmployeeInfo() throws EmployeeException{
		return (_lastName + ", " + _firstName +
		" ("+ _TID + ") " + _employment + ", " +
		new Money(_rate).moneyFormat2dp() + " YTD:" + new Money(_YTD + this.Gross()).moneyFormat2dp());
	}
}
