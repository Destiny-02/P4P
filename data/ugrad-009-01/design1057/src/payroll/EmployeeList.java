
package payroll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import payrollExceptions.EmployeeNotUniqueException;
import payrollExceptions.InvalidDataException;
public class EmployeeList implements Iterable<AbstractEmployee>{
	private List<AbstractEmployee> list = new ArrayList<>();
	private int index = 0;
	public EmployeeList(FileReader file) {
		AbstractEmployee employee;
		while((employee = file.getAEmployee()) != null) {
			try {
				verify(employee);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.exit(1);
			}
			list.add(employee);
		}
	}
	public boolean hasNext() {
		if(list.get(index) != null) {
			return true;
		}
		else {
			return false;
		}
	}
	public AbstractEmployee next() {
		return list.get(index++);
	}
	public Iterator<AbstractEmployee> iterator(){
		return list.iterator();
	}
	public AbstractEmployee getNextEmployee() {
		if(index < list.size()) {
			return list.get(index++);
		}
		else {
			index = 0;
			return null;
		}
	}
	public Date getPayPeriod() {
		assert list.size() >= 1;
		return list.get(0).getDates();
	}
	private void verify(AbstractEmployee employee) throws EmployeeNotUniqueException,
			InvalidDataException{
		if(list.contains(employee)) {
			throw new EmployeeNotUniqueException("Employee ID" +
					employee.getTaxID() + " is not unique.");
		}
		for(AbstractEmployee each : list) {
			if(each.getTaxID() == employee.getTaxID()) {
				throw new EmployeeNotUniqueException("Employee ID" +
						employee.getTaxID() + " is not unique.");
			}
		}
		if(employee.getDeduction() < 0.0) {
			throw new InvalidDataException("Employee ID:" + employee.getTaxID() +
											" has invalid deduction value: " + employee.getDeduction());
		}
		if(employee.getNett() < 0.0) {
			throw new InvalidDataException("Employee ID:" + employee.getTaxID() +
											" has invalid Nett value: " + employee.getNett());
		}
		if(employee.getYtd() < 0.0) {
			throw new InvalidDataException("Employee ID:" + employee.getTaxID() +
											" has invalid Ytd value: " + employee.getYtd());
		}
	}
	public void sortByTID() {
		Collections.sort(list, new Comparator<AbstractEmployee>() {
	        @Override
	        public int compare(AbstractEmployee employee1, AbstractEmployee employee2)
	        {
	            return  (new Integer(employee1.getTaxID())).compareTo(employee2.getTaxID());
	        }
	    });
	}
	public void sortByLastName() {
		Collections.sort(list, new Comparator<AbstractEmployee>() {
	        @Override
	        public int compare(AbstractEmployee employee1, AbstractEmployee employee2)
	        {
	            return  employee1.getName().getLastName().compareTo(employee2.getName().getLastName());
	        }
	    });
	}
}
