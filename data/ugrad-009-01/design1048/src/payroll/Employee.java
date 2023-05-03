package payroll;
import java.util.Comparator;
public class Employee {
	private int _taxID;
	private String _firstName;
	private String _lastName;
	private String _employment;
	private double _rate;
	private double _ytd;
	private String _start;
	private String _end;
	private double _hours;
	private double _deductions;
	private double _gross;
	private double _incomeTax;
	private double _nett;
	public Employee(double nett, double incomeTax, double gross, int taxID, String firstName, String lastName, String employment, double rate, double ytd, String start, String end, double hours, double deductions) {
		_taxID = taxID;
		_firstName = firstName;
		_lastName = lastName;
		_employment = employment;
		_rate = rate;
		_ytd = ytd;
		_start = start;
		_end = end;
		_hours = hours;
		_deductions = deductions;
		_gross = gross;
		_incomeTax = incomeTax;
		_nett = nett;
	}
	public void employeesProcessing() {
		System.out.println(_lastName + ", " + _firstName + " (" + _taxID + ") " + _employment+ ", $" + String.format("%.2f", _rate)+ " YTD:$" + String.format("%.2f", _ytd));
	}
	public void payslipsProcessing(){
		System.out.println(_taxID +". " + _firstName + " " + _lastName + ", Period: " + _start + " to " + _end + ". Gross: $" + String.format("%.2f", _gross) + ", PAYE: $" + String.format("%.2f", _incomeTax) + ", Deductions: $" + String.format("%.2f", _deductions) + " Nett: $" + String.format("%.2f", _nett) + " YTD: $" + String.format("%.2f", _ytd));
	}
	public double burdenProcessing(double totalBurden) {
		totalBurden = totalBurden + _gross;
		return totalBurden;
	}
	public double pAYEProcessing(double totalPAYE) {
		totalPAYE = totalPAYE + _incomeTax;
		return totalPAYE;
	}
	public void printDate() {
		System.out.println(_start + " to " + _end);
	}
	public static class EmployeeSortName implements Comparator<Employee>{
		public int compare(Employee a, Employee b){
			return a._lastName.compareTo(b._lastName);
		}
	}
	public static class EmployeeSorttaxID implements Comparator<Employee>{
		public int compare(Employee a, Employee b){
			if (a._taxID < b._taxID) {
				return -1;
			} else if (a._taxID > b._taxID) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
