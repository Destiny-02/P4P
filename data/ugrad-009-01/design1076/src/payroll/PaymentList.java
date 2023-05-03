package payroll;
import java.util.Collections;
public class PaymentList extends PayrollList<Payment>{
	public Payment getPayment(int index){
		return  _list.get(index);
	}
	public double calcTotalGross(){
		double totalGross=0;
		for (int i=0; i<getLength();i++){
			totalGross += getPayment(i).getGross();
		}
		return totalGross;
	}
	public double calcTotalPAYE(){
		double totalPAYE=0;
		for (int i=0; i<getLength();i++){
			totalPAYE += getPayment(i).getPAYE();
		}
		return totalPAYE;
	}
	public void sortInFamilyName(){
		Collections.sort(_list, new FamilyNameComparator());
	}
	public void sortInTID(){
		Collections.sort(_list, new TIDComparator());
	}
}
