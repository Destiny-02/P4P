package payroll;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
public class FileReader {
	private BufferedReader _reader;
	private long _lineNo;
	{
	_lineNo = 0;
	}
	public FileReader(String arg0) throws NoSuchFileException{
		String path = arg0;
		if(!arg0.matches("^([A-Za-z]\\:\\\\).*$")){
			path=System.getProperty("user.dir")+((arg0.startsWith("\\")||arg0.startsWith("/"))?"":"\\")+arg0.replaceAll("/", "\\\\");
		}
		try{
		_reader=Files.newBufferedReader(Paths.get(arg0));
		}catch(NoSuchFileException ex2){
			throw ex2;
		}catch(IOException ex){
			System.err.format("IOException: %s%n", ex);
		}
	}
	public String getNextLine(){
		try{
		_lineNo++;
		return _reader.readLine();
		}catch(IOException e){
		_lineNo--;
		return null;
		}
	}
	public long getLineNumber(){
		return _lineNo;
	}
}
