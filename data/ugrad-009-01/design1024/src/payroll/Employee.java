
package payroll;
import java.util.Comparator;
public class Employee {
    private final int _TID;
    private final String _fullName;
    private final String _lastName;
    private final String _firstName;
    private final String _empType;
    private final double _rate;
    private final double _YTD;
    private final String _dateStart;
    private final String _dateEnd;
    private final double _hours;
    private final double _deductions;
    public Employee(String[] empFields) throws InputException{
        _fullName = empFields[1];
        String[] fullName = empFields[1].replace(",", "").split(" ");
        _TID = Integer.parseInt(empFields[0]);
        _lastName = fullName[0];
        _firstName = fullName[1];
        _empType = empFields[2];
        _rate = Double.parseDouble(empFields[3].replace("$", ""));
        _YTD = Double.parseDouble(empFields[4].replace("$", ""));
        _dateStart = empFields[5];
        _dateEnd = empFields[6];
        _hours = Double.parseDouble(empFields[7]);
        _deductions = Double.parseDouble(empFields[8].replace("$", ""));
        checkNonsense();
    }
    private void checkNonsense() throws InputException{
        if (!_dateStart.matches("\\d{4}-\\d{2}-\\d{2}")||!_dateEnd.matches("\\d{4}-\\d{2}-\\d{2}")){
            throw new InputException("Invalid Employee: " + _firstName +" "+_lastName +
                    "(" +_TID+"). Date format does not follow YYYY-MM-DD format.");
        }
        if (_fullName.split(",").length != 2){
            throw new InputException("Invalid Employee: " + _fullName + ". Wrong name format.");
        }
        if ((_TID < 0 )||(_rate < 0)|| (_YTD < 0) || (_hours < 0)||(_deductions < 0)){
            throw new InputException("Invalid Employee: " + _firstName +" "+_lastName +
                    "(" +_TID+"). One of the information provided is negative.");
        }
        if(_deductions > calculateGross()){
            throw new InputException("Invalid Employee: " + _firstName +" "+_lastName +
                    "(" +_TID+"). Deduction is greater than gross.");
        }
        String[] startDates= _dateStart.split("-");
        String[] endDates = _dateEnd.split("-");
        if(Integer.parseInt(startDates[0])>Integer.parseInt(endDates[0])){
            throw new InputException("Invalid Date: Start date after end date");
        } else if (Integer.parseInt(startDates[0])==Integer.parseInt(endDates[0])&&
                Integer.parseInt(startDates[1])>Integer.parseInt(endDates[1])){
            throw new InputException("Invalid Date: Start date after end date");
        } else if (Integer.parseInt(startDates[0])==Integer.parseInt(endDates[0])&&
                Integer.parseInt(startDates[1])==Integer.parseInt(endDates[1])&&
                Integer.parseInt(startDates[2])>=Integer.parseInt(endDates[2])){
            throw new InputException("Invalid Date: start date after end date");
        }
    }
    public static class TIDComparator implements Comparator<Employee> {
        public int compare(Employee emp1, Employee emp2) {
            if (emp1._TID > emp2._TID) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    public static class EmployeesComparator implements Comparator<Employee> {
        public int compare(Employee emp1, Employee emp2) {
            int lastAlphabetPriority = emp1._lastName.compareTo(emp2._lastName);
            if (lastAlphabetPriority < 0) {
                return -1;
            } else if (lastAlphabetPriority > 0) {
                return 1;
            } else {
                int firstAlphabetPriority = emp1._firstName.compareTo(emp2._firstName);
                if (firstAlphabetPriority < 0) {
                    return -1;
                } else if (firstAlphabetPriority > 0) {
                    return 1;
                } else
                if (emp1._TID > emp2._TID) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }
    public String getDatePeriod(){
        return _dateStart + " to " + _dateEnd;
    }
    public String getEmployeesString(){
        double gross = calculateGross();
        double newYTD = _YTD + gross;
        String stringForm = String.format("%s, %s (%d) %s, $%.2f YTD:$%.2f\n", _lastName,
                _firstName, _TID, _empType, _rate, newYTD);
        return stringForm;
    }
    public String getPayslipString() {
        double gross = calculateGross();
        double paye = calculatePAYE();
        double nett = gross - paye - _deductions;
        double newYTD = _YTD + gross;
        String stringForm = String.format("%d. %s %s, Period: %s to %s. "
                + "Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f "
                + "YTD: $%.2f\n", _TID, _firstName, _lastName,
                _dateStart, _dateEnd, gross, paye, _deductions,
                nett, newYTD);
        return stringForm;
    }
    public double calculateGross() {
        double gross;
        double rate = _rate;
        if (_empType.equals("Salaried")) {
            gross = roundRate(rate / 52);
        } else if (_empType.equals("Hourly")) {
            if (_hours <= 40) {
                gross = roundRate(rate * _hours);
            } else if (_hours <= 43) {
                gross = roundRate(rate * 40);
                gross += roundRate(roundRate((_hours - 40) * rate) * 1.5);
            } else {
                gross = roundRate(rate * 40);
                gross += roundRate(3 * 1.5 * rate) + roundRate((_hours - 43) * 2 * rate);
            }
        } else {
            throw new RuntimeException("Unrecognised Worker Type " + "(" + _lastName + "): " + _empType);
        }
        return gross;
    }
    public double calculatePAYE() {
        double paye;
        double empRate;
        if (_empType.equals("Salaried")) {
            empRate = _rate;
        } else {
            empRate = calculateGross() * 52;
        }
        if (empRate < 0) {
            throw new RuntimeException("Negative Rate " + "(" + _lastName + "): " + empRate);
        } else if (empRate <= 14000) {
            paye = roundRate(roundRate((empRate * 0.105)) / 52);
        } else if (empRate <= 48000) {
            paye = roundRate((roundRate((empRate - 14000) * 0.175) + roundRate(14000 * 0.105)) / 52);
        } else if (empRate <= 70000) {
            paye = roundRate((roundRate((empRate - 48000) * 0.3) + roundRate(34000 * 0.175) + roundRate(14000 * 0.105)) / 52);
        } else {
            paye = roundRate((roundRate((empRate - 70000) * 0.33) + roundRate(22000 * 0.3) + roundRate(34000 * 0.175) + roundRate(14000 * 0.105)) / 52);
        }
        return paye;
    }
    private double roundRate(double rate) {
        double rounded = Math.round(rate * 100.0) / 100.0;
        return rounded;
    }
}
