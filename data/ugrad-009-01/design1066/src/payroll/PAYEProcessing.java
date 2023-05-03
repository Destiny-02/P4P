package payroll;
import java.util.ArrayList;
public class PAYEProcessing extends ProcessingPayroll {
	public PAYEProcessing(ArrayList<Integer> tID, ArrayList<String> familyName, ArrayList<String> givenName,
			ArrayList<String> employment, ArrayList<Double> rate, ArrayList<Double> yTD, ArrayList<String> startDate,
			ArrayList<String> endDate, ArrayList<Double> hours, ArrayList<Double> deductions) {
		super(tID, familyName, givenName, employment, rate, yTD, startDate, endDate, hours, deductions);
	}
	public void PAYEProcessingTotal(){
		PayslipsProcessing payeTotalObj= new PayslipsProcessing(_tID, _familyName, _givenName, _employment, _rate, _yTD, _startDate, _endDate, _hours, _deductions);
		double pAYETotal=payeTotalObj.PAYETotalAccessing(payeTotalObj);
		PAYEProcessingPrinting(pAYETotal);
	}
	public void PAYEProcessingPrinting(double PAYETotal){
		System.out.printf("%s to %s\n",_startDate.get(1), _endDate.get(1));
		System.out.printf("Total PAYE: $");
		System.out.print(PAYETotal);
		System.out.println("\n");
	}
}
