
package payroll;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Payroll {
	public final static int WEEKS_IN_A_YEAR = 52;
	public static void main(String[] args) throws IOException{
		String fileName = args[0];
		BufferedReader recordsReader = new BufferedReader(new FileReader(new File(fileName)));
		String processingType = args[1];
		String employeeDetailsLine;
		Employee access = new Employee();
		Employee.EmployeeList employeeList = access.new EmployeeList();
		while((employeeDetailsLine = recordsReader.readLine()) != null){
			if (employeeDetailsLine.equals("")==false && employeeDetailsLine.charAt(0) != '#' ){
				String[] splitEmployeeDetails = employeeDetailsLine.trim().split("\\t|\\s+");
				employeeList.addNewEmployee(splitEmployeeDetails);
			}
		}
		recordsReader.close();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateToday = new Date();
		System.out.println(dateFormat.format(dateToday));
		Employee.PrintInformation outputGenerator = access.new PrintInformation();
		try{
			outputGenerator.output(processingType, employeeList.returnEmployeeList());
		}
		catch(IOException e){
			System.out.println("ERROR: Processing type is invalid.");
		}
	}
	public static double round2DP(double toRound){
		return Math.round(toRound*100)/100;
	}
}