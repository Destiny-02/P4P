package payroll;
class SalariedEmployee extends Employee{
	private double _salary;
	private double _gross;
	private double _paye;
	private double _nett;
	private double _YTD;
	private double _hours;
	private double _deduction;
	public SalariedEmployee(int TID,String name,double salary,double ytd,String start,String end,
			double hr,double d,double gross,double paye,double nett){
		super(TID,name,start,end);
		_salary = salary;
		_hours = hr;
		_gross = gross;
		_paye = paye;
		_nett = nett;
		_YTD = ytd;
		_deduction = d;
	}
	protected void printEmployee(){
		System.out.printf(getName()+" ("+getTID()+") Salaried, $%.2f YTD:$%.2f\n",_salary,_YTD);
	}
	protected void printPayslip(){
		String[] a = super.payslipProcess();
		System.out.printf("%s. %s %s, Period: %s to %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f Nett: $%.2f YTD: $%.2f\n",a[0],a[1],a[2],a[3],a[4],_gross,_paye,_deduction,_nett,_YTD);
	}
}
