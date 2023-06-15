package payroll;
import java.util.List;
public class EmployeesProcessing extends CompanyDetails implements Process{
	private List<String> _information;
	public EmployeesProcessing(List<String> information){
		_information = information;
	}
	public void sortInformation(){
		separateInfoLists(_information);
		fillListOfList();
		sortEmployeesInfo();
	}
	public void printResult(){
		try{
		printEmployeesResult();
		}catch(NumberFormatException numex){
			System.out.println("ERROR! Invalid money amount format! Only digits after '$' format is allowed! e.g. '$20'");
		}
	}
}
