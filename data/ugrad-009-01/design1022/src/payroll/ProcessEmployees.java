package payroll;
public class ProcessEmployees implements ProcessCommand {
    private EmployeeRecordsList _recordList;
    private String[] _processedRecord;
    ProcessEmployees(EmployeeRecordsList list) {
        _recordList = list;
        _processedRecord = new String[_recordList.size()];
    }
    public String[] getProcessedRecords() {
        _recordList.sortRecordsByName();
        format();
        return _processedRecord;
    }
    private void format() {
        int i = 0;
        for (EmployeeRecord e : _recordList) {
            _processedRecord[i] = e.formatForEmployees();
            i++;
        }
    }
}

