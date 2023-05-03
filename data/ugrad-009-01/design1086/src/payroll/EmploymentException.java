package payroll;
public class EmploymentException extends Exception {
	private String[] _information;
	public EmploymentException(String[] information){
		_information = information;
	}
	public void printEmploymentError(){
		System.out.println("Error: Employee with taxID " + _information[0] +
				" has invalid employment (" + _information[2] + ")" );
	}
}

