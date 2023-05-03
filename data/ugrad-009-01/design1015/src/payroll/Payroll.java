package payroll;
import java.util.Vector;
import java.util.List;
public class Payroll{
	public static List<Employee> list = new Vector<Employee>();
	public static void main(String[] args) {
		Input input = new Input();
		String errorMessage = null;
		try {
			list = input.takeDetails(args[0]);
			Employee.checkUniqueTaxIDs(list);
			Output output = new Output(list,args[1]);
			Operations.printDate();
			output.printOutput();
		} catch (Error e){
			errorMessage = e.getMessage();
			System.err.println(errorMessage);
			return;
		} catch (NumFormatError e){
			errorMessage = e.getMessage();
			System.err.println(errorMessage);
			return;
		}
	}
}

