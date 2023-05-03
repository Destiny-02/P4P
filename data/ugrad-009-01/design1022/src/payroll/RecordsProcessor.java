package payroll;
public class RecordsProcessor {
    public static final String PAYSLIPS = "Payslips";
    public static final String EMPLOYEES = "Employees";
    public static final String PAYE = "PAYE";
    public static final String BURDEN = "Burden";
    private EmployeeRecordsList _recordList;
    public RecordsProcessor(EmployeeRecordsList list) {
        _recordList = list;
    }
    public String[] processRecords(String processType) {
        ProcessCommand processor;
        switch (processType) {
            case PAYSLIPS:
                processor = new ProcessPayslips(_recordList);
                break;
            case EMPLOYEES:
                processor = new ProcessEmployees(_recordList);
                break;
            case PAYE:
                processor = new ProcessPAYE(_recordList);
                break;
            case BURDEN:
                processor = new ProcessBurden(_recordList);
                break;
            default:
                throw new RuntimeException("Invalid processType: " + processType);
        }
        return processor.getProcessedRecords();
    }
}
