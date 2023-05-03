package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class PrintOutput {
	private final Form _currentForm;
	private final EmployeeRecords _employeeRecords;
	PrintOutput(Form currentForm, EmployeeRecords employeeRecords) {
		_employeeRecords = employeeRecords;
		_currentForm = currentForm;
	}
	public void printForm() {
		if (_currentForm instanceof Payslips) {
			payslipOut();
		} else if (_currentForm instanceof Employees) {
			employeesOut();
		} else if (_currentForm instanceof Burden) {
			burdenOut();
		} else if (_currentForm instanceof Paye) {
			payeOut();
		}
	}
	private void payslipOut(){
		Payslips payslipForm = (Payslips)_currentForm;
		String currentString;
		startPrint();
		for (int i = 0; i < payslipForm.getPrintOrder().length; i++){
			EmployeeData currentEmployee = (_employeeRecords.getEmployee(payslipForm.getPrintOrder()[i]));
			currentString = currentEmployee.payslipFormat();
			System.out.println(currentString);
		}
	}
	private void employeesOut() {
		Employees employeeForm = (Employees)_currentForm;
		String currentString;
		startPrint();
		for (int i = 0; i < employeeForm.getPrintOrder().length; i++) {
			EmployeeData currentEmployee = (_employeeRecords.getEmployee(employeeForm.getPrintOrder()[i]));
			currentString = currentEmployee.employeesFormat();
			System.out.println(currentString);
		}
	}
	private void burdenOut(){
		Burden burdenForm = (Burden)_currentForm;
		startPrint();
		startPrintTwoDates(burdenForm.getFirstEmployee());
		System.out.printf("Total Burden: $%.2f", burdenForm.getBurdenTotal());
		System.out.println();
	}
	private void payeOut(){
		Paye payeForm = (Paye)_currentForm;
		startPrint();
		startPrintTwoDates(payeForm.getFirstEmployee());
		System.out.printf("Total PAYE: $%.2f", payeForm.getPayeTotal());
		System.out.println();
	}
	private void startPrintTwoDates(EmployeeData firstEmployee){
		System.out.println(firstEmployee.dateFormat());
	}
	private void startPrint(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
	}
}
