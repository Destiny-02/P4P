package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public abstract class Printer {
	protected List<Employee> _employeeRecord;
	protected String _processType;
	private DateFormat _dateFormat;
	private Date _date;
	public Printer(List<Employee> employeeRecord, String processType) {
		_employeeRecord = employeeRecord;
		_processType = processType;
		_dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		_date = new Date();
		System.out.println(_dateFormat.format(_date));
	}
	public abstract void print();
}
