package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Display {
	private List<String> _names;
	private double _total;
	private List<String> _dates;
	public Display(double total, List<String> dates){
		_total = total;
		_dates = dates;
	}
	public Display(List<String> names){
		_names = names;
	}
	public void displayEmployees(ArrayList<String> names){
		currentdate();
		for(int i = 0; i <names.size();i++){
			System.out.println(_names.get(i));
		}
	}
	public void displayPayslip(ArrayList<String> names){
		currentdate();
		for(int i = 0; i <names.size();i++){
			System.out.println(_names.get(i));
		}
	}
	public void currentdate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.print(dateFormat.format(date) + "\n");
	}
	public String PayPeriod(){
		String s =  _dates.get(0) + " to " + _dates.get(1);
		return s;
	}
	public void displayBurden(){
		currentdate();
		System.out.println(PayPeriod());
		System.out.println("Total Burden: $" + _total);
	}
	public void displayPAYE(){
		currentdate();
		System.out.println(PayPeriod());
		System.out.println("Total PAYE: $" + _total);
	}
}
