package payroll;
import java.util.Comparator;
public abstract class Employee implements Comparable<Employee>{
	protected int _taxID;
	protected String _firstName;
	protected String _lastName;
	protected double _rate;
	protected double _ytd;
	protected String _startDate;
	protected String _endDate;
	protected double _hoursWorked;
	protected double _deduction;
	protected double _pay;
	protected double _tax;
	protected double _nettPay;
	public Employee(int tid, String firstName, String lastName,
			double rate, double ytd, String start, String end,
			double hours, double deduction){
		_taxID = tid;
		_firstName = firstName;
		_lastName = lastName;
		_rate = rate;
		_ytd = ytd;
		_startDate = start;
		_endDate = end;
		_hoursWorked = hours;
		_deduction = deduction;
		this.calculatePay();
		this.calculateTax();
		this.calculateYTD();
		this.calculateNettPay();
	}
	public void calculatePay(){
		double pay;
		pay = _rate / 52;
		_pay = pay;
	}
	public void calculateTax(){
		double tax = 0;
		double pay = 52 * _pay;
		final int[] taxBrackets = {14000 - 0, 48000 - 14000, 70000 - 48000};
		final double[] taxPercent = {0.105, 0.175, 0.3, 0.33};
		for (int i = 0; i < taxBrackets.length; i++){
			if (pay > taxBrackets[i]){
				tax += (taxBrackets[i] * taxPercent[i]);
				pay -= taxBrackets[i];
			} else {
				tax += (pay * taxPercent[i]);
				pay = 0;
				break;
			}
		}
		if (pay > 0){
			tax += pay * taxPercent[taxPercent.length - 1];
		}
		_tax = Math.round(tax / 52 * 100d) / 100d;
	}
	public void calculateYTD(){
		_ytd += _pay;
	}
	public void calculateNettPay(){
		_nettPay = _pay - _deduction - _tax;
		if (_nettPay < 0){
			throw new RuntimeException("Invalid nett pay for employee " + this.toStringPayslip());
		}
	}
	public double getTax(){
		return _tax;
	}
	public String toStringEmployees(){
		String employee = "";
		employee += String.format("%s, %s", _lastName, _firstName);
		employee += String.format(" (" + _taxID + ")");
		employee += String.format(", $%.2f", _rate);
		employee += String.format(" YTD:$%.2f", _ytd);
		return employee;
	}
	public String toStringPayslip(){
		String employee = "";
		employee += String.format("%d. ", _taxID);
		employee += String.format("%s %s, ", _firstName, _lastName);
		employee += String.format("Period: %s to %s. ", _startDate, _endDate);
		employee += String.format("Gross: $%.2f, ", _pay);
		employee += String.format("PAYE: $%.2f, ", _tax);
		employee += String.format("Deductions: $%.2f ", _deduction);
		employee += String.format("Nett: $%.2f ", _nettPay);
		employee += String.format("YTD: $%.2f", _ytd);
		return employee;
	}
	public String getPeriod(){
		return this._startDate + " to " + this._endDate;
	}
	public int compareTo(Employee a){
		return new ByTaxIDComparetor().compare(this, a);
	}
	public int getTaxID(){
		return this._taxID;
	}
	public static class ByLastNameComparetor implements Comparator<Employee>{
		public int compare(Employee employee1, Employee employee2){
			if (employee1._lastName.compareToIgnoreCase(employee2._lastName) > 0){
				return 1;
			} else if (employee1._lastName.equals(employee2._lastName)){
				return 0;
			}
			return -1;
		}
	}
	public static class ByFirstNameComparetor implements Comparator<Employee>{
		public int compare(Employee employee1, Employee employee2){
			if (employee1._firstName.compareToIgnoreCase(employee2._firstName) > 0){
				return 1;
			} else if (employee1._firstName.equals(employee2._firstName)){
				return 0;
			}
			return -1;
		}
	}
	public static class ByTaxIDComparetor implements Comparator<Employee>{
		public int compare(Employee employee1, Employee employee2){
			if (employee1._taxID > employee2._taxID){
				return 1;
			} else if (employee1._taxID < employee2._taxID){
				return -1;
			}
			return 0;
		}
	}
}
