package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
public class Input {
	private String[] _args;
	public Input(String[] args){
		_args = args;
	}
	public List<String> readInput() throws IOException{
		final List<String> _information = new Vector<String>();
		BufferedReader br = new BufferedReader(new FileReader(_args[0]));
		String line;
		Mainloop:while ((line = br.readLine()) != null) {
			if((line.trim().length() > 0)){
				while ((line.charAt(0) == '#')){
					line = br.readLine();
					if (line == null){
						break Mainloop;
					}
				}
				StringTokenizer tokenizer = new StringTokenizer(line, "\t");
				while(tokenizer.hasMoreTokens()){
					String token = tokenizer.nextToken();
					_information.add(token);
				}
			}
		}
		br.close();
		return _information;
	}
}
