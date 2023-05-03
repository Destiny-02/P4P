package payroll;
import java.util.Map;
import payroll.Command;
import payroll.Employee;
import java.text.DecimalFormat;
import java.util.Arrays;
public class EmployeesCommand implements Command {
	DecimalFormat formator = new DecimalFormat("#.00");
	public void operation(Information info) {
		String[] employeeData = new String[info.getEmployeeInfo().size()];
		int i = 0;
		int counter = 0;
		for ( Map.Entry<Integer, Employee> entry : info.getEmployeeInfo().entrySet()) {
			if (counter == 0) {
				entry.getValue().getDate();
				counter++;
			}
			employeeData[i] = entry.getValue().getNameLastFirst() + " " + "(" + entry.getValue().getTID() + ")" + " " +  entry.getValue().getEmployment() + ", " + "$" + entry.getValue().getRate() + " " + "YTD:$" + entry.getValue().getNewYTD();
			i++;
		}
		Arrays.sort(employeeData);
		for (int j = 0; j < info.getEmployeeInfo().size(); j++) {
			if (j == info.getEmployeeInfo().size()-1) {
				System.out.print((employeeData[j]));
			} else {
				System.out.println((employeeData[j]));
			}
		}
	}
}
