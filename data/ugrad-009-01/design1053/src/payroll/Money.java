package payroll;
import java.text.DecimalFormat;
public abstract class Money {
  private double _rate;
  private double _ytd;
  private double _deductions;
  private double _gross;
  private double _paye;
  private DecimalFormat _df = new DecimalFormat("0.00");
  private static final double FIRST_TIER = 0.105 * 14000;
  private static final double SECOND_TIER = 0.175 * 34000 + FIRST_TIER;
  private static final double THIRD_TIER = 0.300 * 22000 + SECOND_TIER;
  Money(double ytd, double deductions) {
    _ytd = ytd;
    _deductions = deductions;
  }
  Money(double rate, double ytd, double deductions) {
    this(ytd, deductions);
    _rate = rate;
  }
  Money (double rate, double ytd, double deductions, double gross) {
    this(rate, ytd, deductions);
    _gross = gross;
  }
  public double getPAYE() {
    return getPAYE(_rate);
  }
  public double getPAYE(double yearGross) {
    double paye;
    if (yearGross <= 14000){
      paye = 0.105 * yearGross;
    } else if (yearGross <= 48000) {
      paye = 0.175 * (yearGross - 14000) + FIRST_TIER;
    } else if (yearGross <= 70000) {
      paye = 0.300 * (yearGross - 48000) + SECOND_TIER;
    } else {
      paye = 0.330 * (yearGross - 70000) + THIRD_TIER;
    }
    _paye = paye / 52;
    return _paye;
  }
  public String displayPAYE() {
    return format(getPAYE());
  }
  public String displayNett() {
    return format(_gross - _paye - _deductions);
  }
  public String displayNett(double gross) {
    return format(gross - _paye - _deductions);
  }
  public String displayDeductions() {
    return format(_deductions);
  }
  public String displayNewYTD() {
    return format(_ytd + _gross);
  }
  public String displayRate() {
    return format(_rate);
  }
  public String displayGross() {
    return format(_gross);
  }
  public double getYTD() {
    return _ytd;
  }
  public double getGross() {
    return _gross;
  }
  public String format(double money) {
    return "$" + _df.format(money);
  }
}
