package payroll;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
public class Payroll {
	private HashMap<String, Employee> _employeedatabase;
	private HashMap<Integer, Payslip> _payslipdatabase;
	public Payroll() {
		_employeedatabase = new HashMap<String, Employee>();
		_payslipdatabase = new HashMap<Integer, Payslip>();
	}
	public String moneyformatter(Double money) {
		Double cash = Double.valueOf(money);
		BigDecimal bd = new BigDecimal(cash);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.toPlainString();
	}
	public void outputPayslips() {
		Date date = new Date();
		SimpleDateFormat formatteddate = new SimpleDateFormat("y-M-d");
		System.out.println(formatteddate.format(date));
		TreeSet<Integer> taxIDs = new TreeSet<Integer>();
		taxIDs.addAll(_payslipdatabase.keySet());
		for (Iterator<Integer> iterator = taxIDs.iterator(); iterator.hasNext();) {
			Integer taxcode = (Integer) iterator.next();
			Payslip slip = _payslipdatabase.get(taxcode);
			Employee person = slip.getEmployee();
			slip.payEmployee();
			System.out.println(taxcode.toString()+". "+ person.getName()+" "+slip.getStartDate()+" to "+slip.getEndDate()+". Gross: $"+this.moneyformatter(person.getGross())+", PAYE: $"+this.moneyformatter(slip.getPAYE())+", Deductions: $"+this.moneyformatter(slip.getDeductions())+" Nett: $"+this.moneyformatter(slip.getNett())+" YTD: $"+this.moneyformatter(person.getYearToDate()));
		}
		return;
		}
	public void outputEmployees() {
		Date date = new Date();
		SimpleDateFormat formatteddate = new SimpleDateFormat("y-M-d");
		System.out.println(formatteddate.format(date));
		TreeSet<String> employeenames = new TreeSet<String>();
		employeenames.addAll(_employeedatabase.keySet());
		for (Iterator<String> iterator = employeenames.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			Employee person = _employeedatabase.get(name);
			String hourly = "Salaried";
			if (person.isHourly()) {
				hourly = "Hourly";
			}
			System.out.println(person.getName()+" ("+person.getTaxID()+") "+hourly+", $"+this.moneyformatter(person.getRate())+" YTD: $"+this.moneyformatter(person.getYearToDate()));
		}
		return;
	}
	public void outputBurden() {
		Date date = new Date();
		SimpleDateFormat formatteddate = new SimpleDateFormat("y-M-d");
		System.out.println(formatteddate.format(date));
		Set<Integer> taxIDs = _payslipdatabase.keySet();
		Double burden = 0.00;
		Payslip slip = null;
		for (Iterator<Integer> iterator = taxIDs.iterator(); iterator.hasNext();) {
			Integer taxcode = (Integer) iterator.next();
			slip = _payslipdatabase.get(taxcode);
			Employee person = slip.getEmployee();
			slip.payEmployee();
			burden += person.getGross();
		}
		System.out.println(slip.getStartDate()+" to "+slip.getEndDate());
		System.out.println("Total Burden: $"+this.moneyformatter(burden));
		return;
	}
	public void outputPAYE() {
		Date date = new Date();
		SimpleDateFormat formatteddate = new SimpleDateFormat("y-M-d");
		System.out.println(formatteddate.format(date));
		Set<Integer> taxIDs = _payslipdatabase.keySet();
		Double tax = 0.00;
		Payslip slip = null;
		for (Iterator<Integer> iterator = taxIDs.iterator(); iterator.hasNext();) {
			Integer taxcode = (Integer) iterator.next();
			slip = _payslipdatabase.get(taxcode);
			slip.payEmployee();
			tax += slip.getPAYE();
		}
		System.out.println(slip.getStartDate()+" to "+slip.getEndDate());
		System.out.println("Total PAYE: $"+this.moneyformatter(tax));
		return;
	}
	public void formatOutput(String format) {
		if (format.equals("Payslips")) {
			this.outputPayslips();
			return;
		}else if (format.equals("Employees")) {
			this.outputEmployees();
			return;
		}else if (format.equals("Burden")) {
			this.outputBurden();
			return;
		}else if (format.equals("PAYE")) {
			this.outputPAYE();
			return;
		}else {
			return;
		}
	}
	public void takeInput(String filename, input dataloader) {
		dataloader.parseinput(filename);
		dataloader.loadData(_payslipdatabase, _employeedatabase);
		return;
	}
	public static void main(String[] args) {
		Fileinput dataloader = new Fileinput();
		Payroll companyPayroll = new Payroll();
		if (args.length != 2) {
			System.out.println("Incorrect input!");
			return;
		}
		companyPayroll.takeInput(args[0], dataloader);
		companyPayroll.formatOutput(args[1]);
		System.out.print("DONE");
		return;
	}
}