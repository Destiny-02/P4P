package payroll;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Accountant {
    public String makeMoney(double value) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return ("$" + df.format(value));
    }
    public String giveDate() {
        Date myDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        return (dateFormat.format(myDate));
    }
    public double roundIt(double number){
        DecimalFormat df = new DecimalFormat("#0.00");
        return Double.valueOf(df.format(number));
    }
}
