package payroll;
public class ProcessPayslips implements ProcessCommand {
    private EmployeeRecordsList _recordList;
    private String[] _processedRecord;
    ProcessPayslips(EmployeeRecordsList list) {
        _recordList = list;
        _processedRecord = new String[_recordList.size()];
    }
    public String[] getProcessedRecords() {
        _recordList.sortRecordsByID();
        format();
        return _processedRecord;
    }
    private void format() {
        int i = 0;
        for(EmployeeRecord e: _recordList){
            _processedRecord[i] = e.formatForPayslips();
            i++;
        }
    }
}

