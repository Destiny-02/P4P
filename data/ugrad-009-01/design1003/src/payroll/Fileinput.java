package payroll;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
public class Fileinput implements input {
	private Vector<String[]> _orderedarguments = new Vector<String[]>();
	public boolean parseinput(Object datasource) {
		if(datasource instanceof String){
			File data = new File((String) datasource);
			FileInputStream file;
			String line = new String();
			String[] splitline = new String[9];
			try {
				file = new FileInputStream(data);
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
				e.printStackTrace();
				return false;
			}
			InputStreamReader stream = new InputStreamReader(file);
			BufferedReader reader = new BufferedReader(stream);
			try {
				line = reader.readLine();
			} catch (IOException e) {
				System.out.println("File empty");
				try {
					reader.close();
				} catch (IOException failure) {
					e.printStackTrace();
				}
				return false;
			}
			while (line != null){
				if (line.startsWith("#")) {
					try {
						line =reader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
					continue;
				}
				try {
					splitline = line.split("\t");
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Incorrect number of arguments on line: " + line);
					continue;
				}
				_orderedarguments.add(splitline);
				try {
					line = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}else {
			return false;
		}
	}
	public void loadrecord(String[] record, HashMap<String, Employee> employeedatabase, HashMap<Integer, Payslip> payslipdatabase) {
		Employee person;
		if (record.length!=9) {
			return;
		}
		if (record[2].equals("Salaried")) {
			person = new SalariedEmployee(record[1], Double.parseDouble(record[4].substring(1)), Integer.parseInt(record[0]), Double.parseDouble(record[3].substring(1)));
			employeedatabase.put(record[1], person);
		}
		else {
			person = new HourlyEmployee(record[1], Double.parseDouble(record[4].substring(1)), Integer.parseInt(record[0]), Double.parseDouble(record[3].substring(1)));
			employeedatabase.put(record[1], person);
		}
		Payslip slip = new Payslip(Integer.parseInt(record[0]), person, record[5], record[6], Double.parseDouble(record[7]), Double.parseDouble(record[8].substring(1)));
		payslipdatabase.put(Integer.parseInt(record[0]), slip);
		return;
	}
	@Override
	public void loadData(HashMap<Integer, Payslip> payslipdatabase, HashMap<String, Employee> employeedatabase) {
		for (Iterator<String[]> iterator = _orderedarguments.iterator(); iterator.hasNext();) {
			String[] record = (String[]) iterator.next();
			loadrecord(record, employeedatabase, payslipdatabase);
		}
		return;
	}
}

