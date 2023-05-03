package payroll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class EmployeeList {
	List<Employee> employeeList = new ArrayList<>();
	public void enterRecord(String lineFromFile) {
		String[] recordArray = lineFromFile.split("\\t+");
		Employee employee = new Employee(recordArray);
		employeeList.add(employee);
	}
	public void sortEmployees (List<Employee> employeeList) {
		Collections.sort(employeeList);
	}
	public void sortByFamilyName() {
		Employee.FamilyNameComparator comparator = new Employee.FamilyNameComparator();
		Collections.sort(employeeList, comparator);
	}
}