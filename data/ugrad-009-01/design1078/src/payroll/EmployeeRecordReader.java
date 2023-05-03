package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import payroll.Employee.EmployeeType;
public class EmployeeRecordReader {
	private int _lineCount = 0;
	public ArrayList<Employee> readRecords(String fileName, ArrayList<Employee> employeeList) {
		FileReader fileReader = null;
		BufferedReader buffer = null;
		String currentLine;
		try {
			fileReader = new FileReader(fileName);
			buffer = new BufferedReader(fileReader);
			while((currentLine = buffer.readLine()) != null){
				_lineCount++;
				if ((currentLine.equals("")) || (currentLine.substring(0, 1).equals("#")) || currentLine.substring(0, 1).equals(" ")){
					continue;
				} else {
					StringTokenizer st = new StringTokenizer(currentLine, "\t");
					if (st.countTokens() != 9){
						buffer.close();
						throw new InvalidEmployeeRecordException("Invalid Employee Record: Missing Details, line "+ _lineCount);
					}
					while (st.hasMoreTokens()){
						int tid = Integer.parseInt(st.nextToken());
						String name = st.nextToken();
						String type = st.nextToken();
						EmployeeType employeeType = (type.toUpperCase().equals("SALARIED")) ? EmployeeType.SALARIED : EmployeeType.HOURLY;
						double rate = Double.parseDouble(st.nextToken().replace("$", ""));
						double ytd = Double.parseDouble(st.nextToken().replace("$", ""));
						String start = st.nextToken();
						String end = st.nextToken();
						double hours = Double.parseDouble(st.nextToken());
						double deductions = Double.parseDouble(st.nextToken().replace("$", ""));
						if (employeeList.size() > 1){
							if ((!employeeList.get(0).isUniqueTID(tid, employeeList))){
								buffer.close();
								throw new InvalidEmployeeRecordException("Invalid Employee Record: Duplicate TID number ("+ tid +"), line "+ _lineCount);
							}
						}
						checkName(name);
						checkType(type);
						checkTimePeriod(start, end);
						checkNumber(tid);
						checkNumber(rate);
						checkNumber(ytd);
						checkNumber(hours);
						checkNumber(deductions);
						employeeList.add(new Employee(tid, name, employeeType, rate, ytd, start, end, hours, deductions));
					}
				}
			}
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeList;
	}
	private void checkName(String name) throws InvalidEmployeeRecordException{
		String[] splitName = name.split(",");
		if (splitName.length != 2){
			throw new InvalidEmployeeRecordException("Invalid Employee Name: \"" + name + "\", line "+ _lineCount);
		}
	}
	private void checkType(String type) throws InvalidEmployeeRecordException {
		if (!(type.toUpperCase().equals("SALARIED") | type.toUpperCase().equals("HOURLY"))){
			throw new InvalidEmployeeRecordException("Invalid Employee Type: \"" + type + "\", line " + _lineCount +
					". Must be either Salaried or Hourly.");
		}
	}
	private void checkTimePeriod(String startDate, String endDate) throws InvalidEmployeeRecordException{
		String[] start = startDate.split("-");
		String[] end = endDate.split("-");
		if (start.length != 3 | end.length != 3){
			throw new InvalidEmployeeRecordException("Invalid Time Period: Dates have incorrect format, line "+ _lineCount);
		}
		endDate = endDate.replaceAll("-", "");
		startDate = startDate.replaceAll("-", "");
		if (Integer.parseInt(endDate) < Integer.parseInt(startDate)){
			throw new InvalidEmployeeRecordException("Invalid Time Period: End date before Start date, line "+ _lineCount);
		}
	}
	private void checkNumber(double value) throws InvalidEmployeeRecordException{
		if (value < 0){
			throw new InvalidEmployeeRecordException("Invalid Employee Record: Negative value on line, "+ _lineCount);
		}
	}
}