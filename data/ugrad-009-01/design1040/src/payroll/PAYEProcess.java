package payroll;
import java.util.ArrayList;
public class PAYEProcess implements Process {
    public boolean engage() {
        Roster theRoster = Payroll.getRoster();
        ArrayList<Employee> sortedRoster = theRoster.getIDSorted();
        System.out.println(theRoster.giveDate());
        System.out.println(sortedRoster.get(0).startDay + " to " + sortedRoster.get(0).endDay);
        System.out.println("Total PAYE: " + theRoster.getTotalPAYE());
        return true;
    }
}