
package payroll;
public class PayRollSystemException extends Exception {
	public PayRollSystemException(String message, String extraDetails){
		super("\n\tIncorrect formatting on employee details below.\n\t" + message + "\n\t" + extraDetails);
	}
}
