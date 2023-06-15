package payroll.file;
import payroll.exceptions.PayrollException;
public class PayCalculator {
	public double calcGross(double hours, double rate, boolean salaried){
		if(salaried){
			return round(rate/52);
		}else{
			if(hours <= 40){
				return round(hours * rate);
			}else if(hours < 43){
				double time = round(40 * rate);
				double timeAndAHalf = round((hours - 40) * rate * 1.5);
				return round((time + timeAndAHalf)*100)/100;
			}else{
				double time = round(40 * rate);
				double timeAndAHalf = round(3 * rate * 1.5);
				double doubleTime = round((hours - 43) * rate * 2);
				return round((time + timeAndAHalf + doubleTime)*100)/100;
			}
		}
	}
	public double calcPAYE(double gross, double rate, boolean salaried){
		double income;
		if(salaried){
			income = rate;
		}else{
			income = round(gross * 52);
		}
		if(income <= 14000){
			return round(round((income * 0.105))/52);
		}else if(income <= 48000){
			return (round(round((14000 * 0.105)) +
					(round((income - 14000) * 0.175)))/52);
		}else if(income <= 70000){
			return (round(round((14000 * 0.105)) +
					(round((48000 - 14000) * 0.175)) +
					(round((income - 48000) * 0.3)))/52);
		}else{
			return (round(14000 * 0.105) +
					round((48000 - 14000) * 0.175) +
					round((70000 - 48000) * 0.3) +
					round((income - 70000) * 0.33))/52;
		}
	}
	public double calcNett(String name, double gross, double PAYE, double deductions) throws PayrollException{
		if(deductions > gross){
			throw new PayrollException("Error: deductions greater than gross for:  " + name);
		}
		return gross - PAYE - deductions;
	}
	public double updateYTD(double YTD, double gross){
		return YTD + gross;
	}
	private double round(double d){
		return (double)(Math.round(d *100)/100f);
	}
}