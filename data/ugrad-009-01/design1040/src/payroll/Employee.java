package payroll;
public class Employee extends Accountant {
    public Employee(String name) {
        String firstName, lastName;
        this.name = name;
        try {
            firstName = (name.split(", ")[1]);
            lastName = (name.split(", ")[0]);
        } catch (Exception exception) {
            throw  new PayrollException("Error in Employee Name: " + name);
        }
            this.fullName = firstName + " " + lastName;
    }
    String fullName, name, startDay, endDay;
    int taxID;
    double  yearToDate;
    double hours, deductions, rate;
    double tax, gross, net, newYTD;
    public void setStats(String taxID, String rate, String yearToDate, String startDay, String endDay, String hours, String deductions) {
        this.rate = Double.parseDouble(rate.replace("$", ""));
        this.taxID = Integer.parseInt(taxID);
        this.yearToDate = Double.parseDouble(yearToDate.replace("$", ""));
        this.startDay = startDay;
        this.endDay = endDay;
        this.hours = Double.parseDouble(hours);
        this.deductions = Double.parseDouble(deductions.replace("$", ""));
    }
    public void updateEmp(){
        this.gross = calcGross(hours, rate);
        this.tax = doMyTaxes(gross, rate);
        this.net = periodNet(deductions, tax, gross);
        this.newYTD = yearToDate(yearToDate, gross);
        checkValid();
    }
    private void checkValid() {
        if (rate < 0 || hours < 0 || net < 0) {
            throw new PayrollException("Invalid employee statistics: Negative Number calculated");
        }
    }
    public String stringGross() {
        return makeMoney(gross);
    }
    public String stringTax() {
        return makeMoney(tax);
    }
    public String stringNet() {
        return makeMoney(net);
    }
    public String stringNewYTD() {
        return makeMoney(newYTD);
    }
    public String stringRate() {
        return makeMoney(rate);
    }
    public String stringDeducts() {
        return makeMoney(deductions);
    }
    @Override
    public String toString() {
        return getName();
    }
    public String getName() {
        return name;
    }
    public String getSalaried() {
        return "Unknown";
    }
    public double periodNet(double deduct, double tax, double gross) {
        return (gross - deduct - tax);
    }
    public double yearToDate(double ytd, double gross) {
        return roundIt(ytd + gross);
    }
    public double calcGross(double hours, double rate) {
        throw new PayrollException("Employee neither salaried nor hourly");
    }
    public double doMyTaxes(double annual) {
        if (annual > 14000) {
            tax = 14000 * 0.105;
            annual = annual -14000;
            if (annual > 34000) {
                tax = tax + 34000 * 0.175;
                annual = annual -34000;
                if ( annual > 22000) {
                    tax = tax + 22000 * 0.3;
                    annual = annual -22000;
                    tax = tax + annual * 0.33;
                } else {
                    tax = tax + annual * 0.3;
                }
            } else {
                tax = tax + annual * 0.175;
            }
        } else {
            tax = annual * 0.105;
        }
        return roundIt(tax/52);
    }
    public double doMyTaxes(double gross, double rate) {
        throw new PayrollException("Employee neither salaried nor hourly");
    }
}
