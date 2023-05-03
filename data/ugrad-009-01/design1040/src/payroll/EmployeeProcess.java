package payroll;
import java.util.ArrayList;
public class EmployeeProcess implements Process {
    public boolean engage() {
        Roster theRoster = Payroll.getRoster();
        ArrayList<Employee> sortedRoster = theRoster.getNameSorted();
        System.out.println(theRoster.giveDate());
        for (Employee currentEmp : sortedRoster) {
            currentEmp.updateEmp();
            System.out.print(currentEmp.name);
            System.out.print(" (" + currentEmp.taxID + ")");
            System.out.print(" " + currentEmp.getSalaried());
            System.out.print(", " + currentEmp.stringRate() + " ");
            System.out.println("YTD:" + currentEmp.stringNewYTD());
        }
        return true;
    }
}
