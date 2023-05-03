package payroll;
public class SalariedEmployee extends Employee {
    public SalariedEmployee (String name) {
        super(name);
    }
    public double doMyTaxes(double gross, double rate) {
        double annual;
            annual = rate;
        return super.doMyTaxes(annual);
    }
    public double calcGross(double hours, double rate) {
            return roundIt(rate / 52);
        }
    public String getSalaried() {
        return "Salaried";
    }
    }
