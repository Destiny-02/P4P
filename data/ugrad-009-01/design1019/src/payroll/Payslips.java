package payroll;
import java.text.DecimalFormat;
public class Payslips implements Display {
	private EmployeeRecords _employee;
	public Payslips (EmployeeRecords employee) {
		_employee = employee;
	}
	public double getPAYE() {
		if ( (_employee.getEmployment() ).equals("Salaried") ) {
			if (_employee.getRate() <= 14000.0) {
				return _employee.getRate()*0.105/52.0;
			} else if (_employee.getRate() > 14000.0 && _employee.getRate() <= 48000.0) {
				return (14000.0*0.105+(_employee.getRate()-14000.0)*0.175 )/52.0;
			} else if (_employee.getRate() > 48000.0 && _employee.getRate() <= 70000.0) {
				return (14000.0*0.105+34000.0*0.175+(_employee.getRate()-48000.0)*0.3 )/52.0;
			} else {
				return (14000.0*0.105+34000.0*0.175+22000*0.3+(_employee.getRate()-70000.0)*0.33 )/52.0;
			}
		} else {
			double annualGross = _employee.getGross()*52.0;
			if (annualGross <= 14000.0) {
				return annualGross*0.105/52.0;
			} else if (annualGross > 14000.0 && annualGross <= 48000.0) {
				return (14000.0*0.105+(annualGross-14000.0)*0.175 )/52.0;
			} else if (annualGross > 48000.0 && annualGross <= 70000.0) {
				return (14000.0*0.105+34000.0*0.175+(annualGross -48000.0)*0.3 )/52.0;
			} else {
				return (14000.0*0.105+34000.0*0.175+22000*0.3+(annualGross-70000.0)*0.33 )/52.0;
			}
		}
	}
	public double getNETT() {
		double NETT = _employee.getGross()- this.getPAYE()-_employee.getDeduction();
		return NETT;
	}
	public void display() {
		DecimalFormat TwoDP = new DecimalFormat("#.00");
		System.out.print(_employee.getTaxID()+". "+_employee.getGivenName()+" "+_employee.getFamilyName()+", Period: "+_employee.getStartDate()+" to "+_employee.getEndDate()+". Gross: $");
		System.out.println(TwoDP.format(_employee.getGross())+", PAYE: $"+TwoDP.format(this.getPAYE())+", Deductions: $"+TwoDP.format(_employee.getDeduction())+" Nett: $"+TwoDP.format(this.getNETT())+" YTD: $"+TwoDP.format(_employee.getCurrentYTD()) );
	}
	public void checkNegative() throws PayrollException {
		DecimalFormat TwoDP = new DecimalFormat("#.00");
		if (_employee.getGross() < 0) {
			throw new PayrollException("Negative Gross: "+TwoDP.format(_employee.getGross())+" for "+_employee.getGivenName()+", "+_employee.getFamilyName() );
		} else if (this.getPAYE() < 0) {
			throw new PayrollException("Negative PAYE: "+TwoDP.format(this.getPAYE())+" for "+_employee.getGivenName()+", "+_employee.getFamilyName() );
		} else if (this.getNETT() < 0) {
			throw new PayrollException("Negative Nett: "+TwoDP.format(this.getNETT())+" for "+_employee.getGivenName()+", "+_employee.getFamilyName() );
		} else if (_employee.getCurrentYTD() < 0) {
			throw new PayrollException("Negative YTD: "+TwoDP.format(_employee.getCurrentYTD())+" for "+_employee.getGivenName()+", "+_employee.getFamilyName() );
		} else {}
	}
}
