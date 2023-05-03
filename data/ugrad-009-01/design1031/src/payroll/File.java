package payroll;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.String;
public class File {
	FileReader file;
	public File(String fileInName){
		try {
			file = new FileReader(fileInName);
		} catch (FileNotFoundException e) {
			System.err.println("File not detected");
			e.printStackTrace();
		}
	}
}
