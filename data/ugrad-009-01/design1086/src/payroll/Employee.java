package payroll;
import java.util.Comparator;
public abstract class Employee implements Comparable<Employee> {
	private int _taxID;
	private String _name;
	private String _fistLastName;
	private String _employment;
	private double _rate;
	private double _YTD;
	private String _payPeriod;
	private double _hours;
	private double _deductions;
	public Employee (int taxID, String name, String employment, double rate, double YTD, String startDate,
			String endDate, double hours, double deductions){
		_taxID = taxID;
		_name = name;
		_employment = employment;
		_rate = rate;
		_YTD = YTD;
		_payPeriod = startDate + " to " + endDate;
		_hours = hours;
		_deductions = deductions;
		if ((startDate.charAt(5) != '-') || (startDate.charAt(8) != '-') ||(endDate.charAt(5) != '-') || (endDate.charAt(8) != '-')){
		}
		if (_rate < 0 || _YTD < 0 || _hours < 0 || _deductions < 0){
			ErrorReport.printNegativeNumError(_taxID);
			throw new RuntimeException();
		}
	}
	public int compareTo(Employee employee){
		if  (_taxID < employee._taxID){
			return -1;
		} else if (_taxID > employee._taxID){
			return 1;
		} else {
			return 0;
		}
	}
	public static class NameComparator implements Comparator<Employee> {
		public int compare(Employee one, Employee two){
			if (one._name.equals(two._name)){
				return new Integer(one._taxID).compareTo(new Integer(two._taxID));
			} else{
				return one._name.compareTo(two._name);
			}
		}
	}
	public abstract double calculateGross();
	public abstract double calculatePAYE();
	public double calculateNett(double gross, double paye){
		double nett = gross - paye - _deductions;
		return Math.round(nett*100d)/100d;
	}
	public double calculateNewYTD(double gross){
		double YTD = gross + _YTD;
		return Math.round(YTD*100d)/100d;
	}
	public String convertFirstLastName(){
		String[] splitName = _name.split(", ");
		String firstLastName = splitName[1] + " " + splitName[0];
		return firstLastName;
	}
	public void printpayPeriod(){
		System.out.println(_payPeriod);
	}
	public void aEmployeePayslip(){
		double gross = calculateGross();
		double PAYE = calculatePAYE();
		double nett = calculateNett(gross, PAYE);
		double newYTD = calculateNewYTD(gross);
		String name = convertFirstLastName();
		System.out.println(_taxID + ". " + name + ", Period: " + _payPeriod + ". Gross: $" + String.format("%.2f",gross) + ", PAYE: $" + String.format("%.2f",PAYE) +
				", Deductions: $" + String.format("%.2f", _deductions) + " Nett: $" + String.format("%.2f",nett) + " YTD: $" + String.format("%.2f",newYTD));
	}
	public void aEmployeeEmployeesReport(){
		double gross = calculateGross();
		double newYTD = calculateNewYTD(gross);
		System.out.println(_name + " (" +  _taxID + ") " + _employment + ", $" + String.format("%.2f",_rate) + " YTD:$" + String.format("%.2f",newYTD));
	}
	public double calculateBurden(double totalGross){
		double gross = calculateGross();
		totalGross = totalGross + gross;
		return totalGross;
	}
	public double calculatePAYE(double totalPAYE){
		double PAYE = calculatePAYE();
		totalPAYE = totalPAYE + PAYE;
		return totalPAYE;
	}
}
