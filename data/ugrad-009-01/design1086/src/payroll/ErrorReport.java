package payroll;
public class ErrorReport {
	public void printDateError(){
		System.out.println("date is in the wrong format");
	}
	public static void printNegativeNumError(int taxID){
		System.out.println("Error: Employee with taxID " + taxID + " has invalid value as a input (please check if all values are positive)");
	}
	public static void printDuplicateTaxIDError(int taxID, String name){
		System.out.println("Error: taxID " + taxID + " (" + name + ")" + " already exists");
	}
}
