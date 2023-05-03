
package payroll.employees.salary;
import java.text.DecimalFormat;
import payroll.PayrollException;
public abstract class Pay {
	protected final static DecimalFormat df = new DecimalFormat("#.00");
	public abstract float gross() throws PayrollException;
	public abstract float paye() throws PayrollException;
	protected abstract float deduction();
	protected abstract float ytd() throws PayrollException;
	protected float payeCalculations(float annual) throws PayrollException {
		if (annual < 14000) {
			return (annual * (float)0.105 / 52);
		} else if (annual < 48000) {
			return (float) ((14000*0.105 + (annual-14000)*0.175) / 52);
		} else if (annual < 70000) {
			return (float) ((14000*0.105 + 34000*0.175 + (annual-48000)*0.3) / 52);
		} else {
			return (float) ((14000*0.105 + 34000*0.175 + 22000*0.3 + (annual-70000)*0.33) / 52);
		}
	}
	public float nett() throws PayrollException {
		return gross() - paye() - deduction();
	}
	public String payslip() throws PayrollException {
		float[] arrF = {gross(), paye(), deduction(), nett(), ytd()};
		String[] arrS = new String[5];
		for (int i = 0; i < 5; i++) {
			arrS[i] = df.format(arrF[i]);
		}
		arrS[0] = "Gross: $" + arrS[0] + ", ";
		arrS[1] = "PAYE: $" + arrS[1] + ", ";
		arrS[2] = "Deductions: $" + arrS[2] + " ";
		arrS[3] = "Nett: $" + arrS[3] + " ";
		arrS[4] = "YTD: $" + arrS[4];
		return arrS[0] + arrS[1] + arrS[2] + arrS[3] + arrS[4];
	}
	public abstract String employeeStatement() throws PayrollException;
}
