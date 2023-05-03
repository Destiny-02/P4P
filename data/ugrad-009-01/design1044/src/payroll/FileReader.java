package payroll;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class FileReader {
	private String _line;
	private String[] _lineSplit;
	private Scanner _fileScanner;
	private String _filePath;
	private int _lineNumber;
	public FileReader(String filepath) throws FileNotFoundException {
		_filePath = filepath;
		_fileScanner = null;
		_fileScanner = new Scanner(new File(_filePath));
		_lineNumber = 0;
	}
	public String[] getEmployeeLine() throws EmployeeDataException {
		if (_fileScanner != null) {
			while (_fileScanner.hasNextLine()) {
				_line = _fileScanner.nextLine();
				_lineNumber++;
				if (_line.length() != 0) {
					if (_line.charAt(0) != '#') {
						_lineSplit = _line.split("\t");
						if (_lineSplit.length != 9) {
							String error;
							if (_lineSplit.length > 9) {
								error = "Too many elements";
							} else {
								error = "Too few elements";
							}
							throw new EmployeeDataException(_lineSplit, _lineNumber, _filePath, error);
						}
						return _lineSplit;
					}
				}
			}
		}
		return null;
	}
	public int getCurrentLineNumber() {
		return _lineNumber;
	}
	public boolean hasNextLine() {
		return _fileScanner.hasNextLine();
	}
	public void close() {
		_fileScanner.close();
	}
}
