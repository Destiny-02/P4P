package payroll;
public class OutputEmployee {
	private double _YTD;
	OutputEmployee (Employee currentEmployee, double YTD) {
		_YTD = YTD;
		String employement = findEmployement(currentEmployee.onSalary());
		System.out.println(String.format("%s (%d) %s, $%.2f YTD:$%.2f",
			currentEmployee.getName(),
			currentEmployee.getTaxID(),
			employement,
			currentEmployee.getRate(),
			_YTD
		));
	}
	private String findEmployement(Boolean isSalary) {
		if (isSalary) {
			return "Salaried";
		} else {
			return "Hourly";
		}
	}
}