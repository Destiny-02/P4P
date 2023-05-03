package payroll;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
public class EmployeeList {
	private List<Employee> _employeeList = new Vector<Employee>();
	public EmployeeList(String[] args){
		ErrorChecker errorChecker = new ErrorChecker();
		if(errorChecker.checkArguments(args)){
			try{
				File employeeRecords = new File(args[0]);
				Scanner scan = new Scanner(employeeRecords);
				while(scan.hasNextLine()){
					String employeeRecord = scan.nextLine();
					if (!(employeeRecord.isEmpty())){
						if(employeeRecord.charAt(0) != '#'){
							errorChecker.checkEmployeeRecord(employeeRecord);
							Employee employeeDetails = new Employee(employeeRecord);
							_employeeList.add(employeeDetails);
						}
					}
				}
				scan.close();
			} catch (FileNotFoundException e){
				throw new PayrollException("File not found, run the program again with the right file name.");
			}
		} else {
			throw new PayrollException("There is a problem with the input arguments, make sure that you have only entered 2 and the processing type has to be one of the 4 provided.");
		}
	}
	public List<Employee> getListOfEmployees(){
		return _employeeList;
	}
}
