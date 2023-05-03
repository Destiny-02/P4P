package payroll;
public class Date implements Comparable<Date> {
    static final int[] DAYS_IN_MONTH = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int _day;
    private int _month;
    private int _year;
    public Date(String date) throws InvalidEmployeeException {
        String[] dateSplit = date.split("-");
        _year = Integer.parseInt(dateSplit[0]);
        _month = Integer.parseInt(dateSplit[1]);
        _day = Integer.parseInt(dateSplit[2]);
        if (!verifyDate()) {
            throw new InvalidEmployeeException("Date not valid (\"" + date + "\").");
        }
    }
    public int compareTo(Date other) {
        if (this.toString().equals(other.toString())) {
            return 0;
        }
        if (this._year < other._year) {
            return -1 * dayDifference(this, other);
        }
        if (this._month < other._month) {
            return -1 * dayDifference(this, other);
        }
        if (this._day < other._day) {
            return -1 * dayDifference(this, other);
        }
        return dayDifference(other, this);
    }
    private boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        }
        if (year % 100 == 0 && _year % 400 != 0) {
            return false;
        }
        return true;
    }
    private boolean verifyDate() {
        if (_month > 12) {
            return false;
        }
        boolean thirtyOneDays = _month % 2 == (_month<=7?1:0);
        if ((_month == 2 && _day > 28 + (isLeapYear(_year)?1:0)) || _day > 30 + (thirtyOneDays?1:0)) {
            return false;
        }
        return true;
    }
    public String toString() {
        return _year + "-" + String.format("%02d", _month) + "-" + String.format("%02d", _day);
    }
    private int dayDifference(Date dateStart, Date dateEnd) {
        int yearsApart, monthsApart, daysApart, yearStart, monthStart;
        yearsApart = dateEnd._year - dateStart._year;
        yearStart = dateStart._year;
        monthsApart = dateEnd._month - dateStart._month;
        monthStart = dateStart._month;
        if (monthsApart < 0) {
            monthsApart += 12;
            --yearsApart;
            ++yearStart;
        }
        daysApart = dateEnd._day - dateStart._day;
        if (daysApart < 0) {
            daysApart += DAYS_IN_MONTH[dateStart._month-1] + (dateStart._month==2&&isLeapYear(dateStart._year)?1:0);
            if (monthsApart < 0) {
                monthsApart += 12;
                --yearsApart;
                ++yearStart;
            }
            --monthsApart;
            ++monthStart;
        }
        for (int i = monthStart; i < monthStart + monthsApart; ++i) {
            int monthIndex = ((i-1) % 12) + 1;
            daysApart += DAYS_IN_MONTH[dateStart._month-1] +
                (monthIndex+1==2&&isLeapYear(i<=12?dateStart._year:yearStart)?1:0);
        }
        for (int i = yearStart; i < yearStart + yearsApart; ++i) {
            daysApart += (isLeapYear(i)?366:365);
        }
        ++daysApart;
        return daysApart;
    }
}
