package payroll;
import java.lang.Comparable;
public class Error implements Comparable<Error> {
	private String _message;
	private int _line;
	public Error(String message, int line) {
		_message = message;
		_line = line;
	}
	public int compareTo(Error o) {
		return ((Integer)_line).compareTo(o._line);
	}
	public String toString() {
		return "Line " + _line + ": " + _message;
	}
}
