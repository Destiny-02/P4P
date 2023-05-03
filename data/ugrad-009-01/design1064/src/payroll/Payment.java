package payroll;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.Date;
public class Payment implements Process {
	public Double calculateGross(Double hours, String employee, Double rate) {
		double gross = 0;
		if (employee.equals("Hourly")) {
			if (hours <= 40) {
				gross = (double) Math.round((rate * hours * 100)) / 100;
			} else if (hours <= 43) {
				gross += (double) Math.round((rate * 40 * 100)) / 100;
				hours = hours - 40;
				gross += (double) Math.round((rate * hours * 100 * 1.5)) / 100;
			} else {
				gross += (double) Math.round((rate * 40 * 100)) / 100;
				hours = hours - 40;
				gross += (double) Math.round((rate * 3 * 100 * 1.5)) / 100;
				hours = hours - 3;
				gross += (double) Math.round((rate * hours * 100 * 2.0)) / 100;
			}
			String.format("%.2f", gross);
			return gross;
		} else
			return (double) Math.round((rate / 52 * 100)) / 100;
	}
	@Override
	public void process(ArrayList<EmployeeRecord> records) {
		double burden = 0;
		for (int i = 0; i < records.size(); i++) {
			burden += calculateGross(records.get(i).getHours(), records.get(i).getEmployment(),
					records.get(i).getRate());
		}
		DecimalFormat dformat = new DecimalFormat("#.00");
		Date date = new Date();
		System.out.printf("%tF\n",date);
		System.out.println(records.get(1).getStart()+" to "+ records.get(1).getEnd());
		System.out.println("Total Burden: $" + dformat.format(burden));
	}
}
