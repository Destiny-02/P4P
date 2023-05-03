package payroll;
import java.util.Comparator;
public class NameComparator implements Comparator<Employee.Payslip> {
    public int compare(Employee.Payslip p1, Employee.Payslip p2) {
        return p1.compareName(p2);
    }
}
