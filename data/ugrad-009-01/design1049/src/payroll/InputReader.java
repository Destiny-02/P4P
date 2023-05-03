package payroll;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
public class InputReader {
	private Scanner _scanner;
	public void openInputFile(String file){
		try {
			_scanner = new Scanner(new File(file));
		} catch (Exception e1) {
			System.out.println("Could not find file: " + file);
		}
	}
	public ArrayList<String> readValidData() {
		String currentLine;
		ArrayList<String> Lines = new ArrayList<String>();
		while (_scanner.hasNext()) {
			currentLine = _scanner.nextLine();
			if ((!(currentLine.startsWith("#"))) && (!(currentLine.isEmpty()))) {
				Lines.add(currentLine);
			}
		}
		return Lines;
	}
	public void closeInputFile() {
		_scanner.close();
	}
	public EmployeeInfo[] readInput(String fileName) {
		openInputFile(fileName);
		ArrayList<String> employeeDataString = readValidData();
		closeInputFile();
		EmployeeInfo[] employeeData = new EmployeeInfo[employeeDataString.size()];
		for (int i = 0; i < employeeDataString.size(); i++) {
			employeeData[i] = new EmployeeInfo(employeeDataString.get(i));
		}
		return employeeData;
	}
}

