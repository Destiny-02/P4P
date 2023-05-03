package payroll;
public class SalariedEmployee extends Employee{
    private float _rate;
    public SalariedEmployee(String[] splitLine) {
        _rate = Float.parseFloat(splitLine[4]);
        super.InitialiseFields(splitLine, CalculateGross(), CalculateAnnualGross());
    }
    public float CalculateGross() {
        return _rate / 52;
    }
    public float CalculateAnnualGross() {
        return _rate;
    }
}
