package payroll;
import java.text.DecimalFormat;
public abstract class Employee implements EmployeeInterface {
	private String _taxID;
	private String _name;
	private String _employment;
	private double _rate;
	private double _ytd;
	private String _start;
	private String _end;
	private double _hours;
	private double _deductions;
	private double gross;
	private double PAYE;
	private double nett;
	private double newYTD;
	protected final double UPTO14K = 0.105;
	protected final double UPTO48K = 0.175;
	protected final double UPTO70K = 0.300;
	protected final double OVER70K = 0.330;
	public Employee(String[] employeedata) {
		_taxID = employeedata[0];
		_name = employeedata[1];
		_employment = employeedata[2];
		_rate = Double.parseDouble(employeedata[3].replace("$", ""));
		_ytd = Double.parseDouble(employeedata[4].replace("$", ""));
		_start = employeedata[5];
		_end = employeedata[6];
		_hours = Double.parseDouble(employeedata[7]);
		_deductions = Double.parseDouble(employeedata[8].replace("$", ""));
		gross = grossEarnings();
		PAYE = calcPAYE();
		nett = calcNett();
		newYTD = newYTD();
		errorCheck();
	}
	public double calcNett() {
		return gross - PAYE - _deductions;
	}
	public double newYTD() {
		return _ytd + gross;
	}
	public double calcPAYE() {
		double PAYE;
		double annualgross = getgross() * 52;
		if (annualgross <= 14000) {
			PAYE = annualgross * UPTO14K;
			return round(PAYE / 52);
		} else if (annualgross <= 48000) {
			PAYE = 14000 * UPTO14K;
			PAYE += (annualgross - 14000) * UPTO48K;
			return round(PAYE / 52);
		} else if (annualgross <= 70000) {
			PAYE = 14000 * UPTO14K;
			PAYE += 34000 * UPTO48K;
			PAYE += (annualgross % 48000) * UPTO70K;
			return round(PAYE / 52);
		} else {
			PAYE = 14000 * UPTO14K;
			PAYE += 34000 * UPTO48K;
			PAYE += 22000 * UPTO70K;
			PAYE += (annualgross % 70000) * OVER70K;
			return round(PAYE / 52);
		}
	}
	public double round(double input) {
		DecimalFormat tempInput = new DecimalFormat("#.##");
		input = Double.valueOf(tempInput.format(input));
		return input;
	}
	public void errorCheck(){
		if(nett < 0){
			System.out.println("ERROR: For Employee " + _name + " - Deductions greater than earnings");
		}
	}
	public String gettaxID() {
		return _taxID;
	}
	public String getname() {
		return _name;
	}
	public String getemployment() {
		return _employment;
	}
	public double getrate() {
		return _rate;
	}
	public double getytd() {
		return _ytd;
	}
	public String getstart() {
		return _start;
	}
	public String getend() {
		return _end;
	}
	public double gethours() {
		return _hours;
	}
	public double getdeductions() {
		return _deductions;
	}
	public double getgross() {
		return gross;
	}
	public double getnewytd() {
		return newYTD;
	}
	public double getPAYE() {
		return PAYE;
	}
	public double getNett() {
		return nett;
	}
}