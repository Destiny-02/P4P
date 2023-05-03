package payroll;
import java.util.List;
public class BurdenProcessing extends CompanyDetails implements Process{
	private String _burden;
private List<String> _information;
	public BurdenProcessing(List<String> information){
		_information = information;
	}
	public void sortInformation(){
		separateInfoLists(_information);
		fillListOfList();
	}
	public void printResult(){
		try{
		double burden = calculateBurden();
		_burden = String.format("%.2f", burden);
		printPeriod();
		System.out.println("Total Burden: $"+_burden);
		}catch(NumberFormatException numex){
			System.out.println("ERROR! Invalid money amount format! Only digits after '$' format is allowed! e.g. '$20'");
		}
	}
}
