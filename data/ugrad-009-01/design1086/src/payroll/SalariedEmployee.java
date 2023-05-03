package payroll;
public class SalariedEmployee extends Employee{
	private double _rate;
	private double _hours;
	public SalariedEmployee(int taxID,String name, String employment, double rate, double YTD, String startDate,
			String endDate, double hours, double deductions){
		super (taxID, name, employment, rate, YTD, startDate, endDate, hours, deductions);
		_rate = rate;
		_hours = hours;
	}
	public double calculateGross(){
		double gross = _rate/52;
		return Math.round(gross*100d)/100d;
	}
	public double calculatePAYE(){
		double paye = 0;
		if (_rate >= 70000){
			paye = 14000*0.105 + 34000*0.175 + 22000*0.3 + (_rate-14000-34000-22000)*0.33;
		}else if (_rate >= 48000){
			paye = 14000*0.105 + 34000*0.175 + (_rate-14000-34000)*0.30;
		}else if (_rate >= 14000){
			paye = 14000*0.105 + (_rate - 14000)*0.175;
		} else if (_rate >= 0){
			paye = _rate*0.105;
		}
		return Math.round((paye/52)*100d)/100d;
	}
}
