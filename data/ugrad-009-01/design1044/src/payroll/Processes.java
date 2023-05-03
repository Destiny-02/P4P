package payroll;
public enum Processes {
	Payslips, PAYE, Burden, Employees;
	public static boolean contains(String processType) {
		for (Processes p : Processes.values()) {
			if (processType.equals(p.toString())) {
				return true;
			}
		}
		return false;
	}
}
