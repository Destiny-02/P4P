package payroll;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
public class Payroll {
	public static void main(String[] args)throws IOException {
		BufferedReader fis = new BufferedReader(new FileReader(args[0]));
		EmployeeList employeeList = new EmployeeList();
		employeeList.generateEmployeeList(fis);
		Command process = Processing.returnProcessObject(args[1]);
		employeeList.displayDate();
		process.process(employeeList);
        fis.close();
	}
}
