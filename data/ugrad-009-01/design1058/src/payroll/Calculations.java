package payroll;
import java.math.BigDecimal;
import java.util.Vector;
public class Calculations {
	public float calcGross(Employee employee) {
		String[] data = employee.getDataToCalc();
		float gross;
		float rate = Float.valueOf(data[1]);
		float hours = Float.valueOf(data[2]);
		if (data[0].equals("Salaried")) {
			gross = round(rate / 52);
		} else {
			if (hours <= 40) {
				gross = round(rate * hours);
			} else if (hours > 40 && hours <= 43) {
				hours = 40 + calcOverTime(hours);
				gross = round(rate * hours);
			} else {
				hours = 40 + calcOverTime(hours);
				gross = round(rate * hours);
			}
		}
		return gross;
	}
	public float calcPAYE(Employee employee) {
		String[] data = employee.getDataToCalc();
		float PAYE;
		float annualIncome;
		if (data[0].equals("Salaried")) {
			annualIncome = Float.valueOf(data[1]);
		} else {
			annualIncome = Float.valueOf(data[3]) * 52;
		}
		float annualTaxed;
		if (annualIncome <= 14000) {
			annualTaxed = round(annualIncome * 0.105f);
			PAYE = round(annualTaxed / 52);
		} else if (annualIncome > 14000 && annualIncome <= 48000) {
			annualTaxed = round((annualIncome - 14000) * 0.175f) + (14000 * 0.105f);
			PAYE = round(annualTaxed / 52);
		} else if (annualIncome > 48000 && annualIncome <= 70000) {
			annualTaxed = round((annualIncome - 48000) * 0.3f) + (34000 * 0.175f) + (14000 * 0.105f);
			PAYE = round(annualTaxed / 52);
		} else {
			annualTaxed = round((annualIncome - 70000) * 0.33f) + (22000 * 0.3f) + (34000 * 0.175f) + (14000 * 0.105f);
			PAYE = round(annualTaxed / 52);
		}
		return PAYE;
	}
	public float calcNett(Employee employee) throws InvalidDataException {
		String[] data = employee.getDataToCalc();
		float nett = round(Float.valueOf(data[3]) - (Float.valueOf(data[6]) + Float.valueOf(data[5])));
		if (nett < 0) {
			throw new InvalidDataException("Negative nett income");
		}
		return nett;
	}
	public float calcNewYTD(Employee employee) {
		String[] data = employee.getDataToCalc();
		return Float.valueOf(data[3]) + Float.valueOf(data[7]);
	}
	public float calcTotalGross(Vector<Employee> list) {
		float totalGross = 0;
		for (int i = 0; i < list.size(); i++) {
			float gross = calcGross(list.get(i));
			totalGross += gross;
		}
		return totalGross;
	}
	public float calcTotalPaye(Vector<Employee> list) {
		float totalPaye = 0;
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setGross(calcGross(list.get(i)));
			totalPaye += calcPAYE(list.get(i));
		}
		return totalPaye;
	}
	private float calcOverTime(float hours) {
		if (hours <= 43) {
			return (hours - 40) * 1.5f;
		} else {
			float overTime = hours - 40;
			return (3 * 1.5f) + ((overTime - 3) * 2);
		}
	}
	private float round(float value) {
		BigDecimal bd = new BigDecimal(Float.toString(value));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}
}
