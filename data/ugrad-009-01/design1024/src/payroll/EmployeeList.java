
package payroll;
import java.util.ArrayList;
import java.util.Collections;
public class EmployeeList{
    private ArrayList<Employee> _empList;
    public EmployeeList(){
        _empList = new ArrayList<Employee>();
    }
    public void add(Employee emp){
        _empList.add(emp);
    }
    public int length(){
        return _empList.size();
    }
    public EmployeeList sortTID(){
        EmployeeList empTIDList = createCopy(_empList);
        Collections.sort(empTIDList._empList, new Employee.TIDComparator());
        return empTIDList;
    }
    public EmployeeList sortEmployees(){
        EmployeeList empEmployeesList = createCopy(_empList);
        Collections.sort(empEmployeesList._empList, new Employee.EmployeesComparator());
        return empEmployeesList;
    }
    private EmployeeList createCopy(ArrayList<Employee> empList){
        EmployeeList copyList = new EmployeeList();
        for (Employee emp:_empList){
            copyList.add(emp);
        }
        return copyList;
    }
    public void PAYEOutput(){
        double totalPAYE = 0;
        for (Employee emp:_empList){
            totalPAYE += emp.calculatePAYE();
        }
        String outputString = _empList.get(0).getDatePeriod();
        System.out.println(String.format(outputString + "\nTotal PAYE: $%.2f", totalPAYE));
    }
    public void burdenOutput(){
        double totalGross = 0;
        for (Employee emp:_empList){
            totalGross += emp.calculateGross();
        }
        String outputString = _empList.get(0).getDatePeriod();
        System.out.println(String.format(outputString + "\nTotal Burden: $%.2f", totalGross));
    }
    public void employeesOutput(){
        String outputString = "";
        for (Employee emp:_empList){
            String empInfo = emp.getEmployeesString();
            outputString += empInfo;
        }
        System.out.print(outputString);
    }
    public void TIDOutput(){
        String outputString = "";
        for (Employee emp:_empList){
            String empPayslip = emp.getPayslipString();
            outputString += empPayslip;
        }
        System.out.print(outputString);
    }
}
