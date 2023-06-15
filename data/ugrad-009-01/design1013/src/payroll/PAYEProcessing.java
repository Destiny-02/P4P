package payroll;
import java.util.List;
public class PAYEProcessing extends CompanyDetails implements Process{
	private String _PAYE;
	private List<String> _information;
	public PAYEProcessing(List<String> information){
		_information = information;
	}
	public void sortInformation(){
		separateInfoLists(_information);
		fillListOfList();
	}
	public void printResult(){
		try{
		double totalPaye = calculateTotalPaye();
		_PAYE = String.format("%.2f", totalPaye);
		printPeriod();
		System.out.println("Total PAYE: $"+_PAYE);
		}catch(NumberFormatException numex){
			System.out.println("ERROR! Invalid money amount format! Only digits after '$' format is allowed! e.g. '$20'");
		}
	}
}
