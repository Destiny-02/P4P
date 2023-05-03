package payroll;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
public class InputProcessing {
	private int _lineCount=1;
	private String _fileName;
	private HashSet<Integer> _TIDSetCheck;
	InputProcessing(String fileName){
		_fileName = fileName;
		_TIDSetCheck = new HashSet<Integer>();
	}
	public EmployeeRecords getInputInfo(){
		EmployeeRecords employeeData = new EmployeeRecords();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(_fileName));
			String currentEmployeeLine = null;
			boolean hasThereBeenError = false;
			while ((currentEmployeeLine = reader.readLine()) != null) {
				if(currentEmployeeLine.length()<1||currentEmployeeLine.charAt(0)=='#'){
					_lineCount = _lineCount + 1;
				}else{
					ErrorChecking errorCheck = new ErrorChecking(currentEmployeeLine, _TIDSetCheck);
					try{
						errorCheck.runErrorChecking();
						Employee currentEmployee = new Employee(currentEmployeeLine);
						_TIDSetCheck.add(currentEmployee.getTID());
						employeeData.setEmployee(currentEmployee);
						_lineCount = _lineCount + 1;
					} catch(InputLineException caughtException) {
						if(hasThereBeenError == false){
							hasThereBeenError = true;
							System.out.println("----------ERROR REPORT----------");
						}
						System.out.print("There was an error at line "+_lineCount);
						System.out.println(", "+caughtException.returnErrorMessage());
						_lineCount = _lineCount + 1;
					}
				}
			}
			reader.close();
			if(hasThereBeenError==true){
				System.out.println("--------END ERROR REPORT--------");
				System.out.println("");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return employeeData;
	}
	public void runProcessType(String argumentInput, EmployeeRecords employeeData){
		if(argumentInput.equals("Payslips")){
			employeeData.runPayslipProcessing();
		} else if(argumentInput.equals("Employees")){
			employeeData.runEmployeeProcessing();
		} else if(argumentInput.equals("PAYE")){
			employeeData.runPAYEProccessing();
		} else if(argumentInput.equals("Burden")){
			employeeData.runBurdenProccessing();
		} else {
			System.out.println("Could not determine processing type: '"+argumentInput+"'");
		}
	}
}
