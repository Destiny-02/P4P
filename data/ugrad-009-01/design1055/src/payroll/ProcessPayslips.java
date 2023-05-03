package payroll;
import java.text.DecimalFormat;
import java.util.Collections;
public class ProcessPayslips extends Prints implements Command  {
    public void engage(EmployeeList personlist){
        printdate();
        Employee CurrentPerson;
        Collections.sort(personlist);
        for(int i = 0; i<personlist.size(); i++){
            CurrentPerson = personlist.get(i);
            double gross = CurrentPerson.CalGross();
            double PAYE = CurrentPerson.CalPAYE(gross);
            double Nett = CurrentPerson.CalNett(gross,PAYE);
            CurrentPerson.PrintOut(gross,PAYE,Nett);
            System.out.println();
        }
    }
}
