package payroll;
import java.text.DecimalFormat;
import java.util.Map;
import payroll.Command;
import payroll.Employee;
public class PayslipsCommand implements Command {
	DecimalFormat formator = new DecimalFormat("#.00");
	public void operation(Information info){
		int counter = 0;
		int size = info.getEmployeeInfo().size()-1;
	    for ( Map.Entry<Integer, Employee> entry : info.getEmployeeInfo().entrySet() ) {
	    	if (counter == 0) {
	    		entry.getValue().getDate();
	    		counter++;
	    	}
	    	System.out.print(String.valueOf(entry.getValue().getTID()) + ".");
	    	System.out.print(entry.getValue().getNameFirstLast() + ", ");
	    	System.out.print("Period: " + entry.getValue().getPeriod() + ". ");
	    	System.out.print("Gross: " + "$" + entry.getValue().getGross() + ", ");
	    	System.out.print("PAYE: " + "$" + entry.getValue().getPAYE() + ", ");
	    	System.out.print("Deductions: " + "$" + entry.getValue().getDeduction() + " ");
	    	System.out.print("Nett: " + "$" + entry.getValue().getNett() + " ");
	    	if (size == 0) {
	    	System.out.print("YTD: " + "$" + entry.getValue().getNewYTD());
	    	} else {
	    	System.out.println("YTD: " + "$" + entry.getValue().getNewYTD());
	    	}
	    	size--;
	    }
	}
}
