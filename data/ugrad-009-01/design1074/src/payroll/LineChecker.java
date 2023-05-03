package payroll;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class LineChecker {
	private String str;
	private int lineNum;
	private String[] line = {""};
	private boolean invalidTID;
	private boolean invalidHours;
	private boolean invalidNumericData;
	private boolean incorrectUseOfDecimalPoint;
	private boolean multipleDPOccurrence;
	private boolean multipleDSOccurrence;
	private boolean startWithWhitespace;
	private boolean lineCheckingFails;
	private final int[] numericValueIndex = {0, 3, 4, 7, 8};
	private final String[] itemName = {"TID", "Name", "Employment", "Rate", "YTD", "Start", "End", "Hours", "Deduction"};
	private final Pattern pattern =
			Pattern.compile("[a-zA-Z_\\\\'\"\\s]|,|:|;|-|!|@|#|%|&|\\[|\\]|[\\`\\~\\+\\*\\=\\{\\}\\?\\/\\<\\>\\^]");
	private Matcher matcher = null;
	public LineChecker(String str, int lineNum){
		this.str = str;
		this.lineNum = lineNum;
		this.invalidTID = false;
		this.invalidHours = false;
		this.invalidNumericData = false;
		this.incorrectUseOfDecimalPoint = false;
		this.multipleDPOccurrence = false;
		this.multipleDSOccurrence = false;
		this.startWithWhitespace = false;
		this.lineCheckingFails = false;
	}
	public boolean isInvalidLine(){
		if (lineCheckFails()){
			return true;
		}
		if (fieldsCheckFails()){
			return true;
		}
		return false;
	}
	private boolean lineCheckFails(){
		if (str.matches("^\\s+.+$")){
			System.out.println("Warning at line No." + lineNum + " : A valid line from the input file should not "
					+ "start with any whitespace.\n" + "Program will now attempt to obtain the rest of the line after the"
							+ " whitespace(s).\n");
			startWithWhitespace = true;
			line = str.split("^\\s+");
			line = line[1].split("\t+");
		}
		if (!(startWithWhitespace)){
			line = str.split("\t+");
		}
		if (line.length != 9){
			System.out.println("Warning: Some informations (fields) are lost for the empoyee at "
					+ "the line No." + lineNum + ".\n"
					+ "Otherwise please check all input data given in appropriate format.\n"
					+ "(e.g. no empty field(s) of information -- should be exactly 9 fields in total for each employee)"
					+ "This line will be skipped.\n");
			return true;
		}
		return false;
	}
	private boolean fieldsCheckFails(){
		if ((line[0].contains(".")) || (line[0].contains("$"))){
			System.out.println("Warning: Error occur at line No." + lineNum
					+ " and the field of " + itemName[0] +" (field No." + 1 + ").\n"
					+ "TID number should not contain any '.' or '$' character(s).\n"
					+ "This line will be skipped.\n");
			invalidTID = true;
		}
		if (line[7].contains("$")){
			System.out.println("Warning: Error occur at line No." + lineNum
					+ " and the field of " + itemName[7] +" (field No." + 8 + ").\n"
					+ "Hours value should not contain any '$' character(s).\n"
					+ "This line will be skipped.\n");
			invalidHours = true;
		}
		for (int i : numericValueIndex){
			matcher = pattern.matcher(line[i]);
			if ((line[i].indexOf("$") > 0)){
				System.out.println("Warning: Error occur at line No." + lineNum
						+ " and the field of " + itemName[i] +" (field No." + (i+1) + ").\n"
						+ "The use of character '$' in this string is incorrect.\n"
						+ "Program only accepts '$' appears in the first position of any string representing a numeric"
						+ " value (excluding TID and Hours).\n"
						+ "PLEASE ensure this field has an valid value.\n"
						+ "This line will be skipped.\n");
				invalidNumericData = true;
			}else if ((line[i].indexOf("$") == 0)){
				if (line[i].charAt(1) == '.'){
					System.out.println("Warning: Error occur at line No." + lineNum
							+ " and the field of " + itemName[i] +" (field No." + (i+1) + ").\n"
							+ "incorrect use of decimal point.\n"
							+ "ocurrence of '.' must not be at the beginning of any numeric sequence.\n"
							+ "This line will be skipped.\n");
					incorrectUseOfDecimalPoint = true;
				}
			}else{
				if (line[i].charAt(0) == '.'){
					System.out.println("Warning: Error occur at line No." + lineNum
							+ " and the field of " + itemName[i] +" (field No." + (i+1) + ").\n"
							+ "incorrect use of decimal point.\n"
							+ "ocurrence of '.' must not be at the beginning of any numeric sequence.\n"
							+ "This line will be skipped.\n");
					incorrectUseOfDecimalPoint = true;
				}
			}
			if (i != 0){
				int charCountDP = 0;
				int charCountDS = 0;
		        for(char ch: line[i].toCharArray()){
		            if(ch == '.'){
		                charCountDP++;
		            }
		            if(ch == '$'){
		                charCountDS++;
		            }
		        }
		        if (charCountDP >= 2){
		        	System.out.println("Warning: Error occur at line No." + lineNum
							+ " and the field of " + itemName[i] +" (field No." + (i+1) + ").\n"
							+ "Multiple occurrence of decimal points detected inside the string.\n"
							+ "Please ensure numeric value has a valid format.\n"
							+ "This line will be skipped.\n");
		        	multipleDPOccurrence = true;
		        }
		        if (charCountDS >= 2){
		        	System.out.println("Warning: Error occur at line No." + lineNum
							+ " and the field of " + itemName[i] +" (field No." + (i+1) + ").\n"
							+ "Multiple occurrence of dollar signs detected inside the string.\n"
							+ "Please ensure numeric value has a valid format.\n"
							+ "This line will be skipped.\n");
		        	multipleDSOccurrence = true;
		        }
			}
			if (matcher.find()){
				System.out.println("Warning: Error occur at line No." + lineNum
						+ " and the field of " + itemName[i] +" (field No." + (i+1) + ").\n"
						+ "Some numeric value(s) may have inappropriate format or this field has an invalid value.\n"
						+ "(e.g. a numeric value contains whitespace(s) or unacceptable character(s);\n"
						+ "a negative value;\n"
						+ "or an empty field)\n"
						+ "This line will be skipped.\n");
				invalidNumericData = true;
			}
		}
		if (invalidNumericData || invalidTID || invalidHours || incorrectUseOfDecimalPoint
				|| multipleDPOccurrence || multipleDSOccurrence){
			lineCheckingFails = true;
		}
		if (!(line[1].matches("\\w+,?\\s+\\w+"))){
			System.out.println("Warning: Invalid employee's name at line No." + lineNum +".\n"
					+ "A valid name must start with non-whitespace character;\n"
					+ "and must only contain sensible word character"
					+ " (in terms of java regex definition of '\\w').\n" + "Surname and firstname must be separated by "
					+ "whitespace character(s) (excluding '\\r', '\\n' or '\\f').\n" + "This line will be skipped.\n");
			lineCheckingFails = true;
		}
		if ((!(line[2].equalsIgnoreCase("Salaried"))) && (!(line[2].equalsIgnoreCase("Hourly")))){
			System.out.println("Warning at line No." + lineNum
					+ " : Employeement type MUST be either 'Salaried' or 'Hourly' (case-ignored).\n"
					+ "This line will be skipped.\n");
			lineCheckingFails = true;
		}
		if ((!(line[5].matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d"))) || (!(line[6].matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d")))){
			System.out.println("Warning at line No." + lineNum
					+ " : Start and End date should be in the format of 'yyyy-mm-dd'.\n"
					+ "Program will attempt to ignore this error because it might not impede further processing.\n");
		}
		if (lineCheckingFails){
			return true;
		}
		return false;
	}
}
