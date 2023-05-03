package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
public class EmployeeList {
	private ArrayList<Employee> _EmployeeList;
	public EmployeeList(String fileLocation){
		_EmployeeList = new ArrayList<Employee>();
		int index = 1;
		try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))){
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null){
				if(!sCurrentLine.isEmpty() && sCurrentLine.charAt(0) != '#' ){
					_EmployeeList.add(new Employee(sCurrentLine, index));
				}
				index++;
			}
		} catch (IOException ioex) {
			System.err.println("You have caught an IO Exception!");
			System.err.println("Please provide a valid file for reading!");
		}
		HashMap<String, String> TIDCheck = new HashMap<String, String>();
		for(Employee employee : _EmployeeList){
			if (TIDCheck.containsKey(String.valueOf(employee.getTID()))){
				System.out.println("Error: Employee " + employee.getGivenName()
						+ " " + employee.getFamilyName() + " ["
						+ employee.getTID()
						+ "] has a non-unique Income Tax ID");
			} else {
				TIDCheck.put(String.valueOf(employee.getTID()), "HERE");
			}
		}
	}
	public ArrayList<Employee> getEmployeeList(){
		return _EmployeeList;
	}
}
class FamilyNameComparator implements Comparator<Employee>{
	public int compare(Employee employee1, Employee employee2){
		try{
			return employee1.getFamilyName().compareTo(employee2.getFamilyName());
		} catch (NullPointerException npex){
			return 0;
		}
	}
}
class TIDComparator implements Comparator<Employee>{
	public int compare(Employee employee1, Employee employee2){
			return employee1.getTID() - employee2.getTID();
	}
}
class GivenNameComparator implements Comparator<Employee>{
	public int compare(Employee employee1, Employee employee2){
		try{
		return employee1.getGivenName().compareTo(employee2.getGivenName());
		} catch (NullPointerException npex){
			return 0;
		}
	}
}
