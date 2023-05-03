package payroll;
public class InputFormatException extends Exception{
	public InputFormatException(String taxID){
		System.out.println("\n**Error: There has been an input format error for employee TID \"" + taxID +"\". Please check the line on which this employee's data is entered**");
		System.out.println("**The name or dates worked for the employee are invalid**");
	}
}
