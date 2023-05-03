package payroll;
public class EmployeeTax {
	private double _wage;
	private double _tax;
	public EmployeeTax(){
		_wage = 0;
		_tax = 0;
	}
	public EmployeeTax(double wage){
		_wage = wage;
		_tax = calcTax();
	}
	private double calcTax() {
		double value = 0;
		if(_wage<0){
			value = -1;
		} else if( _wage - 14000 <= 0){
			value = _wage* 0.105;
		} else if(_wage - 48000 <= 0){
			value = (14000 * 0.105) + (_wage-14000)*(0.175);
		} else if( _wage - 70000 <= 0){
			value =  (14000 * 0.105) + (34000 * 0.175) + (_wage-48000)*(0.3);
		} else {
			value = (14000 * 0.105) + (34000 * 0.175) + (22000)*(0.3) + (_wage-70000)*(0.33);
		}
		if(value < 0 ){
			throw new RuntimeException("Yearly Tax calulated to be Negative, Check Data");
		}
		return value;
	}
	public double totalTax(){
		return _tax/52;
	}
}

