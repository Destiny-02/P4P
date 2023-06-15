package payroll;
import java.util.List;
public class PayslipsProcessing extends CompanyDetails implements Process{
	private List<String> _information;
	public PayslipsProcessing(List<String> information){
		_information = information;
	}
	public void sortInformation(){
        try{
		separateInfoLists(_information);
		fillListOfList();
        sortPayslipsInfo();
        }catch(NumberFormatException numex){
			System.out.println("ERROR! Invalid TID format! Only integers are allowed! e.g. '216'");
        }
	}
	public void printResult(){
		try{
		printPayslipsResult();
		}catch(NumberFormatException numex){
			System.out.println("ERROR! Invalid money amount format! Only digits after '$' format is allowed! e.g. '$20'");
		}
	}
}
