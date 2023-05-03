package payroll;
import java.util.HashSet;
public class ErrorChecking {
	private Employee _currentEmployee;
	private String _message;
	private boolean _flag;
	private HashSet<Integer> _TIDSet;
	ErrorChecking(String currentEmployeeLine, HashSet<Integer> TIDSet){
		try{
			_currentEmployee = new Employee(currentEmployeeLine);
		} catch(NumberFormatException e){
			_message = "expected a number but couldn't parse it.";
			_flag = true;
		} catch(ArrayIndexOutOfBoundsException e){
			_message = "not all fields were present.";
			_flag = true;
		}
		_TIDSet = TIDSet;
	}
	public void runErrorChecking() throws InputLineException{
		if(_flag==true){
			throw new InputLineException(_message);
		}
		if (isNegativeRate(_currentEmployee.getRate())){
			throw new InputLineException(_message);
		}
		if (isNegativeNett()){
			throw new InputLineException(_message);
		}
		if((_TIDSet != null) && isRepeatTID()){
			throw new InputLineException(_message);
		}
	}
	private boolean isRepeatTID(){
		int currentTID = _currentEmployee.getTID();
		if(_TIDSet.contains(currentTID)){
			_message = "a repeat TID Value.";
			return true;
		}
		return false;
	}
	private boolean isNegativeRate(double rate){
		if(rate<0){
			_message = "a negative rate.";
			return true;
		}
		return false;
	}
	private boolean isNegativeNett(){
		double currentNett = _currentEmployee.getNettIncome();
		if(currentNett<0){
			_message = "a negative Nett value.";
			return true;
		}
		return false;
	}
}
