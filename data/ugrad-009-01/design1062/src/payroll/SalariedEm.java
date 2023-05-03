package payroll;
public class SalariedEm{
	public double calculateSalarisedGross(double rate){
		double totalGross = Math.round((rate/52) * 100);
		totalGross = totalGross / 100;
		return totalGross;
	}
}
