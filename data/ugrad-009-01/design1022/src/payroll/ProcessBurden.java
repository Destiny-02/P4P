package payroll;
public class ProcessBurden implements ProcessCommand {
    private double _totalBurden = 0.00;
    private EmployeeRecordsList _recordList;
    private String[] _processedRecord = new String[2];
    private String _startDate;
    private String _endDate;
    ProcessBurden(EmployeeRecordsList list) {
        _recordList = list;
        _startDate = _recordList.getStartPayPeriod();
        _endDate = _recordList.getEndPayPeriod();
    }
    private void calcTotalBurden() {
        for (EmployeeRecord e : _recordList) {
            _totalBurden += e.returnGrossEarnings();
        }
    }
    private void format() {
        _processedRecord[0] = _startDate + " to " + _endDate;
        _processedRecord[1] = "Total Burden: $" + _totalBurden;
    }
    public String[] getProcessedRecords() {
        calcTotalBurden();
        format();
        return _processedRecord;
    }
}

