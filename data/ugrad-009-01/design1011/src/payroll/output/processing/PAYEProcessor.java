package payroll.output.processing;
import payroll.file.Record;
public class PAYEProcessor extends AbstractProcessor implements Processor {
	public void process() {
		printDate();
		_fileData.get(0).printPeriod();
		System.out.println();
		double total = 0;
		for(Record rec : _fileData){
			total += rec.getPAYE();
		}
		System.out.printf("Total PAYE: $%.2f", total);
		System.out.println();
	}
}
