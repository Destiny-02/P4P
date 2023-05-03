package payroll.processing;
import java.util.ArrayList;
import java.util.Collections;
import payroll.Employee;
public class PayslipsProcess extends Process {
    public void doWork(ArrayList<Employee.Payslip> payslips) {
        Collections.sort(payslips);
        System.out.println(getCurrentDate());
        for (Employee.Payslip payslip : payslips) {
            System.out.println(payslip.printPayslip());
        }
    }
}
