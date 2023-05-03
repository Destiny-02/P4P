package payroll;
import java.text.DecimalFormat;
public abstract class Employee {
    private int _tid;
    private String _fullNameNormal;
    private String _fullNameFormal;
    private String _employment;
    private float _rate;
    private float _ytd;
    private String _start;
    private String _end;
    private float _hours;
    private float _deduction;
    private float _gross;
    private float _annualGross;
    private float _paye;
    private float _nett;
    private float _ytdAfter;
    public void InitialiseFields(String[] splitLine, float gross, float annualGross) {
        _tid = Integer.parseInt(splitLine[0]);
        _fullNameNormal = splitLine[2] + " " + splitLine[1];
        _fullNameFormal = splitLine[1] + ", "+ splitLine[2];
        _employment = splitLine[3];
        _rate = Float.parseFloat(splitLine[4]);
        _ytd = Float.parseFloat(splitLine[5]);
        _start = splitLine[6];
        _end = splitLine[7];
        _hours = Round((Float.parseFloat(splitLine[8]) * 4),"###.") / 4;
        _deduction = Float.parseFloat(splitLine[9]);
        _gross = gross;
        _annualGross = annualGross;
        _paye = CalculatePAYE();
        _nett = _gross - (_deduction + _paye);
        _ytdAfter = _ytd + gross;
        ScanFieldsForNegatives();
    }
    public abstract float CalculateGross();
    public abstract float CalculateAnnualGross();
    private void ScanFieldsForNegatives() {
        if (_tid < 0 || _rate < 0 || _ytd < 0 || _hours < 0 || _deduction < 0) {
            throw new PayrollRuntimeException("One of " + _fullNameNormal + "'s fields has a negative value.");
        }
    }
    private float CalculatePAYE() {
        if (_annualGross <= 14000) {
            return Round(((float) (0.105 * _annualGross) / 52),"###.00");
        } else if (_annualGross <= 48000) {
            return Round(((float) (0.175 * (_annualGross - 14000) + (14000 * 0.105)) / 52), "###.00");
        } else if (_annualGross <= 70000) {
            return Round(((float) (0.30 * (_annualGross - 48000) + (34000 * 0.175) + (14000 * 0.105)) / 52), "###.00");
        } else {
            return Round(((float) (0.33 * (_annualGross - 70000) + (22000 * 0.3) + (34000 * 0.175) + (14000 * 0.105))
                    / 52), "###.00");
        }
    }
    public float Round(float number, String format) {
        return Float.parseFloat(FormatFloat(number, format));
    }
    public String FormatFloat(float number, String format) {
        DecimalFormat formatter = new DecimalFormat(format);
        return formatter.format(number);
    }
    public String PayslipProcessingLineString() {
        return _tid + ". " + _fullNameNormal + ", Period: " + GetPeriodToPeriod() + ". Gross: $"
                + FormatFloat(_gross, "###.00") + ", PAYE: $" + FormatFloat(_paye, "###.00") + ", Deductions: $"
                + FormatFloat(_deduction, "###.00") + " Nett: $" + FormatFloat(_nett, "###.00") + " YTD: $"
                + FormatFloat(_ytdAfter, "###.00");
    }
    public String EmployeeProcessingLineString() {
        return _fullNameFormal + " (" + _tid + ") " + _employment + ", $" + FormatFloat(_rate, "###.00") + " YTD:$"
                + FormatFloat(_ytdAfter, "###.00");
    }
    public String GetPeriodToPeriod() {
        return _start + " to " + _end;
    }
    public String getFullNameFormal() {
        return _fullNameFormal;
    }
    public int getTid() {
        return _tid;
    }
    public float getGross() { return _gross; }
    public float getPAYE() { return _paye; }
}
