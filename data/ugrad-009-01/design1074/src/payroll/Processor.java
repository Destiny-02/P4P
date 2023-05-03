package payroll;
public class Processor {
	private Processing _P;
	public Processor(EmployeesDatabase database, String option){
		_P = null;
		if (option.equals("Payslips")){
			_P = new Employee().new Payslips(database);
		}else if (option.equals("Employees")){
			_P = new Employee().new Employees(database);
		}else if (option.equals("Burden")){
			_P = new Employee().new Burden(database);
		}else if (option.equals("PAYE")){
			_P = new Employee().new PAYE(database);
		}else{
			System.out.println("Invalid option of processing.\n The four valid options are: \n"
					+ "Payslips\n"
					+ "Employees\n"
					+ "Burden\n"
					+ "PAYE\n"
					+ "Please ensure that the second argument for this program is from one of above options.");
		}
	}
	public boolean isValid(){
		if (_P == null){
			return false;
		}else{
			return true;
		}
	}
}
