package payroll.processing;
import java.util.ArrayList;
import payroll.Employee;
import payroll.CurrencyRounder;
public class PAYEProcess extends Process {
    public void doWork(ArrayList<Employee.Payslip> payslips) {
        CurrencyRounder rounder = new CurrencyRounder();
        double payeTotal = 0.0;
        System.out.println(getCurrentDate());
        System.out.println(payslips.get(0).printWorkPeriod());
        for (Employee.Payslip payslip : payslips) {
            payeTotal += payslip.retrievePAYE();
        }
        System.out.println("Total PAYE: " + rounder.display(payeTotal));
    }
}
