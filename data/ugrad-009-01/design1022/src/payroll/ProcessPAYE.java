package payroll;
public class ProcessPAYE implements ProcessCommand {
    private double _TotalPAYE = 0;
    private EmployeeRecordsList _recordList;
    private String[] _processedRecord= new String[2];
    private String _startDate;
    private String _endDate;
    ProcessPAYE(EmployeeRecordsList list) {
        _recordList = list;
        _startDate = _recordList.getStartPayPeriod();
        _endDate = _recordList.getEndPayPeriod();
    }
    private void calcBurden() {
        for (EmployeeRecord e : _recordList) {
            _TotalPAYE += e.returnPAYE();
        }
    }
    private void format() {
        _processedRecord[0] = _startDate + " to " + _endDate;
        _processedRecord[1] = "Total PAYE: $" + _TotalPAYE;
    }
    public String[] getProcessedRecords() {
        calcBurden();
        format();
        return _processedRecord;
    }
}
