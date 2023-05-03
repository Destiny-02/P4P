
package payroll;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
public class FileIO {
	private Path pathToFile;
	private BufferedReader reader;
	private boolean status = false;
	public FileIO(String fileName) {
		try {
			this.pathToFile = Paths.get(fileName);
		}
		catch(InvalidPathException ipe) {
			ipe.printStackTrace();
			System.err.println(ipe);
			System.exit(1);
		}
	}
	public void findFile(String toFind) {
		if( !(Files.isReadable(pathToFile) && Files.isRegularFile(pathToFile) )) {
			System.err.println("Unable to find regular file or the file is not readable at" + pathToFile.toString());
			System.exit(1);
		}
	}
	public void openFile() {
		if(status) {
			return;
		}
		try {
			reader = Files.newBufferedReader(this.pathToFile);
		}
		catch (IOException IOEx) {
			IOEx.printStackTrace();
			System.err.format("IOException:%s%n", IOEx);
			System.exit(1);
		}
		status = true;
	}
	public String readLineFromFile() {
		if(this.status != true) {
			throw new RuntimeException("File must be opened first before reading.");
		}
		try {
			String buffer;
			while(true) {
				buffer = reader.readLine();
				if( buffer != null && !(buffer.equals("") || buffer.startsWith("#")) ) {
					return buffer;
				}
				else if (buffer == null){
					return buffer;
				}
			}
		} catch (IOException IOEx) {
			IOEx.printStackTrace();
			System.err.println("Unable to read from the file.");
			System.exit(1);
		}
		return null;
	}
	public void closeFile() {
		if(!status) {
			return;
		}
		try {
			reader.close();
		} catch (IOException IOEx) {
			IOEx.printStackTrace();
			System.err.println("Unable to close the file.");
			System.exit(1);
		}
		status = false;
	}
	public boolean getStatus() {
		return this.status;
	}
}
