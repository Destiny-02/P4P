package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
public class FileData {
	private LinkedList<String> fileLines = new LinkedList<String>();
	public LinkedList<String> readFile(String[] args){
		BufferedReader bufferedReader = null;
		try {
			String line;
			bufferedReader = new BufferedReader(new FileReader(args[0]));
			while ((line = bufferedReader.readLine()) != null) {
				if((line.length() > 0) && (line.charAt(0) != '#')){
					fileLines.add(line);
				}
			}
			bufferedReader.close();
		}
		catch (IOException e) {
			System.out.println("Error when opening file: "+e);
		}
		return fileLines;
	}
}
