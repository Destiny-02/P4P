package payroll;
import java.util.TreeMap;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Map.Entry;
import java.util.*;
public class Payroll {
	public static void main(String[] args) {
		try {
            File file = new File(args[0].toString());
            Scanner input = new Scanner(file);
            TreeMap<Integer, EmployeeRecords> taxIDTreeMap = new TreeMap<Integer, EmployeeRecords>();
            TreeMap<String, EmployeeRecords> familyNameTreeMap = new TreeMap<String, EmployeeRecords>();
            CharSequence cs = "#";
            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (!line.contains(cs) && line.length() > 0) {
                	if(line.contains("$") ) {
                		line = line.replace("$", "");
                	}
                	if(line.contains(",") ) {
                		line = line.replace(", ", "\t");
                	}
                	String[] splitLine = line.split("\\t");
                	if (!taxIDTreeMap.containsKey(Integer.parseInt(splitLine[0])) ) {
                		EmployeeRecords er = new EmployeeRecords(Integer.parseInt(splitLine[0]), splitLine[1], splitLine[2], splitLine[3], Double.parseDouble(splitLine[4]), Double.parseDouble(splitLine[5]), splitLine[6], splitLine[7], Double.parseDouble(splitLine[8]), Double.parseDouble(splitLine[9]) );
                		er.compareStartAndEndDate();
                		taxIDTreeMap.put( Integer.parseInt(splitLine[0]), er);
                		familyNameTreeMap.put( splitLine[1], er);
                	} else {
                		throw new PayrollException("Repeated Tax ID: "+Integer.parseInt(splitLine[0]));
                	}
                }
            }
            input.close();
            if (args[1].equals("Payslips")) {
            	CurrentDate date = new CurrentDate();
            	date.display();
            	Set<Entry<Integer, EmployeeRecords>> set = taxIDTreeMap.entrySet();
            	Iterator<Entry<Integer, EmployeeRecords>> i = set.iterator();
            	while (i.hasNext()) {
            		Entry<Integer, EmployeeRecords> me = i.next();
            		Payslips PSProcessing = new Payslips(taxIDTreeMap.get(me.getKey()));
            		PSProcessing.checkNegative();
            		PSProcessing.display();
				}
			} else if (args[1].equals("Employees")){
            	CurrentDate date = new CurrentDate();
            	date.display();
				Set<Entry<String, EmployeeRecords>> set = familyNameTreeMap.entrySet();
            	Iterator<Entry<String, EmployeeRecords>> i = set.iterator();
            	while (i.hasNext()) {
            		Entry<String, EmployeeRecords> me = i.next();
                	Employees EmployeeProcessing = new Employees(familyNameTreeMap.get(me.getKey()));
                	EmployeeProcessing.checkNegative();
                	EmployeeProcessing.display();
            	}
			} else if (args[1].equals("Burden")){
            	CurrentDate date = new CurrentDate();
            	date.display();
				double sumGross = 0.0;
				Set<Entry<Integer, EmployeeRecords>> set = taxIDTreeMap.entrySet();
            	Iterator<Entry<Integer, EmployeeRecords>> i = set.iterator();
            	while (i.hasNext()) {
            		Entry<Integer, EmployeeRecords> me = i.next();
            		if (taxIDTreeMap.get(me.getKey()).getGross() < 0) {
            			DecimalFormat TwoDP = new DecimalFormat("#.00");
            			throw new PayrollException("Negative Gross: "+TwoDP.format(taxIDTreeMap.get(me.getKey()).getGross())+" for "+taxIDTreeMap.get(me.getKey()).getGivenName()+" "+taxIDTreeMap.get(me.getKey()).getFamilyName() );
            		}
            		sumGross = sumGross + taxIDTreeMap.get(me.getKey()).getGross();
            	}
            	Burden burdenProcessing = new Burden(sumGross, taxIDTreeMap.get(taxIDTreeMap.lastKey()));
            	burdenProcessing.display();
			} else if (args[1].equals("PAYE")){
            	CurrentDate date = new CurrentDate();
            	date.display();
				double sumPAYE = 0.0;
				Set<Entry<Integer, EmployeeRecords>> set = taxIDTreeMap.entrySet();
            	Iterator<Entry<Integer, EmployeeRecords>> i = set.iterator();
            	while (i.hasNext()) {
            		Entry<Integer, EmployeeRecords> me = i.next();
                	Payslips tempPS = new Payslips(taxIDTreeMap.get(me.getKey()));
                	if (tempPS.getPAYE() < 0) {
            			DecimalFormat TwoDP = new DecimalFormat("#.00");
            			throw new PayrollException("Negative PAYE: "+TwoDP.format(tempPS.getPAYE())+" for "+taxIDTreeMap.get(me.getKey()).getGivenName()+" "+taxIDTreeMap.get(me.getKey()).getFamilyName() );
            		}
                	sumPAYE = sumPAYE + tempPS.getPAYE();
            	}
            	PAYE payeProcessing = new PAYE(sumPAYE, taxIDTreeMap.get(taxIDTreeMap.lastKey()));
            	payeProcessing.display();
			} else{
				System.out.println("args[1] must be one of the following:(Case Sensitive) Payslips, Employees, Burden, PAYE");
			}
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
