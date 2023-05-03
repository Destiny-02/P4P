package payroll;
public enum PayType {
	Salary("Salaried"),Hourly("Hourly");
	String _token;
	private PayType(String type) {
		_token = type;
	}
	public String GetToken(){
		return _token;
	}
	public static PayType getPaytypeForToken(String token) {
			for(PayType type: PayType.values()){
				if(type.GetToken().equals(token)){
					return type;
				}
			}
			return null;
	}
}
