package payroll;
import java.util.ArrayList;
import java.util.Comparator;
public class Employee extends Processing{
	public enum EmployeeType {
		SALARIED, HOURLY;
	}
	private int _tid;
	private String _name;
	private EmployeeType _type;
	private double _rate;
	private double _ytd;
	private String _startDate;
	private String _endDate;
	private double _hours;
	private double _deductions;
	private double _gross;
	public Employee(int tid, String name, EmployeeType type, double rate, double ytd,
			String start, String end, double hours, double deductions) throws InvalidEmployeeRecordException {
		_tid = tid; _name = name; _type = type; _rate = rate; _ytd = ytd;
		_startDate = start; _endDate = end; _hours = hours; _deductions = deductions;
		_gross = new Income(_rate, _hours, _type).getGross();
		if (_deductions > _gross) {
			throw new InvalidEmployeeRecordException("Error: Employee: " + _name +" (" + _tid +") Income is negative.");
		}
	}
	public boolean isUniqueTID(int tid, ArrayList<Employee> employees){
		for (Employee e: employees){
			if (e._tid == tid) {
				return false;
			}
		}
		return true;
	}
	public String generatePayslip(Employee e) {
		double paye = new Income(_rate, _hours, _type).getPaye();
		double nett = _gross - _deductions - paye;
		double newYTD = _gross + _ytd;
		String period = formatPeriod(_startDate, _endDate);
		return _tid + "." + formatName(_name) + "Period: " + period + ". Gross: " + "$" + formatCurrency(_gross) +
		", PAYE: $" + formatCurrency(paye) + ", Deductions: $" + formatCurrency(_deductions) + " Nett: $"
		+ formatCurrency(nett) + " YTD: $" + formatCurrency(newYTD);
	}
	@Override
	public String toString(){
		String type = (_type == EmployeeType.SALARIED)? "Salaried" : "Hourly";
		double newYtd = _gross + _ytd;
		return _name + " (" + _tid + ") " + type + ", $" + formatCurrency(_rate) + " YTD:$" + formatCurrency(newYtd);
	}
	public void dispBurden(ArrayList<Employee> employees){
		double burden = 0;
		String period = formatPeriod(employees.get(0)._startDate, employees.get(0)._endDate);
		for (Employee e: employees){
			burden += e._gross;
		}
		System.out.println(period + "\nTotal Burden: $" + formatCurrency(burden));
	}
	public void dispPaye(ArrayList<Employee> employees){
		double paye = 0;
		String period = formatPeriod(employees.get(0)._startDate, employees.get(0)._endDate);
		for (Employee e: employees){
			paye += new Income(e._rate, e._hours, e._type).getPaye();
		}
		System.out.println(period + "\nTotal PAYE: $" + formatCurrency(paye));
	}
	public enum EmployeeComparator implements Comparator<Employee> {
		employeeTIDComparator{
			@Override
			public int compare(Employee e1, Employee e2) {
				return e1._tid - e2._tid;
			};
		},
		employeeFamilyName{
			public int compare(Employee e1, Employee e2) {
				String employee1Name = e1._name;
				String employee2Name = e2._name;
				return employee1Name.compareTo(employee2Name);
			}
		},
		employeeGivenName {
			public int compare(Employee e1, Employee e2) {
				String employee1Name = e1.formatName(e1._name);
				String employee2Name = e2.formatName(e2._name);
				return employee1Name.compareTo(employee2Name);
			}
		};
	}
}
