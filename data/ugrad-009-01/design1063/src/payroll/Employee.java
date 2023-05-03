package payroll;
import java.text.DecimalFormat;
import java.util.Comparator;
public class Employee {
	private int _TAXID;
	private String _firstname;
	private double _rate;
	private String _start;
	private String _end;
	private double _hours;
	private double _deductions;
	private String _lastname;
	private double _YTD;
	private String _employment;
	private double _gross;
	private double _PAYE;
	private double _Nett;
	public Employee(String[] employeeData) {
		_TAXID = Integer.parseInt(employeeData[0]);
		_firstname = employeeData[2];
		_lastname = employeeData[1];
		_employment = employeeData[3];
		String rates = employeeData[4].replace("$", "");
		double rates1 = Double.parseDouble(rates);
		_rate = rates1;
		_start = employeeData[6];
		_end = employeeData[7];
		_hours = Double.parseDouble(employeeData[8]);
		String deductions1 = employeeData[9].replace("$", "");
		double deductions2 = Double.parseDouble(deductions1);
		_deductions = deductions2;
		_gross = calculateGross(_rate, _hours, _employment);
		String YTD = employeeData[5].replace("$", "");
		double YTD1 = Double.parseDouble(YTD);
		_YTD = YTD1 + _gross;
		_PAYE = calculatePAYE(_gross);
		_Nett = _gross - _PAYE - _deductions;
	}
	private double calculateGross(double rate, double hours, String employment){
		if(employment.equals("Salaried")){
			double gross = round(rate/52);
			return gross;
		}
		else{
			double wageGross;
			double wageGross1;
			double wageGross2;
			double wageGross3;
			if(hours < 40){
				wageGross = round(rate * hours);
			}
			else if((hours > 40) &&(hours <= 43)){
				wageGross1 = round(40 * rate);
				wageGross2 = round((hours - 40) * 1.5 * rate);
				wageGross = round(wageGross1 + wageGross2);
			}
			else{
				wageGross1 = round(40 * rate);
				wageGross2 = round(3 * rate);
				wageGross3 = round((hours - 43) * 2 * rate);
				wageGross = round(wageGross1 + wageGross2 + wageGross3);
			}
			return wageGross;
		}
	}
	private double calculatePAYE(double input){
		double gross1;
		double gross2;
		double gross3;
		double gross4;
		input = input * 52;
		if(input > 0 && input <= 14000){
			return round((input * 0.105)/52);
		}
		else if(input <= 48000){
			gross1 = round(14000*0.105);
			gross2 = round((input - 14000) * 0.175);
			return round((gross1 + gross2)/52);
		}
		else if(input <= 70000){
			gross1 = round(14000 * 0.105);
			gross2 = round((48000 - 14000) * 0.175);
			gross3 = round((input - 48000) * 0.300);
			return round((gross1 + gross2 +gross3)/52);
		}
		else {
			gross1 = round(14000 * 0.105);
			gross2 = round((48000 - 14000) * 0.175);
			gross3 = round((70000 - 48000) * 0.300);
			gross4 = round((input - 70000) * 0.330);
			return round((gross1 + gross2 + gross3 + gross4)/52);
		}
	}
	public double round(double input){
		DecimalFormat tempInput = new DecimalFormat("#.00");
		input = Double.valueOf(tempInput.format(input));
		return input;
	}
	public void printPaySlips(){
		DecimalFormat formatter = new DecimalFormat("#0.00");
		System.out.println(_TAXID+ ". " +_firstname+ " " +_lastname+ ", Period: " +_start+ " to " +_end+ ". Gross: $" +formatter.format(_gross)+ ", PAYE: $" +formatter.format(_PAYE)+ ", Deductions: $"+formatter.format(_deductions)+ " Nett: $" +formatter.format(_Nett)+ " YTD: $"+formatter.format(_YTD));
	}
	public void printEmployees(){
		DecimalFormat formatter = new DecimalFormat("#0.00");
		System.out.println(_lastname+ ", " +_firstname+ " (" +_TAXID+ ") "+_employment+ ", $" +formatter.format(_rate)+ " YTD:$" +formatter.format(_YTD));
	}
	public void printStartToEndDate(){
		System.out.println(_start+ " to " +_end);
	}
	public double getDeductions(){
		return _deductions;
	}
	public double getYTD(){
		return _YTD;
	}
	public static class TAXIDComparator implements Comparator<Employee>{
		public int compare(Employee em1, Employee em2){
			if(em1._TAXID > em2._TAXID){
				return 1;
			}
			else{
				return -1;
			}
		}
	}
	public static class NameComparator implements Comparator<Employee>{
		public int compare(Employee em1, Employee em2){
			return (em1._lastname.compareTo(em2._lastname));
		}
	}
	public double getGross(){
		return _gross;
	}
	public double getPAYE(){
		return _PAYE;
	}
}

