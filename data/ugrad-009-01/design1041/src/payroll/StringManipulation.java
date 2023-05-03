package payroll;
public class StringManipulation {
	private String _string;
	public StringManipulation(String string) {
		_string = string;
	}
	public String removeFirstChar () {
		StringBuilder newString = new StringBuilder(_string);
		newString.deleteCharAt(0);
		String outputString = new String(newString);
		return outputString;
	}
}
