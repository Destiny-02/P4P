package payroll;
public class HourlyEmployee extends Employee {
    private float _rate;
    private float _hours;
    public HourlyEmployee(String[] splitLine) {
        _rate = Float.parseFloat(splitLine[4]);
        _hours = Round((Float.parseFloat(splitLine[8]) * 4),"###.") / 4;
        super.InitialiseFields(splitLine, CalculateGross(), CalculateAnnualGross());
    }
    public float CalculateGross() {
        if (_hours <= 40) {
            return Round((_hours * _rate), "###.00");
        } else if (_hours <= 43) {
            return Round(((40 + ((float) 1.5 * (_hours - 40))) * _rate), "###.00");
        } else {
            return Round(((40 + (float) 4.5 + (2 * (_hours - 43))) * _rate), "###.00");
        }
    }
    public float CalculateAnnualGross() {
        return CalculateGross() * 52;
    }
}

