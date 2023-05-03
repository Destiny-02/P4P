package payroll;
import java.util.ArrayList;
public class PayslipsProcessing extends ProcessingPayroll {
	private ArrayList<Double> _gross= new ArrayList<Double>();
	private ArrayList<Double> _pAYE= new ArrayList<Double>();
	private ArrayList<Double> _nETT= new ArrayList<Double>();
	private ArrayList<Double> _yTDNew= new ArrayList<Double>();
	private Double _grossTemp;
	private int grossIndex;
	public PayslipsProcessing(ArrayList<Integer> tID, ArrayList<String> familyName, ArrayList<String> givenName,
			ArrayList<String> employment, ArrayList<Double> rate, ArrayList<Double> yTD, ArrayList<String> startDate,
			ArrayList<String> endDate, ArrayList<Double> hours, ArrayList<Double> deductions) {
		super(tID, familyName, givenName, employment, rate, yTD, startDate, endDate, hours, deductions);
	}
	public void PayslipsProcessingMethod() {
		PayslipsProcessing grossObj= new PayslipsProcessing(_tID, _familyName, _givenName, _employment, _rate, _yTD, _startDate, _endDate, _hours, _deductions);
		grossObj.grossProcessing();
		PayslipsProcessing payeObj= new PayslipsProcessing(_tID, _familyName, _givenName, _employment, _rate, _yTD, _startDate, _endDate, _hours, _deductions);
		payeObj.grossProcessing();
		payeObj.PAYEProcessing();
		PayslipsProcessing nettObj= new PayslipsProcessing(_tID, _familyName, _givenName, _employment, _rate, _yTD, _startDate, _endDate, _hours, _deductions);
		nettObj.grossProcessing();
		nettObj.PAYEProcessing();
		nettObj.NETTProcessing();
		PayslipsProcessing ytdObj= new PayslipsProcessing(_tID, _familyName, _givenName, _employment, _rate, _yTD, _startDate, _endDate, _hours, _deductions);
		ytdObj.grossProcessing();
		ytdObj.YTDProcessing();
		PayslipsProcessing printingObj= new PayslipsProcessing(_tID, _familyName, _givenName, _employment, _rate, _yTD, _startDate, _endDate, _hours, _deductions);
		printingObj.grossProcessing();
		printingObj.PAYEProcessing();
		printingObj.NETTProcessing();
		printingObj.YTDProcessing();
		printingObj.printingPayslips();
	}
	public void grossProcessing() {
		for(grossIndex=0; grossIndex<_tID.size(); grossIndex++){
			if (_employment.get(grossIndex).equals("Salaried")){
				salariedProcessing();
			}else{
				hourlyProcessing();
			}
		}
	}
	public void salariedProcessing(){
		_grossTemp=((_rate.get(grossIndex))/52);
		_grossTemp=(double)Math.round(_grossTemp * 100d) / 100d;
		_gross.add(_grossTemp);
	}
	public void hourlyProcessing(){
		if(_hours.get(grossIndex)<=40.0){
			_grossTemp=((_hours.get(grossIndex))*(_rate.get(grossIndex)));
			_grossTemp=(double)Math.round(_grossTemp * 100d) / 100d;
			_gross.add(_grossTemp);
		}
		if(_hours.get(grossIndex)>40.0){
			double hoursTemp = (_hours.get(grossIndex)-40.0);
			if(hoursTemp<=3.0){
				_grossTemp=(hoursTemp*1.5*_rate.get(grossIndex))+(40.0*_rate.get(grossIndex));
				_grossTemp=(double)Math.round(_grossTemp * 100d) / 100d;
				_gross.add(_grossTemp);
			}else{
				_grossTemp=(hoursTemp*2.0*_rate.get(grossIndex))+(40.0*_rate.get(grossIndex));
				_grossTemp=(double)Math.round(_grossTemp * 100d) / 100d;
				_gross.add(_grossTemp);
			}
		}
		if(_grossTemp<_deductions.get(grossIndex)){
			System.out.print("Warning, higher deductions than salary for:");
			System.out.print(_tID.get(grossIndex));
			System.out.print(".");
			System.out.print(_familyName.get(grossIndex));
			System.out.print(_familyName.get(grossIndex));
		}
	}
	public void PAYEProcessing() {
		double tempRate=0.0;
		for(int payeIndex=0; payeIndex<_tID.size(); payeIndex++){
			if (_employment.get(payeIndex).equals("Salaried")){
				tempRate=_rate.get(payeIndex);
				calculatingTax(tempRate);
			}else{
				tempRate=(_gross.get(payeIndex)*52.0);
				calculatingTax(tempRate);
			}
		}
	}
	public void calculatingTax(double tempRate){
		double pAYE=0.0;
		if(tempRate<=14000){
			pAYE = 0.105*tempRate;
		}
		if(tempRate>1400 && tempRate<=48000){
			pAYE = 0.105*14000+0.175*(tempRate-14000);
		}
		if(tempRate>48000 && tempRate<70000){
			pAYE = 0.105*14000+0.175*(48000-14000) + 0.30*(tempRate-48000);
		}
		if(tempRate>70000) {
			pAYE = 0.105*14000+0.175*(48000-14000) + 0.30*(70000-48000)+0.33*(tempRate-70000);
		}
		pAYE=pAYE/52.0;
		pAYE=(double)Math.round(pAYE * 100d) / 100d;
		_pAYE.add(pAYE);
	}
	public void NETTProcessing() {
		double nETTTemp=0.0;
		for(int nettIndex=0; nettIndex<_tID.size(); nettIndex++){
			nETTTemp=_gross.get(nettIndex)-(_pAYE.get(nettIndex)+_deductions.get(nettIndex));
			nETTTemp=(double)Math.round(nETTTemp * 100d) / 100d;
			_nETT.add(nETTTemp);
		}
	}
	public void YTDProcessing() {
		double yTDNewTemp=0.0;
		for(int ytdIndex=0; ytdIndex<_tID.size(); ytdIndex++){
			yTDNewTemp=(_yTD.get(ytdIndex)+_gross.get(ytdIndex));
			yTDNewTemp=(double)Math.round(yTDNewTemp * 100d) / 100d;
			_yTDNew.add(yTDNewTemp);
		}
	}
	public void printingPayslips() {
		int tIDTemp;
		String familyNameTemp;
		String givenNameTemp;
		double grossTemp;
		double pAYETemp;
		double deductionsTemp;
		double nETTTemp;
		double yTDNewTemp;
		for (int i = 0; i<_tID.size(); i++) {
			for (int j =_tID.size()-1; j>i; j--) {
				if (_tID.get(i) > _tID.get(j)) {
					tIDTemp=_tID.get(i);
					_tID.set(i, _tID.get(j));
					_tID.set(j, tIDTemp);
					familyNameTemp=_familyName.get(i);
					_familyName.set(i, _familyName.get(j));
					_familyName.set(j, familyNameTemp);
					givenNameTemp=_givenName.get(i);
					_givenName.set(i, _givenName.get(j));
					_givenName.set(j, givenNameTemp);
					grossTemp=_gross.get(i);
					_gross.set(i, _gross.get(j));
					_gross.set(j, grossTemp);
					pAYETemp=_pAYE.get(i);
					_pAYE.set(i, _pAYE.get(j));
					_pAYE.set(j, pAYETemp);
					deductionsTemp=_deductions.get(i);
					_deductions.set(i, _deductions.get(j));
					_deductions.set(j, deductionsTemp);
					nETTTemp=_nETT.get(i);
					_nETT.set(i, _nETT.get(j));
					_nETT.set(j, nETTTemp);
					yTDNewTemp=_yTDNew.get(i);
					_yTDNew.set(i, _yTDNew.get(j));
					_yTDNew.set(j, yTDNewTemp);
				}
			}
		}
		for (int printingIndex=0;printingIndex<_tID.size();printingIndex++){
			System.out.printf("%d.%s %s, Period: %s to %s.", _tID.get(printingIndex),_givenName.get(printingIndex),_familyName.get(printingIndex),_startDate.get(printingIndex),_endDate.get(printingIndex));
			System.out.printf(" Gross: $");
			System.out.printf("%.2f",_gross.get(printingIndex));
			System.out.printf(",");
			System.out.printf(" PAYE: $");
			System.out.printf("%.2f",_pAYE.get(printingIndex));
			System.out.printf(",");
			System.out.printf(" Deductions: $");
			System.out.printf("%.2f",_deductions.get(printingIndex));
			System.out.printf(" Nett: $");
			System.out.printf("%.2f",_nETT.get(printingIndex));
			System.out.printf(" YTD: $");
			System.out.printf("%.2f",_yTDNew.get(printingIndex));
			if (printingIndex!=_tID.size()-1){
				System.out.print("\n");
			}
			if (printingIndex==_tID.size()-1){
				System.out.println();
				System.out.println();
			}
		}
	}
	public double grossTotalProcessing(PayslipsProcessing grossTotalObj){
		double grossTotal=0.0;
		grossTotalObj.grossProcessing();
		for (int index=0;index<grossTotalObj._gross.size();index++){
			grossTotal=grossTotal+grossTotalObj._gross.get(index);
		}
		return grossTotal;
	}
	public double PAYETotalAccessing(PayslipsProcessing payeTotalObj ){
		double pAYETotal=0.0;
		payeTotalObj.grossProcessing();
		payeTotalObj.PAYEProcessing();
		for (int index1=0;index1<payeTotalObj._pAYE.size();index1++){
			pAYETotal=pAYETotal+payeTotalObj._pAYE.get(index1);
		}
		return pAYETotal;
	}
	public void obtainYTD(PayslipsProcessing obtainYTDObj ,int employeeIndex ){
		obtainYTDObj.grossProcessing();
		obtainYTDObj.PAYEProcessing();
		obtainYTDObj.NETTProcessing();
		obtainYTDObj.YTDProcessing();
		System.out.printf("%.2f",_yTDNew.get(employeeIndex));
		if (employeeIndex!=_gross.size()-1){
			System.out.print("\n");
		}
		if (employeeIndex==_gross.size()-1){
			System.out.println();
			System.out.println();
		}
	}
}