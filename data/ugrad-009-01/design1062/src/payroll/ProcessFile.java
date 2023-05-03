package payroll;
public class ProcessFile {
	public double[] lineToDoubleArray(String line){
		String[] splitLine = line.split(",|\t");
		String[] DoubleTempA = {splitLine[0],splitLine[4],splitLine[5],splitLine[8],splitLine[9]};
		DoubleTempA[1] = DoubleTempA[1].replace("$","");
		DoubleTempA[2] = DoubleTempA[2].replace("$","");
		DoubleTempA[4] = DoubleTempA[4].replace("$","");
		double[] DoubleArray = new double[5];
		for(int j = 0; j < DoubleTempA.length; j++){
			DoubleArray[j] = Double.parseDouble(DoubleTempA[j]);
		}
		return DoubleArray;
	}
	public String[] lineToStringArray(String line){
		String[] splitLine = line.split(",|\t");
		String[] stringArray = {splitLine[1],splitLine[2],splitLine[3],splitLine[6],splitLine[7]};
		stringArray[1] = stringArray[1].replace(" ","");
		return stringArray;
	}
}
