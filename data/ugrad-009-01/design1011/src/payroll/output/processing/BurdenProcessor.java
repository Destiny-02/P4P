package payroll.output.processing;
public class BurdenProcessor extends AbstractProcessor implements Processor {
	public void process() {
		printDate();
		double total = 0.00;
		for(payroll.file.Record rec : _fileData){
			total += rec.getGross();
		}
		_fileData.get(0).printPeriod();
		System.out.printf("\nTotal Burden: $%.2f", total);
		System.out.println();
	}
}