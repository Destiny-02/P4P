package payroll;
public class PayeProcess extends Display implements Process {
	private ProcessedRecord records = null;
	private String date = getDate();
	private double totalPaye = 0.0;
	public void execute(ProcessedRecord records) {
		this.records = records;
		getTotalPaye();
	}
	public void display() {
		System.out.println(date);
		System.out.println(records.get(0).getStart()+" to "+records.get(0).getEnd());
		System.out.println("Total PAYE: $"+getDecimalFormat(totalPaye));
	}
	private void getTotalPaye(){
		for(int i=0; i<records.size(); i++){
			Employee employee = records.get(i);
			totalPaye += employee.getPaye();
		}
		totalPaye = Math.round(totalPaye*100.0)/100.0;
	}
}
