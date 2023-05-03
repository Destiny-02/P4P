package payroll;
import java.util.LinkedList;
interface PaySystem {
    EmployeeList list = new EmployeeList();
    Day date = new Day();
    void printInfo(LinkedList<Person> Employees);
}
