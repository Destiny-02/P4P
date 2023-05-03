package payroll;
public enum ProcessType {
	PAYSLIPS("Payslips"), EMPLOYEES("Employees"), BURDEN("Burden"), PAYE("PAYE");
	private String _process;
	ProcessType (String process) {
		_process = process;
	}
	public String getProcess() {
		return _process;
	}
}
