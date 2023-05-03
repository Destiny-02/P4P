package payroll;
import java.util.ArrayList;
public class BurdenProcessing extends ProcessingPayroll {
	public BurdenProcessing(ArrayList<Integer> tID, ArrayList<String> familyName, ArrayList<String> givenName,
			ArrayList<String> employment, ArrayList<Double> rate, ArrayList<Double> yTD, ArrayList<String> startDate,
			ArrayList<String> endDate, ArrayList<Double> hours, ArrayList<Double> deductions) {
		super(tID, familyName, givenName, employment, rate, yTD, startDate, endDate, hours, deductions);
	}
	public void burdenProcessingMethodTotal(){
		PayslipsProcessing grossTotalObj= new PayslipsProcessing(_tID, _familyName, _givenName, _employment, _rate, _yTD, _startDate, _endDate, _hours, _deductions);
		double grossTotal=grossTotalObj.grossTotalProcessing(grossTotalObj);
		burdenProcessingPrinting(grossTotal);
	}
	public void burdenProcessingPrinting(double grossTotal){
		System.out.printf("%s to %s\n",_startDate.get(1), _endDate.get(1));
		System.out.printf("Total Burden: $");
		System.out.printf("%.2f",grossTotal);
		System.out.println("\n");
	}
}
