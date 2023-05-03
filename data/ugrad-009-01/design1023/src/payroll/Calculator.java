package payroll;
public class Calculator {
	private final String SALARIED = "Salaried";
	private final String HOURLY = "Hourly";
	private final int BRACKET1 = 14000;
	private final int BRACKET2 = 48000;
	private final int BRACKET3 = 70000;
	private final double BAND1 = 0.105;
	private final double BAND2 = 0.175;
	private final double BAND3 = 0.3;
	private final double BAND4 = 0.33;
	private Employee employee= null;
	private double grossIncome = 0;
	private double netIncome = 0;
	private double tax = 0;
	private double paye = 0;
	private double ytd = 0;
	private int payPeriod = 1;
	public Calculator(Employee employee){
		this.employee = employee;
	}
	public double getGrossIncome(){
		double rate = employee.getRate();
		double hours = employee.getHours();
		if(employee.getPayType().equals(SALARIED)){
			grossIncome = payPeriod/52.0 * rate;
			getTax();
			return Math.round(grossIncome*100.0)/100.0;
		}
		else if(employee.getPayType().equals(HOURLY)){
			if(hours<=40){
				grossIncome = rate*hours;
				getTax();
				return Math.round(grossIncome*100.0)/100.0;
			}
			else if(hours>40 && hours<=43){
				grossIncome = rate*40 + (hours-40)*rate*1.5;
				getTax();
				return Math.round(grossIncome*100.0)/100.0;
			}
			else if(hours>43){
				grossIncome = rate*40 + 3*rate*1.5 + (hours-43)*2.0;
				getTax();
				return Math.round(grossIncome*100.0)/100.0;
			}
			else{
				return 0.0;
			}
		}
		else{
			getTax();
			return 0.0;
		}
	}
	public double getNetIncome(){
		netIncome = grossIncome - paye - employee.getDeductions();
		return Math.round(netIncome*100.0)/100.0;
	}
	public double getYtd(){
		ytd = employee.getYtd() + grossIncome;
		return Math.round(ytd*100.0)/100.0;
	}
	public double getPaye(){
		paye = tax/52.0;
		return Math.round(paye*100.0)/100.0;
	}
	private void getTax(){
		double annualGross = grossIncome * 52.0;
		if(annualGross>0 && annualGross<=BRACKET1){
			tax = annualGross * BAND1;
		}
		else if(annualGross>BRACKET1 && annualGross<=BRACKET2){
			tax = (annualGross-BRACKET1)*BAND2 + BAND1*BRACKET1;
		}
		else if(annualGross>BRACKET2 && annualGross<=BRACKET3){
			tax = (annualGross-BRACKET2)*BAND3 + BAND1*BRACKET1 + BAND2*(BRACKET2-BRACKET1);
		}
		else if(annualGross>BRACKET3){
			tax = (annualGross-BRACKET3)*BAND4 + BAND1*BRACKET1 + BAND2*(BRACKET2-BRACKET1) + BAND3*(BRACKET3-BRACKET2);
		}
		else{
			tax = 0;
		}
	}
}
