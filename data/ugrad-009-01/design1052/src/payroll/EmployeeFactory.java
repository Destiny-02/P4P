package payroll;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class EmployeeFactory {
	public Employee getEmployeeFromString(String line){
		try {
			String[] employeeData = line.split("\t");
			if (employeeData.length != 9){
				throw new RuntimeException(line + " does not have valid formatting");
			}
			for(String dataField:employeeData){
				if (dataField.matches("\\s+")){
					throw new RuntimeException("Blank fields in " + line);
				}
			}
			int taxID = Integer.parseInt(employeeData[0]);
			if(!employeeData[1].contains(", ")){
				throw new RuntimeException("Invalid name format in " + line);
			}
			String[] name = employeeData[1].split(",");
			name[1] = name[1].trim();
			double rate = parseCurrenecy(employeeData[3]);
			double ytd = parseCurrenecy(employeeData[4]);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String startDate = employeeData[5];
			String endDate = employeeData[6];
			if(dateFormat.parse(startDate).after(dateFormat.parse(endDate))){
				throw new RuntimeException("Start date is after end date " + line);
			}
			double hours = Double.parseDouble(employeeData[7]);
			double deduction = parseCurrenecy(employeeData[8]);
			EmployeeType employmentType = EmployeeType.getEmployeeType(employeeData[2]);
			switch (employmentType){
			case salaried:
				return new SalariedEmployee(taxID, name[1], name[0], rate, ytd, startDate, endDate, hours, deduction);
			case hourly:
				return new HourlyEmployee(taxID, name[1], name[0], rate, ytd, startDate, endDate, hours, deduction);
			default :
				throw new RuntimeException(employeeData[2] + " is not a valid employee type");
			}
		} catch (NumberFormatException e){
			System.out.println(e.toString() + " in " + line);
			e.printStackTrace();
			System.exit(0);
		} catch (ParseException e) {
			System.out.println(e.toString() + " in " + line);
			e.printStackTrace();
			System.exit(0);
		}
		throw new RuntimeException(line + " does not have valid formatting");
	}
	private double parseCurrenecy(String money){
		double parsedMoney = 0.0;
		if (!money.startsWith("$")){
			throw new RuntimeException("Invalid money value: " + money);
		} else {
			parsedMoney = Double.parseDouble(money.substring(1));
		}
		if (parsedMoney < 0){
			throw new RuntimeException("Invalid negitive money value: " + money);
		}
		return parsedMoney;
	}
}
