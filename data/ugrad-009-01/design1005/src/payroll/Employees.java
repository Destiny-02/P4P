package payroll;
import java.util.LinkedList;
class Employees implements PaySystem {
    public void printInfo(LinkedList<Person> Employees) {
        list.sortByName(Employees);
        date.printDateToday();
        for (Person p: Employees) {
            p.employeeDetails();
        }
    }
}
