package payroll;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class InputProcessor {
	public void readFile(CompanyRecords list,String fileName){
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			String[] next;
			while((line = br.readLine()) != null) {
				if(line.isEmpty()||(line.charAt(0)=='#')){
				}else{
					next = lineFilter(line);
					if(next[2].equals("Salaried")){
						try{
							int TID = Integer.parseInt(next[0]);
							double salary = Double.parseDouble(next[3]);
							double YTD = Double.parseDouble(next[4]);
							double hr = Double.parseDouble(next[7]);
							double d = Double.parseDouble(next[8]);
							list.addSalariedEmployee(TID, next[1], salary, YTD,next[5], next[6], hr, d);
						}catch(NumberFormatException e){
							System.out.println("Invalid number.");
						}
					}else if(next[2].equals("Hourly")){
						try{
							int TID = Integer.parseInt(next[0]);
							double rate = Double.parseDouble(next[3]);
							double YTD = Double.parseDouble(next[4]);
							double hr = Double.parseDouble(next[7]);
							double d = Double.parseDouble(next[8]);
							list.addHourlyEmployee(TID, next[1], rate, YTD,next[5], next[6], hr, d);
						}catch(NumberFormatException e){
							System.out.println("Invalid number.");
						}
					}else{
						System.out.println("Invalid Employee. Try again.");
					}
				}
			}
			br.close();
		}catch(FileNotFoundException e){
			System.out.println("Sorry file could not be found.");
		}catch (IOException e) {
			System.out.println("Error reading file.");
		}catch(Exception e){
			System.out.println("shit happened");
		}
	}
	public String[] lineFilter(String line){
		line = line.replaceAll("[$]", "");
		String[] employeeInfo = line.split("\t");
		return employeeInfo;
	}
}
