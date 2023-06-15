package payroll;
public class DuplicateEmployeeException extends Exception {
	public DuplicateEmployeeException(String taxID){
		System.out.println("**WARNING: Possible duplicate employee(TID: \""+ taxID + "\"). Check input file and try again!**");
	}
}
