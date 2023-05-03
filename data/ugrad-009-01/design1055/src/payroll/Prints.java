package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
abstract public class Prints {
    public void printdate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
    }
    public void printTotals(String dates, double value,String type){
        System.out.printf("%s\nTotal %s: $%.2f",dates,type,value);
        System.out.println();
    }
}
