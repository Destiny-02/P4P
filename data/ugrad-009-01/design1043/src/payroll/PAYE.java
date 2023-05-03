package payroll;
public class PAYE extends Gross {
	public double calcPaye(Employee employee) {
		double gross = calcGross(employee);
		double annualGross = gross * 52;
		double remainingIncome = annualGross;
		double annualTax = 0;
		if(annualGross <=14000){
			annualGross = nc.round(annualGross * 0.105);
			remainingIncome = 0;
		}
		else{
			annualTax += nc.round(14000 * .105);
			remainingIncome -= 14000;
		}
		if(remainingIncome <= 34000){
			annualTax += nc.round(remainingIncome * .175);
			remainingIncome = 0;
		}
		else{
			annualTax += nc.round(34000 * .175);
			remainingIncome -= 34000;
		}
		if(remainingIncome <= 22000){
			annualTax += nc.round(remainingIncome * .3);
			remainingIncome =0;
		}
		else{
			annualTax += nc.round(22000 * .3);
			remainingIncome -= 22000;
		}
		if(remainingIncome >0){
			annualTax += nc.round(remainingIncome * .33);
		}
		return nc.round(annualTax/52);
	}
	public double grossSubtractPaye(Employee employee){
		return calcGross(employee) - calcPaye(employee);
	}
	public void printPaye(Employee employee){
		double tax = calcPaye(employee);
		System.out.printf("$%.2f, ", tax);
	}
}
