package payroll;
import java.util.LinkedList;
class PAYE implements PaySystem {
    private float total;
    PAYE(LinkedList<Person> Employees) {
        float t = 0;
        for (Person p : Employees) {
            t = p.addPAYE(t);
        }
        total = t;
    }
    public void printInfo(LinkedList<Person> Employees) {
        date.printDateToday();
        Employees.get(0).printPeriod();
        System.out.println(String.format("\nTotal PAYE: $%.2f",total));
    }
}
