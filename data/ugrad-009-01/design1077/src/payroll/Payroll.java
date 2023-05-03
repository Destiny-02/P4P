package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Payroll {
	public static void main(String[] args) throws IOException {
		Store mainStore = new Store();
		EmployeeList employees = new EmployeeList(mainStore);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));
		String employeeLine = bufferedReader.readLine();
		while(employeeLine != null) {
			if (!employeeLine.startsWith("#") && !employeeLine.isEmpty()) {
				String[] employeeInfo = employeeLine.split("\t");
				employees.addEmployee(employeeInfo);
			}
			employeeLine = bufferedReader.readLine();
		}
		bufferedReader.close();
		mainStore.runProcess(args[1], employees);
	}
}
