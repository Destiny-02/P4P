package payroll;
import java.text.DecimalFormat;
public class BurdenProcessing implements Processing{
	DecimalFormat df = new DecimalFormat("0.00");
	@Override
	public void output(PaymentList paymentList){
		if(paymentList.isEmpty()){
			System.out.println("Error: There is no one to be paid. (empty input file)");
		}
		else{
			String output = new Today().getDate();
			output = output + "\n" + paymentList.getPayment(0).getEmployee().getPeriod();
			output = output + "\n" + "Total Burden: $" + df.format(paymentList.calcTotalGross());
			System.out.println(output);
		}
	}
}
