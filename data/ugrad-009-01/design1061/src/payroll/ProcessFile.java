package payroll;
import java.io.BufferedReader;
import java.io.FileReader;
public class ProcessFile {
	private final static int _numInfoPerLine = 9;
	private String[] _infoLines;
	private String _filePath;
	public String[] getFileLines(){return _infoLines;}
	public ProcessFile(String filePath){
		_filePath = filePath;
		breakFileToLines();
	}
	private int getFileLength(String filePath){
		int count = 0;
		try{
			BufferedReader tempRead = new BufferedReader(new FileReader(filePath));
			while (tempRead.readLine() != null){
				count++;
			}
			tempRead.close();
		}catch (Exception e){
			System.out.println("Error: Could not get file size, file not found");
		}
		return count;
	}
	private String[] recreateList(String[] list, int newCapacity){
		String[] newList = new String[newCapacity];
		for (int i = 0; i < newCapacity; i++){
			newList[i] = list[i];
		}
		return newList;
	}
	private void breakFileToLines(){
		int capacity = getFileLength(_filePath);
		int currentSlot = 0;
		String[] infoLines = new String[capacity];
		try{
			BufferedReader tempRead = new BufferedReader(new FileReader(_filePath));
			for (int i = 0; i < infoLines.length; i++){
				String line = tempRead.readLine();
				if (checkLine(line)){
					infoLines[currentSlot] = line;
					currentSlot++;
				} else {
					capacity--;
				}
			}
			tempRead.close();
		} catch (Exception e){
			System.out.println("Error: Could not get file lines, file not found");
		}
		if (capacity != infoLines.length){
			infoLines = recreateList(infoLines, capacity);
		}
		_infoLines = infoLines;
	}
	private boolean checkLine(String s){
		if (s.equals("")){return false;}
		if (s.charAt(0) == '#'){return false;}
		String[] temp = s.split("\t");
		if (temp.length != _numInfoPerLine){return false;}
		if (checkValuesInLine(temp) == false){return false;}
		return true;
	}
	private boolean checkValuesInLine(String[] line){
		try{
			int tid = Integer.parseInt(line[0]);
			double rate = Double.parseDouble(line[3].substring(1));
			double ytd = Double.parseDouble(line[4].substring(1));
			double hours = Double.parseDouble(line[7]);
			double deduct = Double.parseDouble(line[8].substring(1));
			if (tid < 0 || rate < 0 || ytd < 0 || hours < 0 || deduct < 0){ return false;}
			int y1 = Integer.parseInt(line[5].substring(0,4));
			int y2 = Integer.parseInt(line[6].substring(0,4));
			if (y1 < 0 || y2 < 0){return false;}
			int m1 = Integer.parseInt(line[5].substring(5,7));
			int m2 = Integer.parseInt(line[6].substring(5,7));
			if (m1 > 12 || m2 > 12 || m1 < 1 || m2 < 1){return false;}
			int d1 = Integer.parseInt(line[5].substring(8,10));
			int d2 = Integer.parseInt(line[6].substring(8,10));
			if (d1 < 1 || d2 < 1 || d1 > 31 || d2 > 31){return false;}
			if (!endDateAfterStart(d1, d2, m1, m2, y1, y2)){
				return false;
			}
		} catch (Exception e){ return false; }
		if (line[1].split(",").length < 2){return false;}
		if (!( line[2].toLowerCase().equals("salaried") || line[2].toLowerCase().equals("hourly")) ){return false;}
		if ( !(line[3].substring(0,1).equals("$") && line[4].substring(0,1).equals("$") && line[8].substring(0,1).equals("$")) )
		{return false;}
		return true;
	}
	private boolean endDateAfterStart(int d1, int d2, int m1, int m2, int y1, int y2){
		int d = d2 - d1;
		int m = (m2 - m1)*100;
		int y = (y2 - y1)*10000;
		return ( (y + m + d) > 0);
	}
}
