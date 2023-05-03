package payroll;
import java.text.SimpleDateFormat;
import java.util.Date;
public abstract class PayrollOutput {
	abstract protected void process() throws PayrollUserException;
	protected void printToday(){
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String today = dateFormatter.format(date);
		System.out.println(today);
	}
	protected double PAYECalculator(Employee employee) throws PayrollUserException{
		if(employee.employment.equals("Salaried")){
			return taxCalculator(employee.rate);
		}else if(employee.employment.equals("Hourly")){
			double weeklyGross = hourlyGross(employee.hours, employee.rate);
			return taxCalculator(weeklyGross*52);
		}else{
			throw new PayrollUserException(employee.employment +" is an invalid  employment type");
		}
	}
	protected double grossCalculator(Employee employee) throws PayrollUserException{
		if(employee.employment.equals("Salaried")){
			double gross = employee.rate/52;
			gross = Math.round(gross*100.0)/100.0;
			return gross;
		}else if(employee.employment.equals("Hourly")){
			return hourlyGross(employee.hours, employee.rate);
		}else{
			throw new PayrollUserException(employee.employment +" is an invalid  employment type");
		}
	}
	private double hourlyGross(double hours, double rate){
		double annualEarnings = 0.0;
		if(hours>43){
			annualEarnings = (hours-43)*rate*2;
			annualEarnings = Math.round(annualEarnings*100.0)/100.0;
			hours=43.0;
		}
		if(hours>40){
			annualEarnings += (hours-40)*rate*1.5;
			annualEarnings = Math.round(annualEarnings*100.0)/100.0;
			hours=40.0;
		}
		annualEarnings += hours*rate;
		annualEarnings = Math.round(annualEarnings*100.0)/100.0;
		return annualEarnings;
	}
	private double taxCalculator(double annualEarnings){
		double weeklyTax = 0.0;
		if(annualEarnings>70000){
			weeklyTax = (annualEarnings-70000)*.33;
			weeklyTax = Math.round(weeklyTax*100.0) /100.0;
			annualEarnings = 70000.0;
		}
		if(annualEarnings>48000){
			weeklyTax += (annualEarnings-48000)*.3;
			weeklyTax = Math.round(weeklyTax*100.0) /100.0;
			annualEarnings = 48000.0;
		}
		if(annualEarnings>14000){
			weeklyTax += (annualEarnings-14000)*.175;
			weeklyTax = Math.round(weeklyTax*100.0) /100.0;
			annualEarnings = 14000.0;
		}
		weeklyTax += annualEarnings*.105;
		weeklyTax = Math.round(weeklyTax*100.0) /100.0;
		weeklyTax = weeklyTax/52;
		weeklyTax = Math.round(weeklyTax*100.0) /100.0;
		return weeklyTax;
	}
}
