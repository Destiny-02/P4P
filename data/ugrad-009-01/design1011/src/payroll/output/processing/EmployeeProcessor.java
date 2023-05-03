package payroll.output.processing;
import java.util.Collections;
import java.util.Comparator;
import payroll.file.Record;
public class EmployeeProcessor extends AbstractProcessor implements Processor {
	public EmployeeProcessor() {
	}
	public void process(){
		Comparator<Record> nameComp = new Record.RecordNameComparator();
		Collections.sort(_fileData, nameComp);
		printDate();
		for(Record rec : _fileData){
			rec.printName(true);
			System.out.print(" (");
			rec.printTaxID();
			System.out.print(") ");
			rec.printSalaried();
			System.out.print(", $");
			rec.printRate();
			System.out.print(" YTD:$");
			rec.printYTD();
			System.out.println();
		}
		return;
	}
}