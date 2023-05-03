
package payroll;
public class Process {
    String _processType;
    public Process(String processType){
        _processType = processType;
    }
    public void processOutput(EmployeeList empList){
        if (_processType.equals("Payslips")){
            EmployeeList TIDempList = empList.sortTID();
            TIDempList.TIDOutput();
        } else if (_processType.equals("Employees")){
            EmployeeList employeesList = empList.sortEmployees();
            employeesList.employeesOutput();
        } else if (_processType.equals("Burden")){
            empList.burdenOutput();
        } else if (_processType.equals("PAYE")){
            empList.PAYEOutput();
        } else {
            throw new RuntimeException("Unrecognized Process Input: " + _processType);
        }
    }
}
