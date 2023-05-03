package payroll;
import java.util.Scanner;
public class Employee {
	private int _TID;
	private String _name;
	private String _employment;
	private double _rate;
	private double _YTD;
	private String _start;
	private String _end;
	private double _hours;
	private double _deduct;
	private double _gross;
	private double _paye;
	private double _nett;
	public Employee(String line) {
		Scanner s = new Scanner(line).useDelimiter("\t");
		String tempRate = "";
		String tempYTD = "";
		String tempDeduct = "";
		while (s.hasNext()) {
			_TID = s.nextInt();
			_name = s.next();
			_employment = s.next();
			tempRate += s.next();
			tempYTD += s.next();
			_start = s.next();
			_end = s.next();
			_hours = s.nextDouble();
			tempDeduct += s.next();
		}
		_YTD = Double.parseDouble(tempYTD.replace("$", ""));
		_rate = Double.parseDouble(tempRate.replace("$", ""));
		_deduct = Double.parseDouble(tempDeduct.replace("$", ""));
		if (_rate < 0 || _YTD < 0 || _hours < 0 || _deduct < 0 || _TID < 1) {
			throw new ProcessingException("Invalid input. There are negative value(s) as entry for employee with TID number: " + _TID);
		}
	}
	public int getTID() {
		return _TID;
	}
	public String getName() {
		return _name;
	}
	EmployeeProcessing eProcess = new EmployeeProcessing();
	public double getGross() {
		_gross = eProcess.calcGross(_employment, _rate, _hours);
		_YTD += _gross;
		return _gross;
	}
	public double getPaye() {
		_paye = eProcess.calcPaye(_gross);
		return _paye;
	}
	public double getNett() {
		_nett = eProcess.calcNett(_gross, _paye, _deduct);
		return _nett;
	}
	public void printPayslipLine(){
		System.out.print(_TID + ". ");
		System.out.print(splitName() + ", Period: ");
		System.out.print(_start + " to " + _end);
		System.out.printf(". Gross: $%.2f", getGross());
		System.out.printf(", PAYE: $%.2f" ,getPaye());
		System.out.printf(", Deductions: $%.2f" ,_deduct);
		System.out.printf(" Nett: $%.2f",getNett());
		System.out.printf(" YTD: $%.2f\n",_YTD);
	}
	public void printEmployeeLine() {
		System.out.print(_name);
		System.out.print(" (" + _TID + ") ");
		System.out.print(_employment);
		System.out.printf(", $%.2f", _rate);
		getGross();
		System.out.printf(" YTD:$%.2f\n", _YTD);
	}
	public void printDate() {
		System.out.println(_start + " to " + _end);
	}
	public String splitName() {
		String[] splitted = _name.split(", ");
		String output = "";
		return output + splitted[1] + " " + splitted[0];
	}
}
