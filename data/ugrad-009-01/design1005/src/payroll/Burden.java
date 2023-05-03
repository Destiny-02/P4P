package payroll;
import java.util.LinkedList;
class Burden implements PaySystem {
    private float total;
    Burden(LinkedList<Person> Employees) {
        float g = 0;
        for (Person p : Employees) {
            g = p.addGross(g);
        }
        total = g;
    }
    public void printInfo(LinkedList<Person> Employees) {
        date.printDateToday();
        Employees.get(0).printPeriod();
        System.out.println(String.format("\nTotal Burden: $%.2f",total));
    }
}
