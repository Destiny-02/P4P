package payroll.output.processing;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import payroll.file.Record;
public abstract class AbstractProcessor {
	protected LinkedList<Record> _fileData;
	public void recieveData(List<Record> recList) {
		_fileData = (LinkedList<Record>) recList;
	}
	public void printDate(){
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(df.format(cal.getTime()));
	}
}
