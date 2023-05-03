package payroll;
public class CalculateTax {
	private final double UPTO14000 = 0.105;
	private final double TO48000 = 0.175;
	private final double TO70000 = 0.3;
	private final double OVER70000 = 0.33;
	public double doTax(double gross){
		double annualGross = gross*52;
		double tax;
		if(annualGross <= 14000){
			tax = annualGross*UPTO14000;
		} else if(annualGross <= 48000){
			tax = 14000*UPTO14000+(annualGross-14000)*TO48000;
		} else if(annualGross <= 70000){
			tax = 14000*UPTO14000+(48000-14000)*TO48000+(annualGross-48000)*TO70000;
		} else {
			tax = 14000*UPTO14000+(48000-14000)*TO48000+(70000-48000)*TO70000+(annualGross-70000)*OVER70000;
		}
		return tax/52;
	}
}
