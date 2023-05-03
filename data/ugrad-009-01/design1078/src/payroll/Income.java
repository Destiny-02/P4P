package payroll;
import payroll.Employee.EmployeeType;
public class Income {
	private double _gross;
	private double _paye;
	public Income(double rate, double hours , EmployeeType type) {
		_gross = calcGross(rate, hours, type);
		_paye = calcPaye(_gross);
	}
	public double getGross(){
		return _gross;
	}
	public double getPaye(){
		return _paye;
	}
	private double calcGross(double rate, double hours , EmployeeType type) {
		double gross = 0;
		switch (type){
		case SALARIED:
			gross = rate/52;
			break;
		case HOURLY:
			if (hours <= 40){
				gross = hours*rate;
			} else if (hours <= 43){
				gross = 40*rate + hours%40 *1.5 *rate;
			} else {
				gross = 40*rate + 4.5*rate + hours%43 *2 *rate;
			}
			break;
		}
		return gross;
	}
	private double calcPaye(double gross){
		double paye;
		double annualGross = gross*52;
		if (annualGross <= 14000){
			paye = annualGross*0.105;
		} else if (annualGross <= 48000){
			paye = 14000*0.105 + (annualGross-14000)*0.175;
		} else if (annualGross <= 70000){
			paye = 14000*0.105 + (48000-14000)*0.175 + (annualGross - 48000) *0.30;
		} else {
			paye = 14000*0.105 + (48000-14000)*0.175 + (70000-48000) * 0.30 + (annualGross - 70000) *0.33;
		}
		return paye/52;
	}
}
