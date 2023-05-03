package payroll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
public class EmployeeList implements Iterable<Employable>{
	private ArrayList<Employable> _employeeList;
	public EmployeeList(ArrayList<Employable> list){
		_employeeList = list;
	}
	public void addEmployee(Employable e){
		_employeeList.add(e);
	}
	public void sortByID(){
		Collections.sort(_employeeList, new TaxIDComparator());
	}
	public void sortByLastName(){
		Collections.sort(_employeeList, new LastNameComparator());
	}
	public String getStartDate(){
		return _employeeList.get(0).getStartDate();
	}
	public String getEndDate(){
		return _employeeList.get(0).getEndDate();
	}
	public double sumBurden(){
		double burden = 0;
		for (Employable e: _employeeList){
			burden += e.getWeekly();
		}
		return burden;
	}
	public double sumPaye(){
		double paye = 0;
		for (Employable e: _employeeList){
			paye += e.getPaye();
		}
		return paye;
	}
	public Iterator<Employable> iterator() {
		return new Iterator<Employable>() {
			private int _current = 0;
			public boolean hasNext() {
				return _current < _employeeList.size();
			}
			public Employable next() {
				return _employeeList.get(_current++);
			}
			public void remove() {
				throw new UnsupportedOperationException("Not available!");
			}
		};
	}
}
