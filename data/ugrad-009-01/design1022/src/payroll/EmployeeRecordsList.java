package payroll;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
public class EmployeeRecordsList implements Iterable<EmployeeRecord>{
    private String _fileName;
    private LinkedList<EmployeeRecord> _recordList;
    private String _startPayPeriod;
    private String _endPayPeriod;
    public EmployeeRecordsList(String inputFile) {
         _fileName = inputFile;
        _recordList = new LinkedList<EmployeeRecord>();
        createRecords();
    }
    public Iterator<EmployeeRecord> iterator(){
        return _recordList.iterator();
    }
    private void createRecords() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(_fileName));
            String line = reader.readLine();
            boolean recordAdded = false;
            int emptyLines = 0;
            while (emptyLines < 10 && line != null) {
		if (line.startsWith("#")) { line = reader.readLine();continue; }
                if (line.equals(""))
                    emptyLines++;
                else
                    try {
                        _recordList.add(new EmployeeRecord(line));
                        recordAdded = true;
                    } catch (NameInputException e) {
                        e.reportError();
                    } catch (DateFormatException e){
                        e.reportError();
                    } catch (MissingDataException e) {
                        e.recordError();
                    } catch (NonsenseValueException e){
                        e.recordError();
                    }
                line = reader.readLine();
            }
            if (!recordAdded) {
                throw new InputMismatchException("There are no employees in the file");
            }
            if (recordAdded){
                _startPayPeriod = _recordList.get(0).getStartPeriod();
                _endPayPeriod = _recordList.get(0).getEndPeriod();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file");
        } catch (IOException e) {
            System.out.println("problem with IO");
        }
    }
    public int size(){
        return _recordList.size();
    }
    public void sortRecordsByName(){
        Collections.sort(_recordList, EmployeeRecord.NameComparator);
    }
    public void sortRecordsByID(){
        Collections.sort(_recordList, EmployeeRecord.IDComparator);
    }
    public String getStartPayPeriod(){
        return _startPayPeriod;
    }
    public String getEndPayPeriod(){
        return _endPayPeriod;
    }
}
