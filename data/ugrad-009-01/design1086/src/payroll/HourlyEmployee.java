package payroll;
public class HourlyEmployee extends Employee{
	private double _rate;
	private double _hours;
	public HourlyEmployee(int taxID,String name, String employment, double rate, double YTD, String startDate,
			String endDate, double hours, double deductions){
		super (taxID, name, employment, rate, YTD, startDate, endDate, hours, deductions);
		_rate = rate;
		_hours = hours;
	}
	public double calculateGross(){
		double gross = 0;
		if (_hours >= 43){
			gross = (_hours - 43)*2*_rate + 3*1.5*_rate + 40*_rate;
			gross = _rate * _hours;
		} else if (_hours >= 40){
			gross = (_hours - 40)*1.5*_rate + 40*_rate;
		} else {
			gross = _rate * _hours;
		}
		return Math.round(gross*100d)/100d;
	}
	public double calculatePAYE(){
		double annualGross = calculateGross() * 52;
		double paye = 0;
		if (annualGross >= 70000){
			paye = 14000*0.105 + 34000*0.175 + 22000*0.3 + (annualGross-14000-34000-22000)*0.33;
		}else if (annualGross >= 48000){
			paye = 14000*0.105 + 34000*0.175 + (annualGross-14000-34000)*0.30;
		}else if (annualGross >= 14000){
			paye = 14000*0.105 + (annualGross - 14000)*0.175;
		} else if (annualGross >= 0){
			paye = annualGross*0.105;
		}
		return Math.round((paye/52)*100d)/100d;
	}
}
