package payroll;
public class Burden {
	private double _Burden;
	private String _Period;
	public Burden(Employee a){
		_Burden=0;
		_Period=a.getPeriod();
	}
	public double Add(double Burden){
		return _Burden=_Burden +Burden;
	}
	public void Processing(){
		System.out.println(_Period);
		System.out.println("Total Burden: $"+_Burden);
	}
}
