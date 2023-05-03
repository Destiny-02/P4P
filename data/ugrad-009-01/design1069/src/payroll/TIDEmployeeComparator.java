package payroll;
import java.util.Comparator;
public class TIDEmployeeComparator implements Comparator<Employee> {
    public int compare(Employee employee1, Employee employee2){
        return Integer.compare(employee1.getTid(),employee2.getTid());
    }
}

