package payroll.output.processing;
import java.util.Collections;
public class PayslipProcessor extends AbstractProcessor implements Processor{
	public void process(){
		Collections.sort(_fileData);
		printDate();
		for(payroll.file.Record rec : _fileData){
			rec.printTaxID();
			System.out.print(". ");
			rec.printName(false);
			System.out.print(", Period: ");
			rec.printPeriod();
			System.out.print(". Gross: $");
			System.out.printf("%.2f", rec.getGross());
			System.out.print(", PAYE: $");
			rec.printPAYE();
			System.out.print(", Deductions: $");
			rec.printDeductions();
			System.out.print(" Nett: $");
			rec.printNett();
			System.out.print(" YTD: $");
			rec.printYTD();
			System.out.println();
		}
	}
}