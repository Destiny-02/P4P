package payroll;
public class Payslips extends Data {
	public Payslips(int TaxID, String firstName, String lastName, String Employment, double Rate, double YTD,
			double Hours, double Deduction) {
		super(TaxID, firstName, lastName, Employment, Rate, YTD, Hours, Deduction);
	}
	public double giveNett(double gross,double paye){
		return gross-paye-Deduction;
	}
	public void printPayslips(String start, String end, double yTD,
			double gross,double nett,double tax){
		System.out.print(TaxID+"."+" "+lastName+" "+firstName+", Period: "+start+" to "+end+".");
		System.out.printf(" Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f",gross,tax,Deduction,nett,yTD);
		System.out.print("\n");
	}
}
