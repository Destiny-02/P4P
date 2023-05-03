package payroll;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
public class EmployeeRecords {
	private final Map<Integer,EmployeeData> _employeeMap;
	EmployeeRecords(){
		_employeeMap = new HashMap<Integer, EmployeeData>();
	}
	public void addToRecord(EmployeeData tempEmploy) throws ErrorMessageTID{
		if (_employeeMap.get(tempEmploy.getTID()) != null){
			throw new ErrorMessageTID();
		} else {
			_employeeMap.put(tempEmploy.getTID(), tempEmploy);
		}
	}
	public Set<Integer> getKeySet(){
		return _employeeMap.keySet();
	}
	public EmployeeData getEmployee(Integer key) {
		return _employeeMap.get(key);
	}
}
