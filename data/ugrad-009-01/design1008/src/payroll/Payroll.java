package payroll;
import java.io.IOException;
import java.util.ArrayList;
public class Payroll {
	public static void main(String[] args) throws IOException {
		ArrayList<Employees> empList = new ArrayList<Employees>();
		Input input = new Input(empList, args[0]);
		Gross gross = new Gross(empList);
		PAYE paye = new PAYE(empList);
		Nett nett = new Nett(empList);
		YearToDate ytd = new YearToDate(empList);
		Display disp = new Display(empList, args[1]);
		input.input();
		gross.calculate();
		paye.calculate();
		nett.calculate();
		ytd.calculate();
		disp.display();
	}
}
        