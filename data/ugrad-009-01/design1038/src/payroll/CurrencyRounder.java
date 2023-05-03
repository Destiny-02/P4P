package payroll;
public class CurrencyRounder {
    public double round(double n) {
        try {
            return Double.parseDouble(String.format("%.2f", n));
        }
        catch (NumberFormatException nfEx) {
            return -1.0;
        }
    }
    public String display(double n) {
        return "$" + String.format(String.format("%.2f", n));
    }
}
