package payroll;
import java.util.List;
public class Paye {
	private String _startdate;
	private String _enddate;
	private double _paye;
	public Paye(List<Payslip> payslips) {
		_startdate = payslips.get(0).getStartDate();
		_enddate = payslips.get(0).getEndDate();
		_paye = 0;
		setPaye(payslips);
	}
	private void setPaye(List<Payslip> payslips) {
		for (Payslip employee : payslips) {
			_paye += employee.getPaye();
		}
	}
	public void printPaye(){
		System.out.format("%s to %s%nTotal PAYE: $%.2f%n",
				_startdate, _enddate, _paye);
	}
}
