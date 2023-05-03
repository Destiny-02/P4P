package payroll;
public class SalariedEmployee extends Employee{
	public SalariedEmployee(String[] processedInput) {
		super(processedInput);
	}
	@Override
	public double calcGross() {
		double rate = getRate();
		if (rate<0){
			return 0;
		}
		else {
			return round(rate/52);
		}
	}
	@Override
	public double calcYearGross(){
		return getRate();
	}
}
