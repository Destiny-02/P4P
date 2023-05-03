package payroll;
public class Payroll {
  public static void main(String[] args) {
    InputParser input = new InputParser(args[0]);
    PayrollOutput records = new PayrollOutput(args[1]);
    Employee employee;
    try {
      while ((employee = input.parseEmployee()) != null)
      records.addEmployee(employee);
    } catch (InvalidEmployeeRecordFormatException e) {
      System.out.println(e.getMessage());
    }
    records.printOutput();
  }
}
