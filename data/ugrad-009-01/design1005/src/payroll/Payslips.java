package payroll;
import java.util.LinkedList;
class Payslips implements PaySystem {
    public void printInfo(LinkedList<Person> Employees) {
        list.sortById(Employees);
        date.printDateToday();
        for (Person p : Employees) {
            p.payslipDetails();
        }
    }
}
