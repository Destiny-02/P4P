package payroll;
import java.util.ArrayList;
public class ErrorLog {
	private ArrayList<String[]> _errorList = new ArrayList<String[]>();
	public void errorForRuntime(String currentLine){
		String[] errorMessage = {currentLine, "Not enough/too much data"} ;
		_errorList.add(errorMessage);
	}
	public void errorForFormat(String currentLine){
		String[] errorMessage = {currentLine, "Incorrect data (Invalid Type, Input, or Negative Numbers)"};
		_errorList.add(errorMessage);
	}
	public void errorForTID(String currentLine){
		String[] errorMessage = {currentLine, "Duplicate TID"};
		_errorList.add(errorMessage);
	}
	public void printErrorLog(){
		System.out.println("\n");
		System.out.println("The following lines/employees (in order of appearance) were removed from the process due to an error.");
		System.out.println("Please refer to the reasoning to identify the problem within each line.");
		System.out.println();
		for (String[] errorMessage : _errorList){
			System.out.println("Line: " + errorMessage[0]);
			System.out.println("    Reasoning: " + errorMessage[1]);
		}
	}
}
