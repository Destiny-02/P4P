package payroll;
import java.util.Collections;
public class ProcessEmployees extends ProcessPayslips implements Command{
    public void engage(EmployeeList personlist){
        Collections.sort(personlist, personlist.get(0).new Compare());
        printdate();
        Employee CurrentPerson;
        for(int i = 0; i<personlist.size(); i++){
            CurrentPerson = personlist.get(i);
            double gross = CurrentPerson.CalGross();
            CurrentPerson.PrintOut(gross);
        }
    }
}
