package payroll;
import java.util.ArrayList;
import static java.util.Collections.sort;
public class ErrorLog {
	private ArrayList<Error> _errorLog;
	public ErrorLog() {
		_errorLog = new ArrayList<Error>();
	}
	public void Add(String errorMessage, int line) {
		Error err = new Error(errorMessage, line);
		_errorLog.add(err);
	}
	public String toString() {
		sort(_errorLog);
		String returnString = "";
		if (_errorLog.size() >= 1) {
			returnString += (_errorLog.size() + " Errors.");
			for (Error er : _errorLog) {
				System.out.println(er.toString() + "\n");
			}
		}
		return returnString;
	}
}
