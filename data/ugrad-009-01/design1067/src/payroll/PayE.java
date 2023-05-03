package payroll;
public class PayE extends Data{
	public PayE(String Employment, double Rate, double Hours) {
		super(Employment, Rate, Hours);
	}
	double tax=0;
	public double getIncomeTax(double income){
	if(Employment.equalsIgnoreCase("hourly")){
		income=income*52;
	}
	if(income<=14000){
	    tax = 0.105*income;
	}
	else if(income>14000 && income<=48000){
	    tax = 0.105*14000 + 0.175*(income-14000);
	}
	else if(income>48000 && income <70000)
	{
	    tax = 0.105*14000 + 0.175*(48000-14000) + 0.30*(income-48000);
	}
	else if(income>70000)
	{
	     tax = 0.105*14000 + 0.175*(48000-14000) + 0.30*(70000-48000)+0.33*(income-70000);
	}
	else{
		throw new RuntimeException("Error: Tax of ("+income+") cannot be generated");
	}
	return tax/52;
	}
	public double calculateIncome(){
		if(Employment.equalsIgnoreCase("hourly")){
		double hourly=40*Rate;
		if(Hours>40){
			hourly+=(Hours-40)*1.5*Rate;
			if(Hours>43){
				hourly+=(Hours-43)*2*Rate;
			}
		}
		return hourly;
		}
		else if(Employment.equalsIgnoreCase("salaried")){
		return Rate;
		}
		else{
			throw new RuntimeException("Invalid Employment Status("+Employment+")");
		}
	}
	public void printPAYE(String start, String end, double sum){
		System.out.println(start+" to "+end);
		System.out.printf("Total PAYE: $%.2f",sum);
		System.out.println("\n");
	}
}

