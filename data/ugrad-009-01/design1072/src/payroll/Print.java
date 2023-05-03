package payroll;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
abstract public class Print {
	public void printDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
	}
	void printPeriod(String Start, String End) {
		System.out.print(Start + " to " + End);
	}
	void printlnPeriod(String Start, String End) {
		System.out.println(Start + " to " + End);
	}
	void printlnMoney(String process, double total) {
		System.out.println("Total " + process + ": $" + String.format("%.2f", total));
	}
	void printMoney(double total) {
		System.out.print("$" + String.format("%.2f", total));
	}
	void printlnMoney(double total) {
		System.out.println("$" + String.format("%.2f", total));
	}
}