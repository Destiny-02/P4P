package payroll;
import java.io.*;
public class getInput {
	private String _fileName;
	private EmployeeList _myEmployees;
	public getInput(String fileName, EmployeeList myEmployees){
		_fileName = fileName;
		_myEmployees = myEmployees;
	}
	public void ReadFile(){
		try{
			String myInputLine = null;
			FileReader fileReader = new FileReader(_fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((myInputLine = bufferedReader.readLine()) != null){
				if(myInputLine.compareTo("") == 0){
					continue;
				}
				char firstChar = myInputLine.charAt(0);
				if((firstChar != '#') && !myInputLine.isEmpty()){
					String[] employeeDataArray = myInputLine.split("\t");
					if(employeeDataArray.length == 9){
						int taxID = Integer.parseInt(employeeDataArray[0]);
						String name = employeeDataArray[1];
						String employmentType = employeeDataArray[2];
						double rate = Double.parseDouble(employeeDataArray[3].replaceAll("[$]", ""));
						double yearToDate = Double.parseDouble(employeeDataArray[4].replaceAll("[$]", ""));
						String startDate = employeeDataArray[5];
						String endDate = employeeDataArray[6];
						double hours = Double.parseDouble(employeeDataArray[7]);
						double deduction = Double.parseDouble(employeeDataArray[8].replaceAll("[$]", ""));
						Employee newEmployee = new Employee(taxID, name, employmentType, rate, yearToDate, startDate, endDate, hours, deduction);
						newEmployee.calculateAllData();
						_myEmployees.addEmployee(newEmployee);
					}
				}
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + _fileName + "'");
		}
		catch(IOException ex){
			System.out.println("Error reading file '" + _fileName + "'");
		}
	}
}
