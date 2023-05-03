package payroll;
import java.util.Comparator;
public class AlphabeticalEmployeeComparator implements Comparator<Employee> {
    public int compare(Employee employee1, Employee employee2){
        return employee1.getFullNameFormal().compareTo(employee2.getFullNameFormal());
    }
}
