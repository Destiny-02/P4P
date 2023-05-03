package payroll;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class fileInfo {
	public static String[] readFile(String fileName) {
		int _lineNumber = 0;
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				String[] lineInfo = line.split("");
				if(!lineInfo[0].equals("#") && !lineInfo[0].equals("")){
					_lineNumber++;
				}
			}
			bufferedReader.close();
			String[] lines = new String[_lineNumber];
			FileReader fileReaderB = new FileReader(fileName);
			BufferedReader bufferedReaderB = new BufferedReader(fileReaderB);
			int i = 0;
			while((line = bufferedReaderB.readLine()) != null) {
				String[] lineInfo = line.split("");
				if(!lineInfo[0].equals("#") && !lineInfo[0].equals("")){
					lines[i] = line;
					i++;
				}
			}
			bufferedReaderB.close();
			return lines;
		}
		catch(FileNotFoundException ex) {
			System.out.println(
					"Can't open: " + fileName );
		}
		catch(IOException ex) {
			System.out.println(	"Can't read: " 	+ fileName );
		}
		return null;
	}
}

