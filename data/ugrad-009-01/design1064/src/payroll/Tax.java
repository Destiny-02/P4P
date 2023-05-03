package payroll;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
public class Tax implements Process {
	public Double taxCalc(Double hours, String employee, Double rate) {
		Double tax = 0.00;
		Double salary = 0.00;
		Payment calculatepay = new Payment();
		salary = (double) (Math.round(calculatepay.calculateGross(hours, employee, rate) * 52 * 100) / 100);
		if (salary <= 14000) {
			tax = (double) Math.round((salary * 0.105) * 100) / 100;
		} else if (salary <= 48000) {
			tax += (((14000) * 0.105) * 100) / 100;
			salary -= 14000;
			tax += ((salary * 0.175) * 100) / 100;
		} else if (salary <= 70000) {
			tax += (((14000) * 0.105) * 100) / 100;
			salary -= 14000;
			tax += ((34000* 0.175) * 100) / 100;
			salary -= 34000;
			tax += ((salary * 0.3) * 100) / 100;
		} else {
			tax += ((14000 * 0.105) * 100) / 100;
			salary -= 14000;
			tax += ((34000 * 0.175) * 100) / 100;
			salary -= 34000;
			tax += ((22000 * 0.3) * 100) / 100;
			salary -= 22000;
			tax += ((salary * 0.33) * 100) / 100;
		}
		return (double) Math.round((tax / 52) * 100.00) / 100.00;
	}
	@Override
	public void process(ArrayList<EmployeeRecord> records) {
		Double sum = (Double) 0.00;
		DecimalFormat dformat = new DecimalFormat("#.00");
		for (int i = 0; i < records.size(); i++) {
			sum += (taxCalc(records.get(i).getHours(), records.get(i).getEmployment(), records.get(i).getRate()));
		}
		Date date = new Date();
		System.out.printf("%tF\n",date);
		System.out.println(records.get(1).getStart()+" to "+ records.get(1).getEnd());
		System.out.println("Total PAYE: $" + dformat.format(sum));
	}
}
