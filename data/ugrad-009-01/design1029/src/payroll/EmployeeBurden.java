package payroll;
public class EmployeeBurden {
	private double _wage;
	private double _hours;
	private String _type;
	public EmployeeBurden(){
		_wage = 0;
		_hours = 0;
		_type = null;
	}
	public EmployeeBurden(double wage, double hour, String type){
		_wage = wage;
		_hours = hour;
		_type = type;
	}
	public double calcBurden() {
		double value = 0;
		if(_type.equals("Salaried")){
			value = _wage/52;
		} else if(_type.equals("Hourly")){
			if(_hours <= 40){
				 value =  _wage * _hours;
			} else if(_hours <= 43){
				 value =_wage * 40 + _wage * 1.5 * (_hours - 40 );
			} else {
				 value = _wage * 40 + _wage * 1.5 * 3 + _wage * 2 * (_hours - 43);
			}
		} else {
			throw new RuntimeException("An employees type contains an Error.");
		}
		if(value < 0){
			throw new RuntimeException("Burden is neigative, check Data");
		}
		return value;
	}
}