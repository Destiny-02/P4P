
package payroll;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class Payroll {
    public static void main(String[] args) {
        ProcessData allData = new ProcessData(args[0]);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        Process inputProcess = new Process(args[1]);
        allData.processOutput(inputProcess);
    }
}
