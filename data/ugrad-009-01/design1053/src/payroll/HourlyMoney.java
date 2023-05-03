package payroll;
public class HourlyMoney extends Money {
  private double _hourlyRate;
  private double[] _workedHours;
  private double _gross = 0;
  private static final double[] HOUR_WEIGHTINGS = new double[]{1, 1.5, 2};
  HourlyMoney(double rate, double ytd, double deductions, Hours hours) {
    super(ytd, deductions);
    _hourlyRate = rate;
    _workedHours = hours.workedHours();
    calculateGross();
  }
  private void calculateGross() {
    for (int i = 0; i < _workedHours.length; i++) {
      _gross += _workedHours[i] * _hourlyRate * HOUR_WEIGHTINGS[i];
    }
  }
  @Override
  public String displayNewYTD() {
    return format(getYTD() + _gross);
  }
  @Override
  public String displayRate() {
    return format(_hourlyRate);
  }
  @Override
  public String displayGross() {
    return format(_gross);
  }
  @Override
  public String displayNett() {
    return super.displayNett(_gross);
  }
  @Override
  public double getGross() {
    return _gross;
  }
  public double getPAYE() {
    return super.getPAYE(_gross * 52);
  }
}
