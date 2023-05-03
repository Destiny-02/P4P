
package payroll.employees;
import payroll.PayrollException;
import payroll.employees.salary.HourlyPay;
import payroll.employees.salary.Pay;
import payroll.employees.salary.SalariedPay;
public class Employee implements Comparable<Employee> {
	private final int _tid;
	private final String _lname;
	private final String _fname;
	private final Pay _salary;
	protected Employee(String[] arr) throws PayrollException {
		_tid = Integer.parseInt(arr[0]);
		_lname = arr[1];
		_fname = arr[2];
		try {
			switch (arr[3]) {
			case "Hourly":
				_salary = new HourlyPay(arr); break;
			case "Salaried":
				_salary = new SalariedPay(arr); break;
			default:
				throw new PayrollException("employment", arr[3], "Not a valid employment type."
						+ "Valid values are \'Hourly\' and \'Salaried\'");
			}
		} catch (PayrollException e) {
			throw new PayrollException(_tid, e.data());
		}
	}
	public int compareTo(Employee e) {
		if (this._lname.compareTo(e._lname) == 0) {
			return this._fname.compareTo(e._fname);
		} else {
			return this._lname.compareTo(e._lname);
		}
	}
	protected int getTID() {
		return _tid;
	}
	protected String payslip(String period) throws PayrollException {
		String name = _fname + " " + _lname + ", ";
		String tid = _tid + ". ";
		try {
			String financialArray = _salary.payslip();
			return tid + name + period + financialArray;
		} catch (PayrollException e) {
			throw new PayrollException(_tid, e.data());
		}
	}
	protected String employeeStatement() throws PayrollException {
		try {
			return _lname + ", " + _fname + " (" + _tid +  ") " + _salary.employeeStatement();
		} catch (PayrollException e) {
			throw new PayrollException(_tid, e.data());
		}
	}
	protected float burden() throws PayrollException {
		try {
			return _salary.gross();
		} catch (PayrollException e) {
			throw new PayrollException(_tid, e.data());
		}
	}
	protected float paye () throws PayrollException {
		try {
			return _salary.paye();
		} catch (PayrollException e) {
			throw new PayrollException(_tid, e.data());
		}
	}
}
