package payroll;
class Person {
    private String[] _name;
    private int _taxId;
    private String _jobType, _start, _end;
    private float _rate, _yearToDate, _deduction, _gross, _PAYE, _netPay;
    Person(String[] payArray, int lineCount) throws InvalidInputException {
        InvalidInputException error = new InvalidInputException();
        try {
            _taxId = Integer.parseInt(payArray[0]);
        } catch (Exception e) {
            error.printErrorMessage(lineCount,"Invalid Tax ID");
            throw new InvalidInputException();
        }
        _name = payArray[1].split(", ");
        if (_name.length==1) {
            error.printErrorMessage(lineCount,"Name written in wrong format");
            throw new InvalidInputException();
        }
        if (payArray[2].equals("Salaried")||payArray[2].equals("Hourly")) {
            _jobType = payArray[2];
        } else {
            error.printErrorMessage(lineCount, "Invalid Employment type");
            throw new InvalidInputException();
        }
        _start = payArray[5];
        _end = payArray[6];
        String[] s = _start.split("\\-");
        String[] e = _end.split("\\-");
        if ((s.length!=3)||(e.length!=3)) {
            error.printErrorMessage(lineCount, "Invalid start or end date");
            throw new InvalidInputException();
        } else if ((s[0].length()!=4)||(e[0].length()!=4)||(s[1].length()!=2)||(e[1].length()!=2)||(s[2].length()!=2)||(e[2].length()!=2)) {
            error.printErrorMessage(lineCount, "Invalid start or end date");
            throw new InvalidInputException();
        }
        String r = error.dollarSign(payArray[3],lineCount);
        String ytd = error.dollarSign(payArray[4],lineCount);
        String d = error.dollarSign(payArray[8],lineCount);
        _rate = error.testFloat(r,lineCount,"Invalid Rate");
        _yearToDate = error.testFloat(ytd,lineCount,"Invalid YTD");
        float hours = error.testFloat(payArray[7],lineCount,"Invalid Hours");
        _deduction = error.testFloat(d,lineCount,"Invalid Deduction");
        Income inc = new Income();
        _gross = inc.calculateGross(_jobType,_rate,hours);
        _PAYE = inc.calculatePAYE(_gross);
        _netPay = inc.calculateNetPay(_gross, _PAYE, _deduction);
        if (_netPay<0) {
            error.printErrorMessage(lineCount, "Net pay is negative");
            throw new InvalidInputException();
        }
        _yearToDate = inc.newYTD(_gross, _yearToDate);
    }
    String[] getName() {
        return _name;
    }
    int getTaxId() {
        return _taxId;
    }
    float addGross(float sum) {
        return sum + _gross;
    }
    float addPAYE(float sum) {
        return sum + _PAYE;
    }
    void payslipDetails() {
        System.out.print(_taxId + ". " + _name[1] + " " + _name[0] + ", Period: ");
        printPeriod();
        System.out.print(String.format(". Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f", _gross, _PAYE, _deduction));
        System.out.print(String.format(" Nett: $%.2f YTD: $%.2f\n", _netPay, _yearToDate));
    }
    void employeeDetails() {
        System.out.print(_name[0] + ", " + _name[1] + " (" + _taxId + ") " + _jobType + ", ");
        System.out.print(String.format("$%.2f YTD:$%.2f\n", _rate, _yearToDate));
    }
    void printPeriod() {
        System.out.print(_start + " to " + _end);
    }
}
