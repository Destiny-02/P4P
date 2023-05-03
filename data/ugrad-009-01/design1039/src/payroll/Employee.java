package payroll;
import java.util.Scanner;
public class Employee extends UtilityAbstract{
	private int _TID;
	private String _name;
	private String _jobType;
	private double _rate;
	private double _YTD;
	private String _start;
	private String _end;
	private double _hours;
	private double _deduction;
	public double dollarStringToDouble(String dollarString) {
		double result = 0;
		dollarString = dollarString.replace("$", "");
		Scanner tempSc = new Scanner(dollarString);
		result = tempSc.nextDouble();
		tempSc.close();
		return result;
	}
	public void fillInfoFromScanner(Scanner scanner) {
		set_TID(scanner.nextInt());
		set_name(scanner.next() + " " + scanner.next());
		set_jobType(scanner.next());
		set_rate(dollarStringToDouble(scanner.next()));
		set_YTD(dollarStringToDouble(scanner.next()));
		set_start(scanner.next());
		set_end(scanner.next());
		set_hours(scanner.nextDouble());
		set_deduction(dollarStringToDouble(scanner.next()));
		scanner.close();
	}
	public double calculateGross() {
		double result = 0;
		if (_jobType.equals("Salaried")) {
			result = round(_rate/52);
		}
		else if (_jobType.equals("Hourly")) {
			if (_hours <= 40) {
				result = round(_rate*_hours);
			}
			else if ((_hours > 40) && (_hours <= 43)) {
				result = round(_rate*40 + (_hours-40)*(_rate*1.5));
			}
			else {
				result = round(_rate*40 + 3*(_rate*1.5) + (_hours-43)*(_rate*2));
			}
		}
		else {
			result = 0;
		}
		return result;
	}
	public double calculateTax() {
		double result = 0;
		double weeklyGross = calculateGross();
		if (weeklyGross <= (14000/52)) {
			result = round(weeklyGross*0.105);
		}
		else if ((weeklyGross > (14000/52)) && (weeklyGross <= (48000/52))) {
			result = 28.27 + round((weeklyGross - 269.23)*0.175);
		}
		else if ((weeklyGross > (48000/52)) && (weeklyGross <= (70000/52))) {
			result = 28.27 + 114.42 + round((weeklyGross - 923.08)*0.3);
		}
		else {
			result = 28.27 + 114.42 + 126.92 + round((weeklyGross - 1346.15)*0.33);
		}
		return result;
	}
	public double calculateNett() {
		double result = 0;
		double gross = calculateGross();
		double incomeTax = calculateTax();
		result = round(gross - incomeTax - _deduction);
		return result;
	}
	public String convertNamePayslip() {
		String result = get_name().replace(",", "");
		Scanner temp = new Scanner(result);
		String lastName = temp.next();
		String firstName = temp.next();
		result = firstName + " " + lastName;
		temp.close();
		return result;
	}
	public void checkNegative() throws IllegalStateException {
		if ((_TID < 0) || (_rate < 0) || (_YTD < 0) || (_hours < 0) || (_deduction < 0)) {
			throw new IllegalStateException();
		}
	}
	public int get_TID() {
		return _TID;
	}
	public void set_TID(int _TID) {
		this._TID = _TID;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String get_jobType() {
		return _jobType;
	}
	public void set_jobType(String _jobType) {
		this._jobType = _jobType;
	}
	public double get_rate() {
		return _rate;
	}
	public void set_rate(double _rate) {
		this._rate = _rate;
	}
	public double get_YTD() {
		return _YTD;
	}
	public void set_YTD(double _YTD) {
		this._YTD = _YTD;
	}
	public String get_start() {
		return _start;
	}
	public void set_start(String _start) {
		this._start = _start;
	}
	public String get_end() {
		return _end;
	}
	public void set_end(String _end) {
		this._end = _end;
	}
	public double get_hours() {
		return _hours;
	}
	public void set_hours(double _hours) {
		this._hours = _hours;
	}
	public double get_deduction() {
		return _deduction;
	}
	public void set_deduction(double _deduction) {
		this._deduction = _deduction;
	}
}
