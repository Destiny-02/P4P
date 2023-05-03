package payroll.processing;
import java.util.ArrayList;
import payroll.Employee;
import payroll.CurrencyRounder;
public class BurdenProcess extends Process {
    public void doWork(ArrayList<Employee.Payslip> payslips) {
        CurrencyRounder rounder = new CurrencyRounder();
        double burdenTotal = 0.0;
        System.out.println(getCurrentDate());
        System.out.println(payslips.get(0).printWorkPeriod());
        for (Employee.Payslip payslip : payslips) {
            burdenTotal += payslip.retrieveGross();
        }
        System.out.println("Total Burden: " + rounder.display(burdenTotal));
    }
}
