package payroll;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.ArrayList;
public class EmployeeRecord {
    private ArrayList<Employee> _employeeRecord = new ArrayList<>();
    public void AddEmployee(Employee employee) {
        _employeeRecord.add(employee);
    }
    public void AlphabeticalNameSort() {
        Collections.sort(_employeeRecord, new AlphabeticalEmployeeComparator());
    }
    public void TIDSort() {
        Collections.sort(_employeeRecord, new TIDEmployeeComparator());
    }
    public String PayslipProcessingString() {
        String payslipString = "";
        TIDSort();
        for (int i = 0; i < _employeeRecord.size(); i++) {
            payslipString += _employeeRecord.get(i).PayslipProcessingLineString();
            if (i!=_employeeRecord.size()-1) {
                payslipString += "\n";
            }
        }
        return payslipString;
    }
    public String EmployeeProcessingString() {
        String payslipString = "";
        AlphabeticalNameSort();
        for (int i = 0; i < _employeeRecord.size(); i++) {
            payslipString += _employeeRecord.get(i).EmployeeProcessingLineString();
            if (i!=_employeeRecord.size()-1) {
                payslipString += "\n";
            }
        }
        return payslipString;
    }
    public String BurdenProcessingString() {
        float grossTotal = 0;
        for (Employee employee:_employeeRecord) {
            grossTotal += employee.getGross();
        }
        return _employeeRecord.get(0).GetPeriodToPeriod() + "\nTotal Burden: $" + FormatFloat(grossTotal);
    }
    public String PAYEProcessingString() {
        float payeTotal = 0;
        for (Employee employee:_employeeRecord) {
            payeTotal += employee.getPAYE();
        }
        return _employeeRecord.get(0).GetPeriodToPeriod() + "\nTotal PAYE: $" + FormatFloat(payeTotal);
    }
    public String FormatFloat(float number) {
        DecimalFormat formatter = new DecimalFormat("###.00");
        return formatter.format(number);
    }
}
