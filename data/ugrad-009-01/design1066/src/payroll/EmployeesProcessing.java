package payroll;
import java.util.ArrayList;
public class EmployeesProcessing extends ProcessingPayroll {
	public EmployeesProcessing(ArrayList<Integer> tID, ArrayList<String> familyName, ArrayList<String> givenName,
			ArrayList<String> employment, ArrayList<Double> rate, ArrayList<Double> yTD, ArrayList<String> startDate,
			ArrayList<String> endDate, ArrayList<Double> hours, ArrayList<Double> deductions) {
		super(tID, familyName, givenName, employment, rate, yTD, startDate, endDate, hours, deductions);
	}
	public void employeeProcessingMethod(){
		for (int employeeIndex=0;employeeIndex<_tID.size();employeeIndex++){
			employeeProcessingPrinting(employeeIndex);
			PayslipsProcessing obtainYTDObj= new PayslipsProcessing(_tID, _familyName, _givenName, _employment, _rate, _yTD, _startDate, _endDate, _hours, _deductions);
			obtainYTDObj.obtainYTD(obtainYTDObj, employeeIndex);
		}
	}
	public void employeeProcessingPrinting(int employeeIndex){
		System.out.printf(_familyName.get(employeeIndex));
		System.out.printf(",");
		System.out.printf(_givenName.get(employeeIndex));
		System.out.printf(" (");
		System.out.print(_tID.get(employeeIndex));
		System.out.printf(") ");
		System.out.print(_employment.get(employeeIndex));
		System.out.printf(", $");
		System.out.printf("%.2f",_rate.get(employeeIndex));
		System.out.printf(" YTD:$");
	}
}

