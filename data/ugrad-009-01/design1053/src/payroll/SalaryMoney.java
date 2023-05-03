package payroll;
public class SalaryMoney extends Money {
  SalaryMoney(double rate, double ytd, double deductions, double gross) {
    super (rate, ytd, deductions, gross);
  }
}
