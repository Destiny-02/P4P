package payroll;
import java.util.Comparator;
public class Employee {
  private int _tid;
  private String[] _name;
  private String _employment;
  private String[] _period;
  private Hours _hours;
  private Money _money;
  public Employee(int tid, String[] name, String employment, double rate, double ytd, String[] period, double hours, double deductions) {
    _tid = tid;
    _name = name;
    _employment = employment;
    _period = period;
    _hours = new Hours(hours);
    if (employment.equals("Salaried")) {
      _money = new SalaryMoney(rate, ytd, deductions, rate/52);
    } else if (employment.equals("Hourly")){
      _money = new HourlyMoney(rate, ytd, deductions, _hours);
    }
  }
  public String tid() {
    return Integer.toString(_tid);
  }
  public String name() {
    return _name[1] + " " + _name[0];
  }
  public String rate() {
    return _money.displayRate();
  }
  public String nameByFamily() {
    return _name[0] + ", " + _name[1];
  }
  public String employeeType() {
    return _employment;
  }
  public String period() {
    return _period[0] + " to " + _period[1];
  }
  public String gross() {
    return _money.displayGross();
  }
  public String paye() {
    return _money.displayPAYE();
  }
  public String deductions() {
    return _money.displayDeductions();
  }
  public String nett() {
    return _money.displayNett();
  }
  public String ytd() {
    return _money.displayNewYTD();
  }
  public double burdenContribution() {
    return _money.getGross();
  }
  public double getPAYE() {
    return _money.getPAYE();
  }
  static Comparator<Employee> sortByTid = new Comparator<Employee>() {
    public int compare(Employee one, Employee two) {
        return Integer.compare(one._tid, two._tid);
  }
};
  static Comparator<Employee> sortByName = new Comparator<Employee>() {
    public int compare (Employee one, Employee two) {
      if (one._name[0].compareToIgnoreCase(two._name[0]) == 0) {
        return one._name[1].compareToIgnoreCase(two._name[1]);
      } else {
        return one._name[0].compareToIgnoreCase(two._name[0]);
      }
  }
};
}
