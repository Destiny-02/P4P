package payroll;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TreeSet;
public class PayrollOutput {
  private TreeSet<Employee> _records;
  private String _output;
  private static final String[] VALID_OPTIONS = new String[]{"Payslips", "Employees", "Burden", "PAYE"};
  private DecimalFormat _df = new DecimalFormat("0.00");
  public PayrollOutput(String output) {
    if (!Arrays.asList(VALID_OPTIONS).contains(output)) {
      throw new RuntimeException("Invalid output option");
    }
    _output = output;
    _records = new TreeSet<Employee>(Employee.sortByTid);
  }
  public void addEmployee(Employee employee) throws InvalidEmployeeRecordFormatException {
    if (!_records.add(employee)) {
      throw new InvalidEmployeeRecordFormatException("There are employees with conflicting TIDs.");
    }
  }
  public void printOutput() {
    printDate();
    if (checkOutput("Payslips")) {
      for (Employee employee : _records) {
        payslips(employee);
      }
    } else if (checkOutput("Employees")) {
      TreeSet<Employee> _sorted = new TreeSet<Employee>(Employee.sortByName);
      _sorted.addAll(_records);
      for (Employee employee : _sorted) {
        employees(employee);
      }
    } else if (checkOutput("Burden")) {
      double sum = 0;
      String period = "";
      for (Employee employee : _records) {
        sum += employee.burdenContribution();
        period = employee.period();
      }
      System.out.println(period);
      System.out.println("Total Burden: " + "$" + _df.format(sum));
    } else if (checkOutput("PAYE")) {
      double sum = 0;
      String period = "";
      for (Employee employee : _records) {
        sum += employee.getPAYE();
        period = employee.period();
      }
      System.out.println(period);
      System.out.println("Total PAYE: " + "$" + _df.format(sum));
    }
  }
  private boolean checkOutput(String type) {
    return _output.equals(type);
  }
  private void payslips(Employee employee) {
    if (Double.parseDouble(employee.nett().substring(1)) < 0) {
      System.out.println("***Deductions might be invalid for " + employee.name() + "***");
    }
    System.out.println(
        employee.tid() + ". "
        + employee.name() + ", "
        + "Period: " + employee.period() + ". "
        + "Gross: " + employee.gross() + ", "
        + "PAYE: " + employee.paye() + ", "
        + "Deductions: " + employee.deductions() + " "
        + "Nett: " + employee.nett() + " "
        + "YTD: " + employee.ytd()
    );
  }
  private void employees(Employee employee) {
    System.out.println(
        employee.nameByFamily() + " "
        + "(" + employee.tid() +")" + " "
        + employee.employeeType() + ", "
        + employee.rate() +  " "
        + "YTD:" + employee.ytd()
    );
  }
  private void printDate() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date currentDate = new Date();
    System.out.println(dateFormat.format(currentDate));
  }
}
