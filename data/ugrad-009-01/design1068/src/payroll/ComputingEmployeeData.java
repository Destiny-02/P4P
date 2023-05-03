package payroll;
import java.text.DecimalFormat;
public class ComputingEmployeeData {
	public int changeToInt(String words){
		return Integer.parseInt(words);
	}
	public double changeToDouble(String words){
		return Double.parseDouble(words);
	}
	private int[] changeStringListToInt(String[] list){
		int[] intArray = new int[list.length];
		for(int i = 0; i < list.length; i++){
			intArray[i] = changeToInt(list[i]);
		}
		return intArray;
	}
	public String[] separateFullName(String name){
		String[] splitName = name.split(", ");
		return splitName;
	}
	public boolean isItHourly(String data){
		if(data.equals("Hourly")){
			return true;
		}
		return false;
	}
	public double goAwayDollarSign(String money){
		String moneyWithoutDollarSign = money.replace("$", "");
		return Double.parseDouble(moneyWithoutDollarSign);
	}
	public int[] separateDate(String date){
		String[] dateSeperatedString = date.split("-");
		int[] dateSeperatedInt = changeStringListToInt(dateSeperatedString);
		return dateSeperatedInt;
	}
	public double nearestQuarterHour(String hour){
		double dHour = changeToDouble(hour);
		int iHour = (int)dHour;
		double frac = (dHour-iHour)*100;
		double min = Math.round(frac/25)*25;
		double processedHour = iHour + min/100;
		return processedHour;
	}
	public double calculateGross(double hours, double rate, boolean isHourly){
		if(isHourly){
			double total = 0;
			if(hours > 43){
				total = (hours-43)*rate*2 + 3*rate*1.5 + 40*rate;
			} else if(hours > 40 ){
				total = (hours - 40)*rate*1.5 + 40*rate;
			} else {
				total = hours*rate;
			}
			return roundNearestCent(total*1.00);
		} else {
			return roundNearestCent(rate/52*1.00);
		}
	}
	public double calculatePaye(double gross){
		CalculateTax tax = new CalculateTax();
		return roundNearestCent(tax.doTax(gross)*1.00);
	}
	public double calculateNett(double gross, double paye, double deductions){
		return roundNearestCent(gross - paye - deductions*1.00);
	}
	public double calculateYtd(double originalYtd, double gross){
		return roundNearestCent(originalYtd + gross*1.00);
	}
	public double roundNearestCent(double money){
		double temp = Math.round(money*100);
		return temp/100;
	}
	public String keepTwoDecimal(double money){
		DecimalFormat df = new DecimalFormat("#.00");
		String moneyString = df.format(money);
		return moneyString;
	}
}
