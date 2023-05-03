package payroll;
public enum EmployeeType {
	hourly("Hourly"), salaried("Salaried");
	private final String employee;
	private EmployeeType(final String employee){
		this.employee = employee;
	}
	public String toString(){
		return employee;
	}
	public static EmployeeType getEmployeeType(String name){
		for( EmployeeType employee: values()){
			if(employee.toString().equals(name)){
				return employee;
			}
		}
		throw new IllegalArgumentException(name + " is not a valid employee type");
	}
}