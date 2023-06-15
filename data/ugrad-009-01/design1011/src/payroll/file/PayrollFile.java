package payroll.file;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import payroll.exceptions.PayrollException;
import payroll.output.processing.Processor;;
public class PayrollFile {
	private final BufferedReader _fileReader;
	private List<Record> _recordList = new LinkedList<>();
	public PayrollFile(String fileName) throws FileNotFoundException {
		_fileReader = new BufferedReader(new java.io.FileReader(fileName));
	}
	public void readAll() throws IOException, PayrollException {
		String line;
		while ((line = _fileReader.readLine()) != null) {
			if (line.equals("")) {
				continue;
			}
			if ((line.charAt(0) == '#') || (line.charAt(0) == ('\n'))) {
				continue;
			} else {
				Record rec = new Record(line);
				_recordList.add(rec);
			}
		}
	}
	public void close() throws IOException {
		_fileReader.close();
	}
	public void passData(Processor proc){
		proc.recieveData(_recordList);
	}
	public void updatePayFields()throws PayrollException{
		for(Record rec : _recordList){
			rec.updatePayFields();
		}
	}
}
