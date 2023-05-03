package payroll;
public class ProccessBurden extends ProcessPayslips implements Command{
    public void engage(EmployeeList personlist) {
        printdate();
        Employee CurrentPerson;
        String dates = "";
        double burden = 0.0;
        for (int i = 0; i < personlist.size(); i++) {
            CurrentPerson = personlist.get(i);
            burden = burden + CurrentPerson.CalGross();
            dates = CurrentPerson.GetTimePeriod();
        }
        printTotals(dates,burden,"Burden");
    }
}
