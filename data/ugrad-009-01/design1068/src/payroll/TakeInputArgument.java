package payroll;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
public class TakeInputArgument {
	private String[] _args;
	public TakeInputArgument(String[] args){
		_args = args;
	}
	public List<String> processFile() {
		Scanner input = new Scanner(_args[0]);
		List<String> lineListOfEmployees = new LinkedList<String>();
		try {
            File file = new File(input.nextLine());
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                if(!line.contains("#") && !line.equals("")){
                	lineListOfEmployees.add(line);
                }
            }
            input.close();
        } catch (Exception ex) {
        	System.out.println("Can't Find File!");
            ex.printStackTrace();
        }
		return lineListOfEmployees;
	}
	public String findProcess(){
		Scanner input = new Scanner(_args[1]);
		String process = input.nextLine();
		input.close();
		return process;
	}
}
