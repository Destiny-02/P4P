package payroll;
public class EmployeesProcessing extends UtilityAbstract implements ProcessingInterface {
	private String _name;
	private int _TID;
	private String _jobType;
	private double _payRate;
	private double _grossEarnings;
	private double _YTD;
	private String _payRateStr;
	private String _YTDStr;
	public EmployeesProcessing(Employee employee) {
		_name = employee.get_name();
		_TID = employee.get_TID();
		_jobType = employee.get_jobType();
		_payRate =employee.get_rate();
		_grossEarnings = employee.calculateGross();
		_YTD = employee.get_YTD() + _grossEarnings;
		_payRateStr = toString2DP(_payRate);
		_YTDStr = toString2DP(_YTD);
	}
	public EmployeesProcessing() {
	}
	public void reorganize(EmployeesProcessing[] employees) {
		for (int i = 0; i < employees.length; i++) {
			for (int j = 0; j < (employees.length -1); j++) {
				if (employees[j]._name.compareTo(employees[j+1]._name) > 0 ) {
					EmployeesProcessing temp = employees[j];
					employees[j] = employees[j+1];
					employees[j+1] = temp;
				}
			}
		}
	}
	public void publishInfo() {
		System.out.println(_name + " (" +  _TID + ") " +
				_jobType + ", $" + _payRateStr + " YTD:$" + _YTDStr);
	}
}
