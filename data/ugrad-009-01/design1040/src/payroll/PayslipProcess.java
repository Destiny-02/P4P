package payroll;
import java.util.ArrayList;
public class PayslipProcess implements Process {
    public boolean engage() {
        Roster theRoster = Payroll.getRoster();
        ArrayList<Employee> sortedRoster = theRoster.getIDSorted();
        System.out.println(theRoster.giveDate());
        for (Employee currentEmp : sortedRoster) {
            currentEmp.updateEmp();
            System.out.print(currentEmp.taxID);
            System.out.print(". " + currentEmp.fullName);
            System.out.print(", " + "Period: " + currentEmp.startDay + " to " + currentEmp.endDay);
            System.out.print(". " + "Gross: " + currentEmp.stringGross());
            System.out.print(", " + "PAYE: " + currentEmp.stringTax());
            System.out.print(", " + "Deductions: " + currentEmp.stringDeducts());
            System.out.print(" " + "Nett: " + currentEmp.stringNet());
            System.out.println(" " + "YTD: " + currentEmp.stringNewYTD());
        }
            return true;
    }
}
