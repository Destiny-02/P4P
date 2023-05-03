package payroll;
import java.util.List;
public class ListOfEmployees {
	private List<String> _lineListOfemployees;
	private IndividualEmployee[] _employeesList;
	public ListOfEmployees(List<String> lineListOfemployees){
		_lineListOfemployees = lineListOfemployees;
		_employeesList = new IndividualEmployee[_lineListOfemployees.size()];
	}
	public void separateListToIndividualEmployees() throws PayrollException{
		int count = 0;
		for(String stringEmployee : _lineListOfemployees){
			_employeesList[count] = new IndividualEmployee(stringEmployee);
			_employeesList[count].separateLineToData();
			count++;
		}
	}
	public void calculateNeededData(){
		for(int i = 0; i < _employeesList.length; i++){
			_employeesList[i].calculatingNeededData();
		}
	}
	public void sortEmployeesListByTID(){
		SortEmployeeList sorter = new SortEmployeeList(_employeesList);
		sorter.sortEmployeeListTID();
	}
	public void sortEmployeesListByName(){
		SortEmployeeList sorter = new SortEmployeeList(_employeesList);
		sorter.sortEmployeeListByName();
	}
	public void printAllEmployeePayslips(){
		for(int i = 0; i < _employeesList.length; i++){
			_employeesList[i].printIndividualEmployeePayslip();
		}
	}
	public void printAllEmployeeData(){
		for(int i = 0; i < _employeesList.length; i++){
			_employeesList[i].printIndividualEmployeeData();
		}
	}
	public void employeeListTotalGross(){
		_employeesList[0].printEmployeeStartToEndDate();
		double totalGross = 0.00;
		for(int i = 0; i < _employeesList.length; i++){
			totalGross = _employeesList[i].getGross() + totalGross;
		}
		System.out.println("Total Burden: $" + totalGross);
	}
	public void employeeListTotalPAYE(){
		_employeesList[0].printEmployeeStartToEndDate();
		double totalPaye = 0.00;
		for(int i = 0; i < _employeesList.length; i++){
			totalPaye = _employeesList[i].getPaye() + totalPaye;
		}
		System.out.println("Total PAYE: $" + totalPaye);
	}
	public void checkForSomeErrors() throws PayrollException{
		CheckDataForErrors checking = new CheckDataForErrors();
		checking.checkUniqueTID(_employeesList);
		for(IndividualEmployee employee : _employeesList){
			checking.deductionsCompareEarning(employee.getDeduction(), employee.getGross());
		}
	}
}
