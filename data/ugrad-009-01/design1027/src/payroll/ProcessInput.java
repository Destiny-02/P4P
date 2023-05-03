package payroll;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
public class ProcessInput {
	private EmployeeList _myList;
	private String _processType;
	ProcessInput(String fileName, String processType) {
		_processType = processType;
		readFile(fileName);
	}
	private void readFile(String fileName) {
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			_myList = new EmployeeList();
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() > 1) {
					if (line.charAt(0) != '#'){
						_myList.addToList(line);
					}
				}
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File has not been found! Please try again");
		} catch (IOException e) {
			System.out.println("There is an error, IO Exception");
		}
	}
	public void processEmployees() {
		_myList.calculateEmployees(_processType);
	}
}
