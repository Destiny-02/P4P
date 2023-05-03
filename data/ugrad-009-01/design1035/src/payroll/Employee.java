package payroll;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Employee {
	private int _TID;
	private String _Name;
	private String _Employment;
	private double _rate;
	private double _YTD;
	private String _start;
	private String _end;
	private double _hours;
	private double _deduction;
	DecimalFormat formator = new DecimalFormat("#.00");
	public Employee(int TID, String Name, String Employment, double rate, double YTD, String start, String end, double hours, double deduction) {
		this._TID = TID;
		this._Name = Name;
		this._Employment = Employment;
		this._rate = rate;
		this._YTD = YTD;
		this._start = start;
		this._end = end;
		this._hours = hours;
		this._deduction = deduction;
	}
	public String getTID() {
		return String.valueOf(_TID);
	}
	public String getEmployment() {
		return this._Employment;
	}
	public String getRate() {
		return String.valueOf(formator.format(this._rate));
	}
	public String getDeduction() {
		return String.valueOf(formator.format(Math.round(this._deduction *100.0)/100.0));
	}
	public void printEmployees() {
		System.out.println("TID: " + _TID);
		System.out.println("Name: " + _Name);
		System.out.println("Employment: " + _Employment);
		System.out.println("rate: " + _rate);
		System.out.println("YTD: " + _YTD);
		System.out.println("start: " + _start);
		System.out.println("end: " + _end);
		System.out.println("hours " + _hours);
		System.out.println("deduction: " + _deduction);
	}
	public String getPeriod() {
		return _start + " to " + _end;
	}
	public double getOvertimeHours(){
		double time = _hours;
		if (time > 40 || time < 43) {
			return ((time - (double)40) * (double)1.5) + 40;
		} else if (time <= 40 ) {
			return time;
		} else {
			return ((time - (double)40) * (double)2) + 40;
		}
	}
	public String getGross(){
		if (_Employment.equalsIgnoreCase("salaried")) {
		return formator.format(Math.round(((double)40/2080) * _rate * 100.0)/100.0);
		}
		if (_Employment.equalsIgnoreCase("hourly")) {
			if (_hours <= 40) {
			return formator.format(Math.round(_hours * _rate* 100.0)/100.0);
			} else
				return formator.format(Math.round(((getOvertimeHours()*_rate* 100.0)/100.0)));
		}
		return "No pay";
	}
	public String getPAYE() {
		double PAYE = 0;
		if (_Employment.equalsIgnoreCase("salaried")) {
			double income = _rate;
			if(income > 70000) {
				PAYE += (income - (double)70000)*(double)0.33;
				income = (double)70000;
			}
			if(income> 48000) {
				PAYE += (income - (double)48000)*(double)0.30;
				income = (double)48000;
			}
			if (income > 14000) {
				PAYE += (income - (double)14000)*(double)0.175;
				income = (double)14000;
			}
			PAYE += income*(double)0.105;
			PAYE = PAYE/(double)52;
			return formator.format(Math.round(PAYE *100.0)/100.0);
			}
		if (_Employment.equalsIgnoreCase("hourly")) {
		double income = (getOvertimeHours() * 52) * Math.round(_rate);
		if(income > 70000) {
			PAYE += (income - (double)70000)*(double)0.33;
			income = (double)70000;
		}
		if(income> 48000) {
			PAYE += (income - (double)48000)*(double)0.30;
			income = (double)48000;
		}
		if (income > 14000) {
			PAYE += (income - (double)14000)*(double)0.175;
			income = (double)14000;
		}
		PAYE += income*(double)0.105;
		PAYE = PAYE/(double)52;
		return String.valueOf(formator.format(Math.round(PAYE * 100.0)/100.0));
		}
		return "No Pay";
	}
	public String getNett(){
		return formator.format(Math.round(((Double.parseDouble(getGross()) - Double.parseDouble(getPAYE()) - _deduction)*100.0))/100.0);
	}
	public String getNameFirstLast() {
		String[] name = _Name.split(",");
		return name[1] + " " + name[0];
	}
	public String getNameLastFirst() {
		return this._Name;
	}
	public String getNewYTD(){
		return formator.format(Math.round((Double.parseDouble(getGross()) + _YTD) * 100.00)/100.00);
	}
	public void getDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   Date date = new Date();
		   System.out.println(dateFormat.format(date));
	}
}

