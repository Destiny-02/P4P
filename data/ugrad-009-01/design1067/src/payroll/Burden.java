package payroll;
public class Burden extends Data{
	public Burden(String Employment, double Rate, double Hours) {
		super(Employment, Rate, Hours);
	}
	public double getGross(){
		PayE ss=new PayE(Employment,Rate,Hours);
		if(Employment.equalsIgnoreCase("salaried")){
		return ss.calculateIncome()/52;
		}
		else if(Employment.equalsIgnoreCase("hourly")){
			return ss.calculateIncome();
		}
		else{
			throw new RuntimeException("Invalid Employment Status("+Employment+")");
		}
	}
	public void printBurden(String start, String end, double Sum){
		System.out.println(start+" to "+end);
		System.out.printf("Total Burden: $%.2f",Sum);
		System.out.print("\n");
	}
}

