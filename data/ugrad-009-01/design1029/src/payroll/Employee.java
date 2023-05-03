package payroll;
import java.text.DecimalFormat;
public class Employee {
	private int _taxID;
	private String _name;
	private String _type;
	private double _wage;
	private double _toDate;
	private double _hours;
	private double _deductables;
	private String _dateRange;
	public Employee(String[] data) {
		checkSize(data);
		_taxID = Integer.parseInt(data[0]);
		_name = data[1];
		_type = data[2];
		_wage = Double.parseDouble(data[3].substring(1));
		_toDate = Double.parseDouble(data[4].substring(1));
		_hours = Double.parseDouble(data[7]);
		_deductables = Double.parseDouble(data[8].substring(1));
		_dateRange = data[5] + " to " + data[6];
	}
	public Employee(){
		_taxID = 0;
		_name = null;
		_type = null;
		_wage = 0;
		_toDate = 0;
		_hours = 0;
		_deductables = 0;
	}
	public double makeMoney(String money){
		if(money.substring(0,0).equals("$")){
			double value = Double.parseDouble(money.substring(1));
			if(value < -1){
				throw new RuntimeException("Error in" + _taxID + "Money can't be neigative");
			}
			return value;
		} else {
			throw new RuntimeException("Error in "+ _taxID + "Money must begin with '$'");
		}
	}
	public double employeeBurden(){
		EmployeeBurden employeeBurden = new EmployeeBurden(_wage, _hours, _type);
		return employeeBurden.calcBurden();
	}
	public double employeeTax() {
		if(_type.equals("Salaried")){
			EmployeeTax employeeTax = new EmployeeTax(_wage);
			return  employeeTax.totalTax();
		} else {
			EmployeeTax employeeTax = new EmployeeTax((employeeBurden()*52));
			return employeeTax.totalTax();
		}
	}
	public void printEmployeePayslip(){
		DecimalFormat df = new DecimalFormat("####0.00");
		this.rearrangeName();
		double gross = employeeBurden();
		EmployeeTax tax = new EmployeeTax(gross * 52);
		double Paye = tax.totalTax();
		double nett = gross - Paye - _deductables;
		double YTD = gross + _toDate;
		System.out.print(_taxID+ ". " );
		System.out.print(rearrangeName() + ", Period: " );
		System.out.print(_dateRange);
		System.out.print(". Gross: $");
		System.out.print(df.format(gross)+", PAYE: $");
		System.out.print(df.format(Paye) + ", Deductions: $");
		System.out.print(df.format(_deductables) + " Nett: $");
		System.out.print(df.format(nett) + " YTD: $");
		System.out.println(df.format(YTD));
	}
	public void printEmployeeInfo() {
		double toDate = employeeBurden() + _toDate ;
		DecimalFormat df = new DecimalFormat("####0.00");
		System.out.print(_name+ " (" );
		System.out.print(_taxID+ ") " );
		System.out.print(_type + ", $");
		System.out.print(df.format(_wage) + " YTD:$");
		System.out.print(df.format(toDate) + "\n");
	}
	private void checkSize(String[] data) {
		if(data.length != 9){
			throw new RuntimeException("Employee data not complete - Error in input file.");
		}
	}
	public String rearrangeName(){
		String[] names = _name.split(", ");
		return (names[1] + " " + names[0]);
	}
	public int taxID() {
		return _taxID;
	}
	public String name() {
		return _name;
	}
	public String getTimeRange() {
		return _dateRange;
	}
}
