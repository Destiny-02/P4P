package payroll;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
public class Sort{
	static final Comparator<Employee> TAXID_ORDER = new Comparator<Employee>(){ public int compare(Employee e1, Employee e2) {
		Integer taxIDe1 = Integer.parseInt(e1.getTaxID());
        Integer taxIDe2 = Integer.parseInt(e2.getTaxID());
		return taxIDe1.compareTo(taxIDe2);
		}
    };
    static final Comparator<Employee> NAME_ORDER = new Comparator<Employee>(){ public int compare(Employee e1, Employee e2) {
    	String e1LastName = e1.getGivenNames(false)[0];
        String e2LastName = e2.getGivenNames(false)[0];
    	return e1LastName.compareTo(e2LastName);
    	}
    };
    public void sorter(boolean tidOrName, ArrayList<Employee> employeeList){
    	if(tidOrName){
    		Collections.sort(employeeList, TAXID_ORDER);
    	} else if (!tidOrName) {
    		Collections.sort(employeeList, NAME_ORDER);
    	}
    }
}