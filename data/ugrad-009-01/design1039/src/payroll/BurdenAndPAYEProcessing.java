package payroll;
public class BurdenAndPAYEProcessing extends UtilityAbstract implements ProcessingInterface{
	private String _startDate;
	private String _endDate;
	private double _variable;
	private String _type;
	private String _variableStr;
	public BurdenAndPAYEProcessing(Employee employee) {
		_startDate = employee.get_start();
		_endDate = employee.get_end();
	}
	public BurdenAndPAYEProcessing() {
	}
	public void setType(String type) {
		_type = type;
	}
	public void calculateVariable(Employee[] employeeInfo) {
		_variable = 0;
		double amount = 0;
		for (int i = 0; i < employeeInfo.length; i++) {
			Employee currentEmployee = new Employee();
			currentEmployee = employeeInfo[i];
			if (_type.equals("Burden")) {
				amount = currentEmployee.calculateGross();
			}
			else {
				amount = currentEmployee.calculateTax();
			}
			_variable = _variable + amount;
		}
		_variableStr = toString2DP(_variable);
	}
	public void publishInfo() {
		System.out.println(_startDate + " to " + _endDate);
		if (_type.equals("Burden")) {
			System.out.println("Total Burden: $" + _variableStr);
		}
		else if (_type.equals("PAYE")) {
			System.out.println("Total PAYE: $" + _variableStr);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
}
