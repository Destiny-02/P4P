package payroll;
import payroll.processing.ProcessException;
public class Employee {
    private static final int NUMFIELDS = 9;
    private static final String EMPLOY_SALARIED = "Salaried";
    private static final String EMPLOY_HOURLY = "Hourly";
    private static final String DATE_FORMAT = "\\d{4}-\\d{2}-\\d{2}";
    private static final double[] WORK_RATE = new double[]{40, 3};
    private static final String NAME_STR_PATTERN = "([a-zA-z]+)( [a-zA-z]+)*";
    private static final String[] HOUR_STR_PATTERN = new String[]{"\\d+", "\\d+.00", "\\d+.50", "\\d+.0", "\\d+.25", "\\d+.5", "\\d+.75"};
    private static final String[] MONEY_STR_PATTERN = new String[]{"\\$\\d+", "\\$\\d+.\\d{2}"};
    private static final double[] TAX_BRACKET = new double[]{14000.0, 48000.0-14000.0, 70000.0-48000.0};
    private static final double[] TAX_RATE = new double[]{0.105, 0.175, 0.3, 0.33};
    private int _taxID;
    private String[] _name;
    private EmploymentType _employmentType;
    private double _rate;
    private double _ytd;
    private Date _startDate;
    private Date _endDate;
    private double _hours;
    private double[] _workHours = new double[3];
    private double _deduction;
    public Employee(String rawRecord) throws InvalidEmployeeException {
        String[] recordData =  rawRecord.split("\t");
        if (recordData.length != NUMFIELDS) {
            throw new InvalidEmployeeException("Record does not have appropriate number of fields. " +
                "(Record on line beginning " + rawRecord.substring(0, Math.min(20, rawRecord.length())) + ")");
        }
        for (String field : recordData) {
            if (field.isEmpty() || field == null) {
                throw new InvalidEmployeeException("Field(s) left blank in record on line beginning " +
                    rawRecord.substring(0, Math.min(20, rawRecord.length())) + ".");
            }
        }
        _taxID = verifyTID(recordData[0]);
        _name = verifyName(recordData[1]);
        _employmentType = verifyEmploymentType(recordData[2]);
        _rate = verifyMoneyValue(recordData[3]);
        _ytd = verifyMoneyValue(recordData[4]);
        if (!periodSevenDays((_startDate=verifyDate(recordData[5])),(_endDate = verifyDate(recordData[6])))) {
            throw new InvalidEmployeeException("Time period is not seven days (\"" + _startDate + " -> " + _endDate +
                "\", TaxID=" + _taxID + ").");
        }
        determineHours(_hours=verifyHours(recordData[7]));
        _deduction = verifyMoneyValue(recordData[8]);
    }
    public int getTaxID() {
        return _taxID;
    }
    public boolean samePeriod(Employee other) {
        return (_startDate.compareTo(other._startDate)) == 0 && (_endDate.compareTo(other._endDate)) == 0;
    }
    public class Payslip implements Comparable<Payslip> {
        private int _payslipTaxID;
        private String[] _payslipName;
        private double _gross;
        private double _paye;
        private double _net;
        private double _postYTD;
        public Payslip() {
            _payslipTaxID = _taxID;
            _payslipName = _name;
        }
        public int compareTo(Employee.Payslip other) {
            return compareTaxID(other);
        }
        public int compareTaxID(Employee.Payslip other) {
            return this._payslipTaxID - other._payslipTaxID;
        }
        public int compareName(Employee.Payslip other) {
            if (this._payslipName[0].equals(other._payslipName[0])) {
                if (this._payslipName[1].equals(other._payslipName[1])) {
                    return compareTaxID(other);
                }
                return this._payslipName[1].compareTo(other._payslipName[1]);
            }
            return this._payslipName[0].compareTo(other._payslipName[0]);
        }
        public void calculatePayslip() throws ProcessException {
            _gross = calculateGross();
            _paye = calculatePAYE();
            _net = calculateNetIncome();
            if (_net < 0.0) {
                throw new ProcessException("Employee with TaxID=" + _payslipTaxID + " has negative net payment.");
            }
            _postYTD = calculateNewYTD();
        }
        public String printPayslip() {
            return String.format("%d. %s %s, Period: %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f " +
                "Nett: $%.2f YTD: $%.2f", _taxID, _name[1], _name[0], printWorkPeriod(), _gross, _paye, _deduction,
                _net, _postYTD);
        }
        public String printEmployee() {
            return String.format("%s, %s (%d) %s, $%.2f YTD:$%.2f", _name[0], _name[1], _taxID, _employmentType,
                _rate, _postYTD);
        }
        public double retrieveGross() {
            return _gross;
        }
        public double retrievePAYE() {
            return _paye;
        }
        public String printWorkPeriod() {
            return _startDate + " to " + _endDate;
        }
        private double calculateGross() {
            CurrencyRounder rounder = new CurrencyRounder();
            if (_employmentType == EmploymentType.Salaried) {
                return rounder.round(_rate / 52);
            }
            double grossIncome = 0.0;
            for (int i = 0; i < 3; ++i) {
                grossIncome += rounder.round(_rate * _workHours[i] * (1.0 + 0.5*i));
            }
            return grossIncome;
        }
        private double calculatePAYE() {
            CurrencyRounder rounder = new CurrencyRounder();
            double grossAnnual = (_employmentType==EmploymentType.Salaried?_rate:rounder.round(_gross*52));
            double[] incomeBrackets = splitIncome(grossAnnual);
            double taxPAYE = 0.0;
            for (int i = 0; i < incomeBrackets.length; ++i) {
                taxPAYE += rounder.round(incomeBrackets[i] * TAX_RATE[i]);
            }
            return rounder.round(taxPAYE / 52);
        }
        private double calculateNetIncome() throws ProcessException{
            return _gross - _paye - _deduction;
        }
        private double calculateNewYTD() {
            return _ytd + _gross;
        }
        private double[] splitIncome(double income) {
            double[] incomeParts = new double[4];
            for (int i = 0; i < 3; ++i) {
                double incomePiece = Math.min(income, TAX_BRACKET[i]);
                incomeParts[i] = incomePiece;
                income -= incomePiece;
            }
            incomeParts[3] = income;
            return incomeParts;
        }
    }
    private int verifyTID(String s) throws InvalidEmployeeException {
        int taxID;
        if (!s.matches("\\d+") || (taxID=tryParseInt(s)) < 0) {
            throw new InvalidEmployeeException("Tax ID is not an integer number (" + s + ").");
        }
        return taxID;
    }
    private String[] verifyName(String s) throws InvalidEmployeeException{
        String[] name;
        if ((name=s.split(", ")).length != 2 || !name[0].matches(NAME_STR_PATTERN) ||
            !name[1].matches(NAME_STR_PATTERN)) {
            throw new InvalidEmployeeException("Name is not in the correct format (\"" + s + "\", TaxID=" + _taxID + ").");
        }
        return name;
    }
    private EmploymentType verifyEmploymentType(String s) throws InvalidEmployeeException {
        try {
            return EmploymentType.valueOf(s);
        }
        catch (IllegalArgumentException iaEx) {
            throw new InvalidEmployeeException("Employment type is invalid (\"" + s + "\", TaxID=" + _taxID + ").");
        }
    }
    private double verifyMoneyValue(String s) throws InvalidEmployeeException {
        double moneyAmount;
        if (!s.startsWith("$") || (moneyAmount=tryParseDouble(s.substring(1))) < 0.0) {
            throw new InvalidEmployeeException("Money value not in correct format (\"" + s + "\", TaxID=" + _taxID + ").");
        }
        if (!isValidPattern(s, MONEY_STR_PATTERN)) {
            throw new InvalidEmployeeException("Money value not in correct format (\"" + s + "\", TaxID=" + _taxID + ").");
        }
        return moneyAmount;
    }
    private Date verifyDate(String s) throws InvalidEmployeeException {
        if (!s.matches(DATE_FORMAT)) {
            throw new InvalidEmployeeException("Start date not in correct format (\"" + s + "\", TaxID=" + _taxID + ").");
        }
        return new Date(s);
    }
    private boolean periodSevenDays(Date startDate, Date endDate) {
        return startDate.compareTo(endDate) == -7;
    }
    private double verifyHours(String s) throws InvalidEmployeeException {
        double hours;
        if ((hours=tryParseDouble(s)) < 0.0) {
            throw new InvalidEmployeeException("Hours not in correct format (\"" + s + "\", TaxID=" + _taxID + ").");
        }
        if (!isValidPattern(s, HOUR_STR_PATTERN)) {
            throw new InvalidEmployeeException("Hours not in correct format (\"" + s + "\", TaxID=" + _taxID + ").");
        }
        return hours;
    }
    private void determineHours(double hours) {
        for(int i = 0; i < 2; ++i) {
            double h = Math.min(hours, WORK_RATE[i]);
            _workHours[i] = h;
            hours -= h;
        }
        _workHours[2] = hours;
    }
    private boolean isValidPattern(String s, String[] patterns) {
        for (int i = 0; i < patterns.length; ++i) {
            if (s.matches(patterns[i])) {
                return true;
            }
        }
        return false;
    }
    private int tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException nfEx) {
            return -1;
        }
    }
    private double tryParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        }
        catch (NumberFormatException nfEx) {
            return -1.0;
        }
    }
}
