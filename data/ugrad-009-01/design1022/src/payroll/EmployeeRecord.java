package payroll;
import java.text.DecimalFormat;
import java.util.Comparator;
public class EmployeeRecord {
    private String _employeeType;
    private int _ID;
    private String _name;
    private double _payRate;
    private double _YTD;
    private String _startPeriod;
    private String _endPeriod;
    private double _hoursWorked;
    private double _PAYE;
    private double _deductions;
    private double _nett;
    private String _formattedEmployeeRecord;
    private double _grossEarnings;
    EmployeeRecord(String employeeInfo) throws NameInputException, DateFormatException,
            MissingDataException, NonsenseValueException {
        String[] splitData = split(employeeInfo);
        _ID = Integer.parseInt(splitData[0]);
        _name = splitData[1];
        _employeeType = splitData[2];
        _payRate = Double.parseDouble(splitData[3]);
        _YTD = Double.parseDouble(splitData[4]);
        _startPeriod = splitData[5];
        _endPeriod = splitData[6];
        _hoursWorked = Double.parseDouble(splitData[7]);
        _deductions = Double.parseDouble(splitData[8]);
        calcGrossEarnings();
        calcPAYE();
        calcNett();
        calcYTD();
        checkInputs();
    }
    private String[] split(String employeeInfo) throws MissingDataException {
        String[] splitData = employeeInfo.split("\t");
        if (splitData.length != 9){
            throw new MissingDataException("**Error** There is date missing from this record");
        }
        for (int i = 0; i < splitData.length; i++) {
            splitData[i] = splitData[i].replace("$", "");
        }
        return splitData;
    }
    private void checkInputs() throws NameInputException, DateFormatException, NonsenseValueException {
        if (!(_name.matches("[a-zA-Z]+, [a-zA-Z]+"))){
            throw new NameInputException("Error name input");
        }
        String[][] checkDates = {_startPeriod.split("-"), _endPeriod.split("-")};
        for(String[] dateToCheck : checkDates) {
            if (dateToCheck.length != 3) {
                throw new DateFormatException("**Error** Date format");
            } else if ((((dateToCheck[0].length()) != 4) || (dateToCheck[1].length() != 2)) || (dateToCheck[2].length() != 2)) {
                throw new DateFormatException("Error date format");
            }
        }
        if ((_startPeriod.compareTo(_endPeriod)) > 0){
            throw new NonsenseValueException("**Error** End date before start date");
        }
        else if(((_hoursWorked < 0) || (_payRate <0)) || (_deductions <0) || (_nett < 0)){
            throw new NonsenseValueException("**Error** Negative number doesn't make sense");
        }
    }
    public String formatForPayslips() {
        String[] splitName = _name.split(", ");
        _formattedEmployeeRecord = String.format("%d. %s %s, Period: %s to %s. Gross: $%.2f, " +
                "PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f", _ID, splitName[1], splitName[0],
                _startPeriod, _endPeriod, _grossEarnings, _PAYE, _deductions, _nett, _YTD);
        return _formattedEmployeeRecord;
    }
    public String formatForEmployees() {
        _formattedEmployeeRecord = String.format("%s (%d) %s, $%.2f YTD:$%.2f", _name, _ID, _employeeType, _payRate, _YTD);
        return _formattedEmployeeRecord;
    }
    private void calcGrossEarnings() {
        if (_employeeType.equals("Hourly")) {
            if (_hoursWorked <= 40) {
                _grossEarnings = round2DP((_hoursWorked * _payRate));
            } else if (_hoursWorked <= 43) {
                _grossEarnings = round2DP(40 * _payRate + ((_hoursWorked - 40) * _payRate * 1.5));
            } else if (_hoursWorked > 43) {
                _grossEarnings = round2DP(40 * _payRate) + round2DP((3 * _payRate * 1.5)) +
                        round2DP(((_hoursWorked - 43) * _payRate * 2));
            }
        } else {
            _grossEarnings = round2DP(_payRate / 52);
        }
    }
    private void calcPAYE() {
        double annualGross = _grossEarnings * 52;
        if (annualGross <= 14000) {
            _PAYE = round2DP((annualGross * 0.105) / 52);
        } else if (annualGross <= 48000) {
            _PAYE = round2DP ((14000 * 0.105 + (annualGross - 14000) * 0.175) / 52);
        } else if (annualGross <= 70000) {
            _PAYE = round2DP ((14000 * 0.105 + +34000 * 0.175 + (annualGross - 48000) * 0.30) / 52);
        } else {
            _PAYE = round2DP((14000 * 0.105 + +34000 * 0.175 + 22000 * 0.3 + (annualGross - 70000) * 0.33) / 52);
        }
    }
    private void calcNett(){
        _nett = _grossEarnings - _PAYE - _deductions;
    }
    private void calcYTD(){
        _YTD += _grossEarnings;
    }
   private double round2DP(double num) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(num));
    }
    public static Comparator<EmployeeRecord> IDComparator = new Comparator<EmployeeRecord>() {
        @Override
        public int compare(EmployeeRecord e1, EmployeeRecord e2) {
            return (e1._ID - e2._ID);
        }
    };
    public static Comparator<EmployeeRecord> NameComparator = new Comparator<EmployeeRecord>() {
        @Override
        public int compare(EmployeeRecord e1, EmployeeRecord e2) {
            return (e1._name.compareTo(e2._name));
        }
    };
    public String getStartPeriod() {
        return _startPeriod;
    }
    public String getEndPeriod() {
        return _endPeriod;
    }
    public double returnGrossEarnings(){
        return _grossEarnings;
    }
    public double returnPAYE(){
        return _PAYE;
    }
}