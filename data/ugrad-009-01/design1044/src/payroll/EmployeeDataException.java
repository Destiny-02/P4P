package payroll;
public class EmployeeDataException extends Exception {
	private static final long serialVersionUID = 1L;
	private String[] _data;
	private String[] _data2 = null;
	private int _line;
	private int _line2;
	private String _filepath;
	private String _message;
	public EmployeeDataException(String[] employeeData, int line, String filePath, String message) {
		_data = employeeData;
		_line = line;
		_filepath = filePath;
		_message = message;
	}
	public EmployeeDataException(String[] employeeData, String[] employeeData2, int line, int line2, String filePath,
			String message) {
		_data = employeeData;
		_data2 = employeeData2;
		_line = line;
		_line2 = line2;
		_filepath = filePath;
		_message = message;
	}
	public String getMessage() {
		String data = null;
		if (_data.length != 0) {
			data = "";
		}
		data += "File: " + _filepath + "\nLine " + Integer.toString(_line) + ": ";
		for (String i : _data) {
			data += i + "\t";
		}
		if (_data2 != null) {
			data += "\nLine " + Integer.toString(_line2) + ": ";
			for (String i : _data2) {
				data += i + "\t";
			}
		}
		data += "\nMessage: " + _message;
		return data;
	}
}
