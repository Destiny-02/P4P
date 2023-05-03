package payroll;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class InputProcessor {
	public void addEmployeeToListFromFile(String fileName, EmployeeList list)throws PayrollUserException{
		try {
			FileReader fileReader = new FileReader (fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String inputLine;
			while ((inputLine = bufferedReader.readLine()) != null){
				if (inputLine.isEmpty()){
				}
				else if(inputLine.trim().equals("") || inputLine.trim().equals("\n")){
				}
				else if (inputLine.substring(0, 1).equals("#")){
				}
				else{
					String[] processedInput = processLine(inputLine);
					addEmployeeToList(processedInput,list);
				}
			}
			bufferedReader.close();
			list.checkDuplicateTID();
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: Unable to open file '" + fileName + "'");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: error when reading/writing file.");
		}
	}
	private String[] processLine(String input)throws PayrollUserException{
		String[] splitted = input.split("\\t");
		if(splitted.length!=9){
			String msg="\nError: Missing data in the following input line. Need exactly 9 inputs, Has: " + splitted.length +" inputs.\n";
			throw new PayrollUserException(msg + input);
		}
		String employeeinfo = "(For employee: " + "TID: " + splitted[0] + " Name: " + splitted[1] + ")";
		checkNameFormat(employeeinfo,splitted[1]);
		checkDollarFormat(employeeinfo, splitted[3],splitted[4],splitted[8]);
		checkDateFormat(employeeinfo,splitted[5]);
		checkDateFormat(employeeinfo,splitted[6]);
		checkEmploymentType(input, splitted[2]);
		splitted[3]=splitted[3].substring(1);
		splitted[4]=splitted[4].substring(1);
		splitted[8]=splitted[8].substring(1);
		checkNegativeValues(input, splitted[3],splitted[4],splitted[7],splitted[8]);
		checkDateOrder(input, splitted[5], splitted[6]);
		return splitted;
	}
	private boolean isSalaried(String[] processedInput){
		return processedInput[2].equals("Salaried");
	}
	private void addEmployeeToList(String[] processedInput, EmployeeList list){
		Employee employee;
		if (isSalaried(processedInput)){
			employee = new SalariedEmployee(processedInput);
		}
		else {
			employee = new HourlyEmployee(processedInput);
		}
		list.add(employee);
	}
	private void checkDollarFormat(String employeeinfo, String rate, String YTD, String deduction)throws PayrollUserException{
		String msg = "\nError: Missing $ sign in the column: ";
		if(!rate.substring(0,1).equals("$")){
			throw new PayrollUserException(msg + "Rate. " + employeeinfo );
		}
		else if (!YTD.substring(0,1).equals("$")){
			throw new PayrollUserException(msg + "YTD. " + employeeinfo );
		}
		else if(!deduction.substring(0,1).equals("$")){
			throw new PayrollUserException(msg + "Deduction. " + employeeinfo );
		}
	}
	private void checkDateFormat(String employeeinfo, String date)throws PayrollUserException{
		String msg= "\n Error: Wrong Date Format. Date must be in format:yyyy-mm-dd. \n";
		if(date.length()!=10||!date.substring(7,8).equals("-")||!date.substring(4,5).equals("-")){
			throw new PayrollUserException(msg + employeeinfo);
		}
	}
	private void checkNameFormat(String employeeinfo, String name)throws PayrollUserException{
		String msg="\n Error: Wrong Name Format. Name must be in format: Family, Given.\n";
		if(name.split(",").length!=2){
			throw new PayrollUserException(msg + employeeinfo);
		}
	}
	private void checkNegativeValues(String line, String rate, String YTD, String hours,String deduction) throws PayrollUserException {
		double value1=Double.parseDouble(rate);
		double value2=Double.parseDouble(YTD);
		double value3=Double.parseDouble(hours);
		double value4=Double.parseDouble(deduction);
		if(value1<0|value2<0|value3<0|value4<0){
			String msg="Error: At least one negative value detected in the line: \n";
			throw new PayrollUserException(msg+line);
		}
	}
	private void checkDateOrder(String line, String start, String end) throws PayrollUserException{
		start=start.replace("-", "");
		end=end.replace("-", "");
		int startValue = Integer.parseInt(start);
		int endValue = Integer.parseInt(end);
		if (startValue>endValue){
			String msg="\nError: Start date before end date. Error in the following line: \n";
			throw new PayrollUserException(msg+line);
		}
	}
	private void checkEmploymentType(String line, String type) throws PayrollUserException{
		if (type.equals("Salaried")||type.equals("Hourly")){
		}
		else{
			String msg="\nError: Unidentified employment type, the type must be either Salaried or Hourly. Found: "+ type + " . In line: \n";
			throw new PayrollUserException(msg+line);
		}
	}
}
