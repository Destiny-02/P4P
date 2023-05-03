package payroll;
public class TaxCalculator {
	public Double calculateTax(Double grosspay) {
		grosspay = grosspay*52;
		Double tax;
		if (grosspay<=14000) {
			tax = (java.lang.Math.rint(grosspay*10.5)/100);
			return tax;
		}
		else {
			tax = (java.lang.Math.rint(14000*10.5)/100);
			grosspay -= 14000;
		}
		if (grosspay<=34000) {
			tax += (java.lang.Math.rint(grosspay*17.5)/100);
			return tax;
		}
		else {
			tax += (java.lang.Math.rint(34000*17.5)/100);
			grosspay -= 34000;
		}
		if (grosspay<=22000) {
			tax += (java.lang.Math.rint(grosspay*30)/100);
			return tax;
		}
		else {
			tax += (java.lang.Math.rint(22000*30)/100);
			grosspay -= 22000;
		}
		tax += (java.lang.Math.rint(grosspay*33)/100);
		return tax;
	}
}
