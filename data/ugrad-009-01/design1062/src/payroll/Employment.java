package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
public class Employment {
	private double _tid;
	private String _fName;
	private String _lName;
	private String _startDate;
	private String _endDate;
	private double _deduction;
	private double _ytd;
	private double _gross;
	private double _rate;
	private String _emType;
	public Employment(double tid, String fName, String lName, double ytd, String startDate, String endDate, double deduction, double gross, String emType, double rate){
		_fName = fName;
		_lName = lName;
		_startDate = startDate;
		_endDate = endDate;
		_deduction = deduction;
		_ytd = ytd;
		_tid = tid;;
		_gross = gross;
		_emType = emType;
		_rate = rate;
	}
	private double calculatePaye(double gross){
		double totalTax;
		double paye;
		double annualGross = gross * 52;
		if(annualGross <= 14000){
			totalTax = annualGross * 0.105;
		} else if(annualGross <= 48000){
			annualGross = annualGross - 14000;
			totalTax = 1470 + annualGross * 0.175;
		} else if(annualGross <= 70000){
			annualGross = annualGross - 48000;
			totalTax = 7420 + annualGross * 0.3;
		} else {
			annualGross = annualGross - 70000;
			totalTax = 14020 + annualGross * 0.33;
		}
		paye = Math.round((totalTax/52) * 100);
		paye = paye / 100;
		return paye;
	}
	public void displayDate() {
		DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		System.out.println(date.format(currentDate));
	}
	private double calculateNett(double gross, double paye, double deduction){
		double Nett = gross - paye - deduction;
		return Nett;
	}
	private double calculateYTD(double gross, double ytd){
		double newYtd = gross + ytd;
		return newYtd;
	}
	public double calculateBurdenTotal(double totalBurden){
		totalBurden = totalBurden + _gross;
		return totalBurden;
	}
	public double calculatePayeTotal(double totalPaye){
		double paye = calculatePaye(_gross);
		totalPaye = totalPaye + paye;
		return totalPaye;
	}
	public void displayPaySlip(){
		double paye = calculatePaye(_gross);
		double nett = calculateNett(_gross, paye, _deduction);
		double ytd = calculateYTD(_gross, _ytd);
		int newTid = (int)_tid;
		System.out.println(newTid + ". " + _fName + " " + _lName + ", Period: " + _startDate + " to " + _endDate +
				". Gross: $" + String.format("%.2f", _gross) + ", PAYE: $" + String.format("%.2f", paye) + ", Deductions: $"
				+ String.format("%.2f", _deduction) + " Nett: $" + String.format("%.2f", nett) + " YTD: $" + String.format("%.2f", ytd));
	}
	public void displayEmployeeSlip(){
		int newTid = (int)_tid;
		double ytd = calculateYTD(_gross, _ytd);
		System.out.println(_lName + ", " + _fName + " (" + newTid + ") " + _emType + ", $" + String.format("%.2f", _rate) + " YTD:$" + String.format("%.2f", ytd));
	}
	public void displayBurdenSlip(double totalBurden){
		displayDate();
		System.out.println(_startDate + " to " + _endDate);
		System.out.println("Total Burden: $" + totalBurden);
	}
	public void displayPayeSlip(double totalPaye){
		displayDate();
		System.out.println(_startDate + " to " + _endDate);
		System.out.println("Total PAYE: $" + totalPaye);
	}
	public static class EmploymentComparatorTid implements Comparator<Employment>{
		public int compare(Employment o1, Employment o2) {
			if(o1._tid > o2._tid){
				return 1;
			} else if(o1._tid < o2._tid){
				return -1;
			} else {
				return 0;
			}
		}
	}
	public static class EmploymentComparatorName implements Comparator<Employment>{
		public int compare(Employment o1, Employment o2) {
			return o1._lName.compareTo(o2._lName);
		}
	}
}