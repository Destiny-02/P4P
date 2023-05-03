package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class InputReader {
	private ArrayList<String> _processedInput = new ArrayList<String>();
	private String _filePath;
	public InputReader(String filePath) {
		_filePath = filePath;
	}
	public ArrayList<String> processInput() throws IOException {
		String line;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(_filePath));
		while ((line = bufferedReader.readLine()) != null) {
			if (!(line.startsWith("#")) && !(line.trim().isEmpty())) {
				_processedInput.add(line);
			}
		}
		bufferedReader.close();
		return _processedInput;
	}
}
