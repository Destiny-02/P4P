package payroll;
import java.text.DecimalFormat;
public class PayslipsProcessing implements Processing{
	@Override
	public void output(PaymentList paymentList) {
		if(paymentList.isEmpty()){
			System.out.println("Error: There is no one to be paid.(empty input file)");
		}
		else{
			System.out.println(new Today().getDate());
			paymentList.sortInTID();
			getOutput(paymentList);
		}
	}
	private void getOutput(PaymentList paymentList){
		DecimalFormat df = new DecimalFormat("0.00");
		for (int i=0; i<paymentList.getLength(); i++){
			String output = "";
			Payment payment = paymentList.getPayment(i);
			Employee employee = payment.getEmployee();
			output += employee.getTID()+ ". ";
			output += employee.getFullName() + ", ";
			output += "Period: " + employee.getPeriod() + ". ";
			output += "Gross: $" + df.format(payment.getGross()) + ", ";
			output += "PAYE: $" + df.format(payment.getPAYE())+ ", ";
			output += "Deductions: $" + df.format(payment.getDeduction())+ " ";
			output += "Nett: $" + df.format(payment.getNett())+ " ";
			output += "YTD: $" + df.format(payment.calcNewYTD());
			System.out.println(output);
		}
	}
}
