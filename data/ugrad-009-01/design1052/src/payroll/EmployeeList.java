package payroll;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
public class EmployeeList implements Iterable<Employee>{
	private List<Employee> _employees = new Vector<Employee>();
	private String _datePeriod;
	public boolean add(Employee employee){
		if( _employees.size() == 0){
			_datePeriod = employee.getPeriod();
		} else if (!_datePeriod.equals(employee.getPeriod())){
			throw new RuntimeException("Employee: " + employee.toStringPayslip() +
					" has incorrect period. \nExpected period was " + _datePeriod );
		} else {
			for (Employee storedEmployee:_employees){
				if (storedEmployee.compareTo(employee) == 0){
					throw new RuntimeException("Employee: " + employee.toStringPayslip() +
							" has a non-unique TaxID" );
				}
			}
		}
		_employees.add(employee);
		return true;
	}
	public String getDatePeriod(){
		return _datePeriod;
	}
	public void sortByLastName(){
		Employee.ByLastNameComparetor comparetor = new Employee.ByLastNameComparetor();
		_employees.sort(comparetor);
	}
	public void sortByFirstName(){
		Employee.ByFirstNameComparetor comparetor = new Employee.ByFirstNameComparetor();
		_employees.sort(comparetor);
	}
	public void sortByTaxID(){
		Employee.ByTaxIDComparetor comparetor = new Employee.ByTaxIDComparetor();
		_employees.sort(comparetor);
	}
	public Iterator<Employee> iterator() {
		return  _employees.iterator();
	}
}
