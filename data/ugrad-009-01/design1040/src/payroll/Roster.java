package payroll;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class Roster extends Accountant {
    public Roster() {
        empList = new ArrayList<>();
    }
    ArrayList<Employee> empList;
    public void addEmployee(String[] allInfo) {
        if (allInfo[2].equals("Salaried")) {
            empList.add(new SalariedEmployee(allInfo[1]));
        } else if (allInfo[2].equals("Hourly")) {
            empList.add(new HourlyEmployee(allInfo[1]));
        } else {
            throw new PayrollException("Employee" + allInfo[1] + " is neither salaried nor hourly");
        }
        empList.get(empList.size() - 1).setStats(allInfo[0], allInfo[3], allInfo[4], allInfo[5], allInfo[6], allInfo[7], allInfo[8]);
    }
    public String getBurden() {
        double gross = 0;
        for (Employee currentEmp : empList) {
            currentEmp.updateEmp();
            gross += currentEmp.gross;
        }
        return makeMoney(gross);
    }
    public String getTotalPAYE() {
        double tax = 0;
        for (Employee currentEmp : empList) {
            currentEmp.updateEmp();
            tax += currentEmp.tax;
        }
        return makeMoney(tax);
    }
    public ArrayList<Employee> getNameSorted() {
        ArrayList<Employee> newEmpList = new ArrayList<>(empList);
        Comparator<Employee> nameComparator = new Comparator<Employee>() {
            @Override
            public int compare(final Employee o1, final Employee o2) {
                return o1.name.compareTo(o2.name);
            }
        };
        Collections.sort(newEmpList, nameComparator);
        return newEmpList;
    }
    public ArrayList<Employee> getIDSorted() {
        ArrayList<Employee> newEmpList = new ArrayList<>(empList);
        Comparator<Employee> idComparator = new Comparator<Employee>() {
            @Override
            public int compare(final Employee e1, final Employee e2) {
                return  e1.taxID > e2.taxID ? +1 : e1.taxID < e2.taxID ? -1 : 0;
            }
        };
        Collections.sort(newEmpList, idComparator);
        return newEmpList;
    }
}