package payroll;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class Input {
	private ArrayList<Employees> _empList = new ArrayList<Employees>();
	private BufferedReader _br;
	private String _arg = "";
	public Input(ArrayList<Employees> empList, String arg) throws FileNotFoundException {
		_empList = empList;
		_arg = arg;
		_br = new BufferedReader(new FileReader(arg));
	}
	public void input() throws IOException {
		String line;
        while ((line = _br.readLine())!= null) {
			if (line.contains("#") || line.equals("")) {
				continue;
			}
        	String[] items = line.split(", |\t|\\$");
 			_empList.add(new Employees(Integer.parseInt(items[0]), items[1], items[2], items[3], Double.parseDouble(items[5]), Double.parseDouble(items[7]),
 					items[8], items[9], Double.parseDouble(items[10]), Double.parseDouble(items[12])));
        }
        _br.close();
	}
}
