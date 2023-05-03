package payroll;
public class Payment {
	private Employee _employee;
	private double _gross;
	private double _PAYE;
	private double _nett;
	public Payment(Employee employee){
		double gross;
		double PAYE;
		double nett;
		double deduction;
		gross=employee.calcGross();
		PAYE = employee.calcPAYE(employee.calcYearGross());
		deduction = employee.getDeduction();
		nett = gross - PAYE - deduction;
		_employee=employee;
		_gross = gross;
		_PAYE = PAYE;
		_nett = nett;
		checkNegativeNett();
	}
	public double getGross(){
		return _gross;
	}
	public double getPAYE(){
		return _PAYE;
	}
	public double getNett(){
		return _nett;
	}
	public double calcNewYTD(){
		return _employee.getYTD()+_gross;
	}
	public double getDeduction(){
		return _employee.getDeduction();
	}
	public Employee getEmployee(){
		return _employee;
	}
	private void checkNegativeNett(){
		if(_nett<0){
			try{
				String msg="\nError: Deductions more than earnings. Check deduction value. (System is setting Nett to be 0.)";
				String employeeinfo="\n For Employee: " + getEmployee().getTID()+ ". " + getEmployee().getName();
				throw new PayrollUserException(msg+employeeinfo);
			}
			catch (PayrollUserException e){
				_nett=0;
				e.printStackTrace();
			}
		}
	}
}
