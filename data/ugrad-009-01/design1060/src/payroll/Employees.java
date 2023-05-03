package payroll;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
public class Employees implements Form{
	private Integer[] _printOrder;
	private final EmployeeRecords _employeeRecords;
	Employees(EmployeeRecords employeeRecords) {
		_employeeRecords = employeeRecords;
		_printOrder = null;
	}
	public void processForm(){
		String nameString;
		Map<String, Integer> mapToSortKeys = new HashMap<String, Integer>();
		for (Integer key : _employeeRecords.getKeySet()){
			nameString = (_employeeRecords.getEmployee(key)).orderFormat();
			mapToSortKeys.put(nameString, key);
		}
		Object[] objectArray = mapToSortKeys.keySet().toArray();
		String[] tempStringArray = Arrays.copyOf(objectArray, objectArray.length, String[].class);
		Arrays.sort(tempStringArray);
		_printOrder = new Integer[objectArray.length];
		for (int i = 0; i < tempStringArray.length; i++){
			_printOrder[i] = mapToSortKeys.get(tempStringArray[i]);
		}
	}
	Integer[] getPrintOrder() {
		return _printOrder;
	}
}
