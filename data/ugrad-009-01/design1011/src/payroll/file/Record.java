package payroll.file;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import payroll.exceptions.PayrollException;
public class Record implements Comparable<Record> {
	private final String[] _data;
	private final PayCalculator calc = new PayCalculator();
	double _gross;
	double _PAYE;
	double _nett;
	public Record(String payFileLine) throws PayrollException{
		_data = payFileLine.split("\t");
		if(_data.length != 9){
			throw new PayrollException("Error: record does not contain correct number of fields: "
										 + _data[0]);
		}
		if(_data[1].split(", ").length != 2){
			throw new PayrollException("Error: name formatted incorrectly: " + _data[1]);
		}
		int[] moneyIndices = new int[] {3,4,8};
		int[] numberIndices = new int[] {0, 3, 4, 7, 8};
		for(int i: moneyIndices){
			if (!(_data[i].charAt(0) == ('$'))){
				throw new PayrollException("Error: no \"$\" before " + _data[i]);
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = df.parse(_data[5], new ParsePosition(0));
		Date date2 = df.parse(_data[6], new ParsePosition(0));
		if(!date1.before(date2)){
			throw new PayrollException("Error: dates not valid \"" + _data[5] + " to " + _data[6] + "\"");
		}
		if(!(_data[2].equals("Salaried")) && !(_data[2]).equals("Hourly")){
			throw new PayrollException("Error: Type of employee not valid: " + _data[2]);
		}
		for(int i : moneyIndices){
			_data[i] = removeDolSign(_data[i]);
		}
		for(int i : numberIndices){
			if(Float.parseFloat(_data[i]) < 0){
				throw new PayrollException("Error: negative number where should not be: " + _data[i]);
			}
		}
	}
	private String removeDolSign(String moneyAmount){
		if(moneyAmount.charAt(0) == '$'){
			return moneyAmount.substring(1);
		}else{
			return moneyAmount;
		}
	}
	public void printName(boolean famFirst){
		if(famFirst){
			System.out.print(_data[1]);
		}else{
			String[] name = _data[1].split(", ");
			System.out.print(name[1] + " " + name[0]);
		}
	}
	public void printPeriod(){
		System.out.print(_data[5] + " to " + _data[6]);
	}
	public void printYTD(){
		double YTD = Double.parseDouble(_data[4]);
		System.out.printf("%.2f", YTD);
	}
	public void printNett(){
		System.out.printf("%.2f", _nett);
	}
	public void printPAYE(){
		System.out.printf("%.2f", _PAYE);
	}
	public void printDeductions(){
		double ded = Double.parseDouble(_data[8]);
		System.out.printf("%.2f", ded);
	}
	public void printTaxID(){
		System.out.print(_data[0]);
	}
	public void printSalaried(){
		System.out.print(_data[2]);
	}
	public void printRate(){
		double rate = Double.parseDouble(_data[3]);
		System.out.printf("%.2f", rate);
	}
	public double getPAYE(){
		return _PAYE;
	}
	public double getGross(){
		return _gross;
	}
	public void updatePayFields() throws PayrollException{
		boolean salaried;
		if(_data[2].equals("Salaried")){
			salaried = true;
		}else{
			salaried = false;
		}
		double rate = Double.parseDouble(_data[3]);
		double YTD = Double.parseDouble(_data[4]);
		double hours = Double.parseDouble(_data[7]);
		double deductions = Double.parseDouble(_data[8]);
		_gross = calc.calcGross(hours, rate, salaried);
		_PAYE = calc.calcPAYE(_gross, rate, salaried);
		_nett = calc.calcNett(_data[1], _gross, _PAYE, deductions);
		YTD = calc.updateYTD(YTD, _gross);
		_data[4] = "" + YTD;
	}
	public static class RecordNameComparator implements java.util.Comparator<Record>{
		public int compare(Record rec1, Record rec2) {
			return rec1._data[1].compareToIgnoreCase(rec2._data[1]);
		}
	}
	public int compareTo(Record rec) {
		if (Integer.parseInt(this._data[0]) < Integer.parseInt(rec._data[0])) {
			return -1;
		} else if (Integer.parseInt(this._data[0]) > Integer.parseInt(rec._data[0])) {
			return 1;
		} else {
			return 0;
		}
	}
}