package payroll;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import payroll.processing.Process;
import payroll.processing.ProcessException;
import payroll.processing.ProcessType;
import payroll.processing.PayslipsProcess;
import payroll.processing.EmployeesProcess;
import payroll.processing.BurdenProcess;
import payroll.processing.PAYEProcess;
public class Payroll {
    private Map<Integer, Employee> _employeeRecords = new HashMap<Integer, Employee>();
    private ProcessType _processType;
    public Payroll(String proposedProcess) throws ProcessException {
        try {
            _processType = ProcessType.valueOf(proposedProcess);
        }
        catch (IllegalArgumentException iaEx) {
            throw new ProcessException("Process type is invalid (" + proposedProcess + ").");
        }
    }
    public static void main(String[] args) throws IOException, FileNotFoundException, InvalidEmployeeException,
        ProcessException {
        if (args.length != 2) {
            System.out.println("Invalid number of arguments.");
            return;
        }
        Payroll payroll = new Payroll(args[1]);
        BufferedReader recordFile = new BufferedReader(new FileReader(args[0]));
        if (recordFile == null) {
            System.out.println("Could not open record file (" + args[0] + ").");
            return;
        }
        String line = "";
        while ((line=recordFile.readLine()) != null) {
            if (!line.startsWith("#") && !line.isEmpty()) {
                payroll.addEmployee(line);
            }
        }
        recordFile.close();
        if (!payroll.allSamePeriod()) {
            throw new ProcessException("Pay period not the same for every employee.");
        }
        payroll.processPayroll();
    }
    public void addEmployee(String rawInput) throws InvalidEmployeeException {
        Employee employee = new Employee(rawInput), tmp = _employeeRecords.get(employee.getTaxID());
        if (tmp == null) {
            _employeeRecords.put(employee.getTaxID(), employee);
        }
        else {
            throw new InvalidEmployeeException("Employee with TaxID=" + employee.getTaxID() + " already exists.");
        }
    }
    public void processPayroll() throws ProcessException {
        ArrayList<Employee.Payslip> payslips = new ArrayList<Employee.Payslip>();
        for (Employee employee : _employeeRecords.values()) {
            Employee.Payslip payslip = employee.new Payslip();
            payslip.calculatePayslip();
            payslips.add(payslip);
        }
        Process process;
        switch (_processType) {
            case Payslips:
                process = new PayslipsProcess();
                break;
            case Employees:
                process = new EmployeesProcess();
                break;
            case Burden:
                process = new BurdenProcess();
                break;
            case PAYE:
                process = new PAYEProcess();
                break;
            default:
                throw new ProcessException("Unknown process type (" + _processType + ").");
        }
        process.doWork(payslips);
    }
    private boolean allSamePeriod() throws ProcessException {
        if (_employeeRecords.isEmpty()) {
            throw new ProcessException("No Employees recorded.");
        }
        Employee firstEmployee = _employeeRecords.values().iterator().next();
        for (Employee employee : _employeeRecords.values()) {
            if (!firstEmployee.samePeriod(employee)) {
                return false;
            }
        }
        return true;
    }
}
