package payroll;
import java.util.LinkedList;
public class EmployeeList extends LinkedList<Employee>{
    private LinkedList<Employee> _contents;
    public EmployeeList(){
         _contents = new LinkedList<Employee>();
    }
}
