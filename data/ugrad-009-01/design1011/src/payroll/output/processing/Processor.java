package payroll.output.processing;
import java.util.List;
import payroll.file.Record;
public interface Processor {
	public void process();
	public void recieveData(List<Record> recList);
}
