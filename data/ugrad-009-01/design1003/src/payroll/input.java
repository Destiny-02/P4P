package payroll;
import java.util.HashMap;
public interface input {
	void loadData(HashMap<Integer, Payslip> payslipdatabase, HashMap<String, Employee> employeedatabase);
	boolean parseinput(Object datasource);
}
