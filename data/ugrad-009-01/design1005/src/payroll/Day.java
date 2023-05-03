package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
class Day {
    void printDateToday() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(df.format(date));
    }
}
