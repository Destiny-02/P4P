package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
public class Processing {
	ArrayList<Employee> _al = new ArrayList<Employee>();
	public Processing(ArrayList<Employee> al) {
		_al.addAll(al);
	}
	public void burdenProcess() {
		double sumBurd = 0;
		for (int i = 0; i < _al.size(); i++) {
			sumBurd += _al.get(i).getGross();
		}
		printCurrentDate();
	    _al.get(0).printDate();
	    System.out.printf("Total Burden: $%.2f\n", sumBurd);
	}
	public void payeProcess() {
		double sumPaye = 0;
		for (int i = 0; i < _al.size(); i++) {
			_al.get(i).getGross();
			sumPaye += _al.get(i).getPaye();
		}
		printCurrentDate();
		_al.get(0).printDate();
	    System.out.printf("Total PAYE: $%.2f\n", sumPaye);
	}
	public void employeeProcess() {
		Collections.sort(_al, new SortByNameComparator());
		checkForDuplicates();
		printCurrentDate();
		for (int j = 0; j < _al.size(); j++) {
			_al.get(j).printEmployeeLine();
		}
	}
	public void payslipProcess() {
		checkForDuplicates();
		Collections.sort(_al, new SortByTIDComparator());
		printCurrentDate();
		for (int i = 0; i < _al.size(); i++) {
			_al.get(i).printPayslipLine();
		}
	}
	private void printCurrentDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    Date dateobj = new Date();
	    System.out.println(df.format(dateobj));
	}
	private void checkForDuplicates() {
		ArrayList<Integer> tid = new ArrayList<Integer>();
		for (int i = 0; i < _al.size(); i++) {
			tid.add(_al.get(i).getTID());
		}
		Set<Integer> uniques = new HashSet<Integer>();
		for (int i : tid) {
			if (uniques.contains(i)) {
				throw new ProcessingException("Invalid input(s). Data contains duplicate TID values");
			}
			uniques.add(i);
		}
	}
}
