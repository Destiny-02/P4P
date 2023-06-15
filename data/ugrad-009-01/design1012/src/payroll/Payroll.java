package payroll;
import java.util.ArrayList;
import java.io.FileNotFoundException;
public class Payroll {
	public static void main(String[] args){
		try{
		FileReader inputFileReader = new FileReader();
		EmployeeList employeeList = inputFileReader.reader(args[0]);
		ArrayList<Employee> mainList = new ArrayList<Employee>();
		mainList = employeeList.copyList();
		Sort sorterForProcessing = new Sort();
		String processing = args[1];
		if(processing.equals("Payslips")){
			sorterForProcessing.sorter(true, mainList);
			Processing.payslipsProcessing(mainList);
		}
		else if(processing.equals("Employees")){
			sorterForProcessing.sorter(false, mainList);
			Processing.employeesProcessing(mainList);
		}
		else if(processing.equals("Burden")){
			Processing.burdenProcessing(mainList);
		}
		else if(processing.equals("PAYE")){
			Processing.PAYEProcessing(mainList);
		}
		else{
			System.out.println("The processing type specified is invalid or does not exist. Please check the arguments to the program");
		}
		} catch(FileNotFoundException FNFEx) {
			System.out.println("Error: Could not find the file \"" + args[0] + "\". Please try again ");
		}
	}
}
