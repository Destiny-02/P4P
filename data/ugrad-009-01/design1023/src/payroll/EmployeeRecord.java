package payroll;
import java.util.LinkedList;
import java.util.ArrayList;
public class EmployeeRecord{
	private ArrayList<Employee> employeeRecord = new ArrayList<Employee>();
	public EmployeeRecord(LinkedList<String> fileLines){
		for(String line : fileLines){
			String[] employeeData = line.split("\t");
			Employee employee = new Employee(employeeData[0],employeeData[1],employeeData[2],employeeData[3],employeeData[4],employeeData[5],employeeData[6],employeeData[7],employeeData[8]);
			employeeRecord.add(employee);
		}
	}
	public Employee get(int index){
		return employeeRecord.get(index);
	}
	public int size(){
		return employeeRecord.size();
	}
}
