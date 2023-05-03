package payroll;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
public class Company {
	Map<Integer, Employee> _EmpMap = new Hashtable<Integer, Employee>();
	public void addEmployee(Employee Employee){
		_EmpMap.put(Employee.getTaxID(), Employee);
	}
	public void writeEmployees() {
		List<Employee> listValues = new ArrayList<Employee>(_EmpMap.values());
		Collections.sort(listValues);
		for(Employee employee: listValues) {
			System.out.println(employee);
		}
	}
	public void writePayslips() throws Exception {
		List<Integer> idList = new ArrayList<Integer>(_EmpMap.keySet());
		Collections.sort(idList);
		for(Integer i: idList) {
			System.out.println(_EmpMap.get(i).getPayslip());
		}
	}
	public void writeBurden() {
		BigDecimal burden = BigDecimal.ZERO;
		String dates = new String();
		List<Employee> ListValues = new ArrayList<Employee>(_EmpMap.values());
		for(Employee i: ListValues) {
			burden = burden.add(i.getGross());
			dates = i.formatPayPeriod();
		}
		System.out.println(dates);
		System.out.println("Total Burden: $" + burden);
	}
	public void writePAYE() {
		BigDecimal paye = BigDecimal.ZERO;
		String dates = new String();
		List<Employee> ListValues = new ArrayList<Employee>(_EmpMap.values());
		for(Employee i: ListValues) {
			paye = paye.add(i.getPAYE());
			dates = i.formatPayPeriod();
		}
		System.out.println(dates);
		System.out.println("Total PAYE: $" + paye);
	}
	public Employee getEmployee(int TaxID) {
		if(_EmpMap.containsKey(TaxID)) {
			return _EmpMap.get(TaxID);
		}
		return null;
	}
}
