package payroll;
public class PayslipsProcessing extends UtilityAbstract implements ProcessingInterface {
	private int _TID;
	private String _name;
	private String _startDate;
	private String _endDate;
	private double _grossEarnings;
	private double _incomeTax;
	private double _deduction;
	private double _nett;
	private double _YTD;
	private String _grossEarningsStr;
	private String _incomeTaxStr;
	private String _deductionStr;
	private String _nettStr;
	private String _YTDStr;
	public PayslipsProcessing(Employee employee) {
		_TID = employee.get_TID();
		_startDate = employee.get_start();
		_endDate = employee.get_end();
		_deduction = employee.get_deduction();
		_name = employee.convertNamePayslip();
		_grossEarnings = employee.calculateGross();
		_YTD = employee.get_YTD() + _grossEarnings;
		_incomeTax = employee.calculateTax();
		_nett = employee.calculateNett();
		_grossEarningsStr = toString2DP(_grossEarnings);
		_incomeTaxStr = toString2DP(_incomeTax);
		_deductionStr = toString2DP(_deduction);
		_nettStr = toString2DP(_nett);
		_YTDStr = toString2DP(_YTD);
	}
	public PayslipsProcessing() {
	}
	public void reorganize(PayslipsProcessing[] input) {
		int i = 0;
		int j = 0;
		for (i = 0; i < input.length; i++) {
			for(j = 0; j < (input.length - 1); j++) {
				if (input[j]._TID > input[j+1]._TID) {
					PayslipsProcessing temp = input[j];
					input[j] = input[j+1];
					input[j+1] = temp;
				}
			}
		}
	}
	public void publishInfo() {
		System.out.println(_TID + ". " + _name + ", Period: " + _startDate
				+ " to " + _endDate + ". Gross: $" + _grossEarningsStr +
				", PAYE: $" + _incomeTaxStr + ", Deductions: $" + _deductionStr
				+ " Nett: $" + _nettStr + " YTD: $" + _YTDStr);
	}
}
