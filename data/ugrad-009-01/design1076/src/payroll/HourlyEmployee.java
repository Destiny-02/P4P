package payroll;
public class HourlyEmployee extends Employee{
	public HourlyEmployee(String[] processedInput) {
		super(processedInput);
	}
	@Override
	public double calcGross() {
		double hours = getHours();
		double rate = getRate();
		if(hours<0 || rate<0){
			return 0;
		}
		else if(hours<=40){
			return round(rate*hours);
		}
		else if (hours<=43){
			return round(rate*40+rate*(hours-40)*1.5);
		}
		else {
		return round(rate*40+rate*(hours-40)*1.5+rate*(hours-43)*2.0);
		}
	}
	@Override
	public double calcYearGross(){
		double gross = calcGross();
		return gross * 52;
	}
}
