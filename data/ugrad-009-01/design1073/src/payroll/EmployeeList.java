
package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class EmployeeList {
	private String startDate, endDate;
	private List<Employee> employeeList;
	public EmployeeList(String fileName) throws PayRollSystemException{
		String currentLineToRead;
		employeeList = new ArrayList<Employee>();
		try (BufferedReader toRead = new BufferedReader(new FileReader(fileName))){
			while ((currentLineToRead = toRead.readLine()) != null){
				if (currentLineToRead.length() > 0){
					if(!(currentLineToRead.substring(0, 1).equals("#"))){
						createNewEmployee(currentLineToRead);
					}
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	private void createNewEmployee(String fileData) throws PayRollSystemException{
		String[] employeeDetails = fileData.split("\t");
		ValidateEmployeeInput stringCheck = new ValidateEmployeeInput(employeeDetails);
		if(stringCheck.getValidity() == true){
			int id = Integer.parseInt(employeeDetails[0]);
			String[] formattedName = separateNames(employeeDetails[1]);
			String firstName = formattedName[1], lastName = formattedName[0];
			String employmentType = employeeDetails[2];
			double rate = reformattMoneyString(employeeDetails[3]), ytd = reformattMoneyString(employeeDetails[4]);
			startDate = employeeDetails[5]; endDate = employeeDetails[6];
			double hours = Double.parseDouble(employeeDetails[7]), deduction = reformattMoneyString(employeeDetails[8]);
			if(employmentType.equals("Hourly")){
				employeeList.add(new HourlyEmployee(id, firstName, lastName, rate, ytd, hours, deduction));
			} else {
				employeeList.add(new SalariedEmployee(id, firstName, lastName, rate, ytd, hours, deduction));
			}
		} else {
			throw new PayRollSystemException(fileData, stringCheck.getAdditionalErrorDetails());
		}
	}
	private String[] separateNames(String unformatted){
		return unformatted.split(", ");
	}
	private double reformattMoneyString(String unformatted){
		String[] numericValue = unformatted.split("\\$");
		return Double.parseDouble(numericValue[1]);
	}
	private String returnDateRange(){
		return (startDate + " to " + endDate);
	}
	private void printCurrentDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));
	}
	public void printProcessedPAYEInformation(){
		printCurrentDate();
		System.out.println(returnDateRange());
		double runningPAYETotal = 0;
		for(int i = 0; i < employeeList.size(); i++){
			runningPAYETotal += (employeeList.get(i)).getWeeksPAYE();
		}
		System.out.println(String.format("Total PAYE: $%.2f", runningPAYETotal));
	}
	public void printProcessedBurdenInformation(){
		printCurrentDate();
		System.out.println(returnDateRange());
		double runningBurdenTotal = 0;
		for(int i = 0; i < employeeList.size(); i++){
			runningBurdenTotal += (employeeList.get(i)).getWeeksGross();
		}
		System.out.println(String.format("Total Burden: $%.2f", runningBurdenTotal));
	}
	public void printPayslip(){
		printCurrentDate();
		int[] idArray = new int[employeeList.size()];
		for(int i = 0; i < employeeList.size(); i++){
			idArray[i] = (employeeList.get(i)).getID();
		}
		idArray = sortArray(idArray);
		for(int i = 0; i < idArray.length; i++){
			printPayslipData(employeeList.get(idArray[i]));
		}
	}
	private void printPayslipData(Employee employee){
		System.out.print(employee.getID() + ". ");
		System.out.print(employee.getFirstName() + " " + employee.getLastName() + ", ");
		System.out.print("Period: " + returnDateRange() + ". ");
		System.out.print(String.format("Gross: $%.2f, ", employee.getWeeksGross()));
		System.out.print(String.format("PAYE: $%.2f", employee.getWeeksPAYE()));
		System.out.print(String.format(", Deductions: $%.2f", employee.getWeeklyDeductions()));
		System.out.print(String.format(" Nett: $%.2f", employee.getNettIncome()));
		System.out.print(String.format(" YTD: $%.2f\n", employee.getUpdatedYTD()));
	}
	public void printEmployees(){
		printCurrentDate();
		String[] namesArray = new String[employeeList.size()];
		int[] sortedByFirstNameIndices, sortedByLastNameIndices, finalModifiedIndices = new int[namesArray.length];
		for(int i = 0; i < namesArray.length; i++){
			namesArray[i] = (employeeList.get(i)).getFirstName();
		}
		sortedByFirstNameIndices = sortArray(namesArray);
		for(int i = 0; i < namesArray.length; i++){
			namesArray[i] = (employeeList.get(sortedByFirstNameIndices[i])).getLastName();
		}
		sortedByLastNameIndices = sortArray(namesArray);
		for(int i = 0; i < namesArray.length; i++){
			finalModifiedIndices[i] = sortedByFirstNameIndices[sortedByLastNameIndices[i]];
		}
		for(int i = 0; i < namesArray.length; i++){
			printEmployeesData(employeeList.get(finalModifiedIndices[i]));
		}
	}
	private void printEmployeesData(Employee employee){
		System.out.print(employee.getLastName() + ", " + employee.getFirstName());
		System.out.print(" (" + employee.getID() + ") ");
		System.out.print(employee.getEmploymentType());
		System.out.print(String.format(", $%.2f", employee.getRate()));
		System.out.print(String.format(" YTD:$%.2f\n", employee.getUpdatedYTD()));
	}
	private int[] sortArray(String[] valuesToSort){
		String tempValue;
		int tempIndices;
		int[] arrayToReturn = new int[valuesToSort.length];
		for(int i = 0; i < arrayToReturn.length; i++)	{arrayToReturn[i] = i;}
		for(int i = 0; i < arrayToReturn.length - 1; i++){
			for(int j = 0; j < arrayToReturn.length - 1; j++){
				if (valuesToSort[j].compareToIgnoreCase(valuesToSort[j+1]) > 0){
					tempValue = valuesToSort[j];
					valuesToSort[j] = valuesToSort[j+1];
					valuesToSort[j+1] = tempValue;
					tempIndices = arrayToReturn[j];
					arrayToReturn[j] = arrayToReturn[j+1];
					arrayToReturn[j+1] = tempIndices;
				}
			}
		}
		return arrayToReturn;
	}
	private int[] sortArray(int[] valuesToSort){
		int tempValue, tempIndices;
		int[] arrayToReturn = new int[valuesToSort.length];
		for(int i = 0; i < arrayToReturn.length; i++)	{arrayToReturn[i] = i;}
		for(int i = 0; i < arrayToReturn.length - 1; i++){
			for(int j = 0; j < arrayToReturn.length - 1; j++){
				if (valuesToSort[j] > valuesToSort[j+1]){
					tempValue = valuesToSort[j];
					valuesToSort[j] = valuesToSort[j+1];
					valuesToSort[j+1] = tempValue;
					tempIndices = arrayToReturn[j];
					arrayToReturn[j] = arrayToReturn[j+1];
					arrayToReturn[j+1] = tempIndices;
				}
			}
		}
		return arrayToReturn;
	}
}
