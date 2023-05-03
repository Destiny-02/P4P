package payroll;
public class MissingEmployeeInformationException extends Exception {
	private static final long serialVersionUID = 1L;
	public MissingEmployeeInformationException(String msg){
		super(msg);
	}
}
