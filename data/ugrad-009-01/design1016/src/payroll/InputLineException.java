package payroll;
@SuppressWarnings("serial")
public class InputLineException extends Exception {
	private String _errorMessage;
	InputLineException (String errorMessage){
		_errorMessage = errorMessage;
	}
	public String returnErrorMessage(){
		return _errorMessage;
	}
}
