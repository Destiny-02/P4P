package payroll;
public class BurdenProcess extends Display implements Process {
	private ProcessedRecord records=null;
	private String date=getDate();
	private double totalBurden=0.0;
	public void execute(ProcessedRecord records) {
		this.records = records;
		getTotalBurden();
	}
	public void display() {
		System.out.println(date);
		System.out.println(records.get(0).getStart()+" to "+records.get(0).getEnd());
		System.out.println("Total Burden: $"+getDecimalFormat(totalBurden));
	}
	private void getTotalBurden(){
		for(int i=0; i<records.size(); i++){
			Employee employee = records.get(i);
			totalBurden += employee.getGrossIncome();
		}
		totalBurden = Math.round(totalBurden*100.0)/100.0;
	}
}
