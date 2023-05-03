package payroll;
public class EarningsErrorException extends Exception {
	public EarningsErrorException(String taxID){
		System.out.println("\n**WARNING: An employee's (TID for employee: \"" + taxID + "\") net and/or gross earnings has been calculated to be less than zero. Please check the input file for any irregularities**");
		System.out.println("**The employee has been excluded from the processing as a negative amount for net or gross earnings is not possible**\n");
	}
}
