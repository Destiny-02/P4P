package payroll.processing;
import java.util.ArrayList;
import payroll.Employee;
public abstract class Process {
    protected String getCurrentDate() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    }
    public abstract void doWork(ArrayList<Employee.Payslip> payslips);
}
