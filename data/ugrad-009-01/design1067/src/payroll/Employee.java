package payroll;
public class Employee extends Data{
	public Employee(int TaxID, String firstName, String lastName, String Employment, double Rate, double YTD,
			double Hours) {
		super(TaxID, firstName, lastName, Employment, Rate, YTD, Hours);
	}
	public double calculate_newYTD(double gross){
		return gross+YTD;
	}
	public void printEmployees(double ytd){
		System.out.print(lastName+", "+firstName+" ("+TaxID+") "+Employment+", ");
		System.out.printf("$%.2f YTD:$%.2f",Rate,ytd);
		System.out.print("\n");
	}
}
