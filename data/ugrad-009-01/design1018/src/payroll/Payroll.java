
package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import comparators.FullNameComparator;
import comparators.TIDComparator;
import employee.GeneralEmployee;
import employee.HourlyEmployee;
import employee.SalariedEmployee;
public class Payroll {
	public static void main(String[] args)
	{
		Payroll aPayroll = new Payroll();
		String processType = args[1];
		ArrayList<GeneralEmployee> employeeList = new ArrayList<GeneralEmployee>();
		try
		{
			FileReader inputFileReader = new FileReader(args[0]);
			BufferedReader inputBufferedReader = new BufferedReader(inputFileReader);
			String oneInputLine;
			while ((oneInputLine = inputBufferedReader.readLine()) != null)
			{
				if (oneInputLine.length() == 0 || !Character.isDigit(oneInputLine.charAt(0)))
				{
					continue;
				}
				oneInputLine = oneInputLine.replaceAll("\\$", "");
				String[] record = oneInputLine.split("\t");
				GeneralEmployee anEmployee = null;
				switch(record[2])
				{
					case "Salaried" :
						anEmployee = new SalariedEmployee(Integer.parseInt(record[0]), record[1], record[2], Double.parseDouble(record[3]), Double.parseDouble(record[4]), record[5], record[6], Double.parseDouble(record[7]), Double.parseDouble(record[8]));
						break;
					case "Hourly" :
						anEmployee = new HourlyEmployee(Integer.parseInt(record[0]), record[1], record[2], Double.parseDouble(record[3]), Double.parseDouble(record[4]), record[5], record[6], Double.parseDouble(record[7]), Double.parseDouble(record[8]));
						break;
				}
				employeeList.add(anEmployee);
			}
			inputFileReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		aPayroll.printRecords(processType, employeeList);
	}
	private void printRecords(String processType, ArrayList<GeneralEmployee> employeeList)
	{
		TimeZone timeZone = TimeZone.getTimeZone("Pacific/Auckland");
		DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(timeZone);
		System.out.println(dateFormat.format(new Date()));
		GeneralEmployee tempEmp = employeeList.get(0);
		String dateStart = tempEmp.getDateStart();
		String dateEnd = tempEmp.getDateEnd();
		switch(processType)
		{
			case "Payslips" :
				Collections.sort(employeeList, new TIDComparator());
				for (int i = 0; i < employeeList.size(); i++)
				{
					GeneralEmployee emp = employeeList.get(i);
					emp.printPayslip();
				}
				break;
			case "Employees" :
				Collections.sort(employeeList, new FullNameComparator());
				for (int i = 0; i < employeeList.size(); i++)
				{
					GeneralEmployee emp = employeeList.get(i);
					emp.printEmployeeDetails();
				}
				break;
			case "Burden" :
				double burdenResult = 0;
				System.out.printf("%s to %s\n", dateStart, dateEnd);
				for (int i = 0; i < employeeList.size(); i++)
				{
					GeneralEmployee emp = employeeList.get(i);
					burdenResult += emp.getGross();
				}
				System.out.printf("Total Burden: $%.2f\n", burdenResult);
				break;
			case "PAYE" :
				double payeResult = 0;
				System.out.printf("%s to %s\n", dateStart, dateEnd);
				for (int i = 0; i < employeeList.size(); i++)
				{
					GeneralEmployee emp = employeeList.get(i);
					payeResult += emp.getPaye();
				}
				System.out.printf("Total PAYE: $%.2f\n", payeResult);
				break;
		}
	}
}
