package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Payroll {
    public static void main(String[] args) {
        String fileName = args[0];
        String processType = args[1];
        EmployeeRecordsList recordList = new EmployeeRecordsList(fileName);
        RecordsProcessor processor = new RecordsProcessor(recordList);
        String[] outputString = processor.processRecords(processType);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        for(String s:outputString){
            System.out.println(s);
        }
    }
}