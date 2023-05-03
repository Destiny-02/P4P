package payroll;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
public class ReadFile {
	private String _path;
	public ReadFile(String file){
		_path = file;
	}
	public void sortFile(ArrayList<Employment> employeeData){
		try{
			BufferedReader textReader = new BufferedReader(new FileReader(_path));
			ProcessFile fileLine = new ProcessFile();
			String newLine;
			while((newLine = textReader.readLine())!= null){
				if(!(newLine.startsWith("#") || newLine.isEmpty())){
					double[] myDoubleArray = fileLine.lineToDoubleArray(newLine);
					String[] myStringArray = fileLine.lineToStringArray(newLine);
					if(myStringArray[2].equals("Salaried")){
						SalariedEm salRate = new SalariedEm();
						double employSalRate = salRate.calculateSalarisedGross(myDoubleArray[1]);
						Employment employee = new Employment(myDoubleArray[0], myStringArray[1], myStringArray[0], myDoubleArray[2], myStringArray[3], myStringArray[4], myDoubleArray[4],employSalRate, myStringArray[2], myDoubleArray[1]);
						employeeData.add(employee);
					}else {
						HourlyEm hrRate = new HourlyEm();
						double employHrRate = hrRate.calculateHourGross(myDoubleArray[1],myDoubleArray[3]);
						Employment employee = new Employment(myDoubleArray[0], myStringArray[1], myStringArray[0], myDoubleArray[2], myStringArray[3], myStringArray[4], myDoubleArray[4],employHrRate, myStringArray[2], myDoubleArray[1]);
						employeeData.add(employee);
					}
				}
			}
			textReader.close();
		}catch(FileNotFoundException e){
			System.out.println("Sorry file is not found :(");
		}catch(IOException e){
			System.out.println("Sorry error reading line :(");
		}catch(Exception e){
			System.out.println("Sorry error unknown :'(");
		}
	}
}
