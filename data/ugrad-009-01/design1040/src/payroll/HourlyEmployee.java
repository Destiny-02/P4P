package payroll;
public class HourlyEmployee extends Employee {
    public HourlyEmployee (String name) {
        super(name);
    }
    public double doMyTaxes(double gross, double rate) {
        double annual = 52 * gross;
        return super.doMyTaxes(annual);
    }
    public double calcGross(double hours, double rate) {
        if (hours <= 40) {
            return roundIt(rate * hours);
        } else if (hours <= 43) {
            return roundIt((rate * 40) + (hours - 40) * rate * 1.5);
        } else {
            return roundIt((rate * 40) + (3) * rate * 1.5 + (hours - 43) * rate * 2);
        }
    }
    public String getSalaried() {
        return "Hourly";
    }
}
