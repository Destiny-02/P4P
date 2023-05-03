package payroll;
public class ExceptionCheck {
	public void checkException(String[] employeeData){
		try{
			wrongDateFormat(employeeData[5]);
			wrongDateFormat(employeeData[6]);
			wrongNameFormat(employeeData[1]);
			noDollar(employeeData[3]);
			noDollar(employeeData[4]);
			noDollar(employeeData[8]);
		}
		catch(InputError ex){
			{System.out.println(ex);}
		}
	}
	private void wrongDateFormat(String date) throws InputError{
		if(date.matches("\\d{4}-\\d{2}-\\d{2}")){
			return;
		}
		else{
			throw new InputError("Input Data has error: Date format incorrect");
		}
	}
	private void wrongNameFormat(String name) throws InputError{
		if(name.contains(",")){
			return;
		}
		else{
			throw new InputError("Input Data has error: Name format incorrect");
		}
	}
	private void noDollar(String money) throws InputError{
		if(money.startsWith("$")){
			return;
		}
		else{
			throw new InputError("Input Data has error: Absence of $ in Value: " + money);
		}
	}
	public void checkNonsenseValues(Employee employee) {
		try{
			employee.nonsense(employee);
		}
		catch(InputError ex){
			{System.out.println(ex);}
		}
	}
}
