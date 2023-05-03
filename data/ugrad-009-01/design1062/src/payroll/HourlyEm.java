package payroll;
public class HourlyEm{
	public double calculateHourGross(double rate, double hours){
		double totalGross;
		if(hours <= 40){
			totalGross = hours * rate;
		} else if(hours > 40 && hours <= 43){
			hours = hours - 40;
			totalGross = 40 * rate + hours * rate * 1.5;
		} else {
			hours = hours - 43;
			totalGross = 40 * rate + 4.5 * rate + hours * rate * 2;
		}
		totalGross = Math.round((totalGross) * 100);
		totalGross = totalGross/100;
		return totalGross;
	}
}
