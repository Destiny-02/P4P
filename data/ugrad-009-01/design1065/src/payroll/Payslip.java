package payroll;
import java.text.DecimalFormat;
import java.util.ArrayList;
public class Payslip {
	private String _enddate;
	private String _startdate;
	private String _name;
	private String _deductions;
	private String _taxID;
	private double _ratedoub;
	private double _hoursdoub;
	private double _deductionsdoub;
	public Payslip(String line){
		String[] parts = line.split("\\t");
		_taxID = parts[0];
		_name = parts[1];
		_startdate = parts[5];
		_enddate =parts[6];
		_deductions=parts[8];
		parts[3] = parts[3].replace("$", "");
		_ratedoub = Double.parseDouble(parts[3]);
		_hoursdoub=Double.parseDouble(parts[7]);
		parts[8] = parts[8].replace("$", "");
		_deductionsdoub=Double.parseDouble(parts[8]);
	}
	public Payslip(){
	}
	public String payslipLine(String line){
		_name = _name.replace(",", "");
		String [] namesplit = _name.split(" ");
		double gross=0;
		Income i = new Income(line);
		gross = i.GrossPay(_ratedoub,_hoursdoub);
		String weekgross = Double.toString(gross);
		DecimalFormat formatter1 = new DecimalFormat("$0.00");
		weekgross = (formatter1.format((Double.parseDouble(weekgross))));
		double tax = 0;
		Payments t = new Payments(line);
		tax = t.PAYEtax();
		String paye =  Double.toString(tax);
		DecimalFormat formatter2 = new DecimalFormat("$0.00");
		paye = (formatter2.format((Double.parseDouble(paye))));
		String ded = _deductions;
		ded = ded.replace("$", "");
		DecimalFormat formatter5 = new DecimalFormat("$0.00");
		ded = (formatter5.format((Double.parseDouble(ded))));
		double net = gross - tax - _deductionsdoub;
		String nett =  Double.toString(net);
		DecimalFormat formatter3 = new DecimalFormat("$0.00");
		nett = (formatter3.format((Double.parseDouble(nett))));
		String ytd = i.YTD(line);
		DecimalFormat formatter4 = new DecimalFormat("$0.00");
		ytd = (formatter4.format((Double.parseDouble(ytd))));
		return(_taxID + ". "+ namesplit[1] + " " + namesplit[0]  + ", Period: " + _startdate + " to " + _enddate +". Gross: " + weekgross + ", PAYE: " + paye + ", Deductions: " + ded + " Nett: " + nett + " YTD: " + ytd );
	}
	public ArrayList<String> organisedlist(ArrayList<Integer> taxID,ArrayList<String> names){
		ArrayList<String> inorder = new ArrayList<String>();
		for(int i = 0; i<taxID.size();i++){
			for(int j = 0; j<names.size();j++){
				String line = names.get(j);
				String[] parts = line.split(" ");
				parts[0] = parts[0].replace(".","");
				if(Integer.parseInt(parts[0])==taxID.get(i)){
					inorder.add(names.get(j));
					break;
				}
			}
		}
		return inorder;
	}
}
