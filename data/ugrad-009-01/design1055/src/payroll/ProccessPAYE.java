package payroll;
public class ProccessPAYE extends ProcessPayslips implements Command{
    public void engage(EmployeeList personlist){
        printdate();
        Employee CurrentPerson;
        String dates = "";
        double gross;
        double PAYE = 0.0;
        for (int i = 0; i < personlist.size(); i++) {
            CurrentPerson = personlist.get(i);
            gross = CurrentPerson.CalGross();
            PAYE = PAYE + CurrentPerson.CalPAYE(gross);
            dates = CurrentPerson.GetTimePeriod();
        }
        printTotals(dates,PAYE,"PAYE");
    }
}
