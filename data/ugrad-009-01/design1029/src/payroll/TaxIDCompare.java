package payroll;
import java.util.Comparator;
public class TaxIDCompare implements Comparator<Employee> {
	public int compare(Employee employeeA, Employee employeeB){
		Integer taxIDA = employeeA.taxID();
		Integer taxIDB = employeeB.taxID();
		return taxIDA.compareTo(taxIDB);
	}
}
