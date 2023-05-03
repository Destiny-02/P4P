package payroll.processing;
import java.util.ArrayList;
import java.util.Collections;
import payroll.Employee;
import payroll.NameComparator;
public class EmployeesProcess extends Process {
    public void doWork(ArrayList<Employee.Payslip> payslips) {
        Collections.sort(payslips, new NameComparator());
        System.out.println(getCurrentDate());
        for (Employee.Payslip payslip : payslips) {
            System.out.println(payslip.printEmployee());
        }
    }
}
