package payroll;
public class PAYE {
	private double _PAYE;
	private String _Period;
	public PAYE(Employee a){
		_PAYE=0;
		_Period=a.getPeriod();
	}
	public double Add(double PAYE){
		return _PAYE=_PAYE +PAYE;
	}
	public void Processing(){
		System.out.println(_Period);
		System.out.println("Total PAYE: $"+_PAYE);
	}
}

