
package payroll;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ValidateEmployeeInput {
	private boolean _validInput;
	private String[] _fileData;
	private String _additionalErrorDetails;
	public ValidateEmployeeInput(String[] fileData){
		_fileData = fileData;
		_validInput = true;
		_additionalErrorDetails = "Additional Details: ";
		checkCorrectStringLength();
		checkIDNumber();
		checkNameFormat();
		checkDate();
		checkEmployment();
	}
	public boolean getValidity(){
		return _validInput;
	}
	public String getAdditionalErrorDetails(){
		return _additionalErrorDetails;
	}
	private void checkCorrectStringLength(){
		if (_fileData.length != 9){
			_validInput = false;
			_additionalErrorDetails += "\t[Not enough arguments]";
		}
	}
	private void checkIDNumber(){
		try{
			Integer.parseInt(_fileData[0]);
		} catch(NumberFormatException e){
			_validInput = false;
			_additionalErrorDetails += "\t[ID not number]";
		}
	}
	private void checkNameFormat(){
		if(!_fileData[1].contains(", ")){
			_validInput = false;
			_additionalErrorDetails += "\t[Names not separated by ', ']";
		}
	}
	private void checkDate(){
        try {
        	SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        	Date startDate = dateFormat.parse(_fileData[5]);
			Date endDate = dateFormat.parse(_fileData[6]);
			if(startDate.after(endDate)){
				_validInput = false;
				_additionalErrorDetails += "\t[End date before start date]";
			}
		} catch (ParseException e) {
			_validInput = false;
			e.printStackTrace();
			_additionalErrorDetails += "\t[Incorrect Date Format]";
		}
	}
	private void checkEmployment(){
		if(!_fileData[2].equals("Hourly") && !_fileData[2].equals("Salaried")){
			_validInput = false;
			_additionalErrorDetails += "\t[Employment must be either 'Hourly' or 'Salaried']";
		}
	}
}
