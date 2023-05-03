package payroll;
public class Employment {
	private Employmentstate _state;
	private double _rate;
	public Employment(String state,double rate){
		_state=(state.equals("Salaried"))?Employmentstate.Salaried:Employmentstate.Hourly;
		_rate=rate;
	}
	public boolean getEmploymentstate(){
		return this._state._state;
	}
	public String getEmploymentstateString(){
			return _state.toString();
		}
	public double getRate(){
		return this._rate;
	}
	private enum Employmentstate {
		Hourly(false),Salaried(true);
		private boolean _state;
		private Employmentstate(boolean a){
			_state=a;
		}
	}
}
