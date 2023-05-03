package payroll;
import java.text.DecimalFormat;
public class PAYEProcessing implements Processing{
	@Override
	public void output(PaymentList paymentList){
		DecimalFormat df = new DecimalFormat("0.00");
		if(paymentList.isEmpty()){
			System.out.println("Error: There is no one to be paid. (empty input file)");
		}
		else{
			String output = new Today().getDate();
			output = output + "\n" + paymentList.getPayment(0).getEmployee().getPeriod();
			output = output + "\n" + "Total PAYE: $" + df.format(paymentList.calcTotalPAYE());
			System.out.println(output);
		}
	}
}
