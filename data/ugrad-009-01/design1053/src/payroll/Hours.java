package payroll;
public class Hours {
  private double normalHours;
  private double overTimeHours;
  private double doubleTimeHours;
  public Hours(double totalHours) {
    if (totalHours > 43) {
      doubleTimeHours = totalHours - 43;
      overTimeHours = 3;
      normalHours = 40;
    } else if (totalHours > 40 && totalHours < 43) {
      overTimeHours = totalHours - 40;
      normalHours = 40;
    } else {
      normalHours = totalHours;
    }
  }
  public double[] workedHours() {
    return new double[]{normalHours, overTimeHours, doubleTimeHours};
  }
}
