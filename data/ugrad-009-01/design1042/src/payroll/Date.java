package payroll;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class Date{
	private LocalDate _date;
	public Date(){
		_date = LocalDate.now();
	}
	public String toString(){
		return _date.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
	public LocalDate getDate(){
		return _date;
	}
	public Date(String date, String format) throws DateTimeParseException{
		int year,month,day = 0;
		int charPointerPos = -1;
		int initialPos = -1;
		int currentMax = 0;
		String[] storageString = new String[3];
		charPointerPos = date.indexOf('-');
		if(charPointerPos>=0){storageString[currentMax++] = date.substring(initialPos+1, charPointerPos);}
		while(charPointerPos >= 0) {
			initialPos = charPointerPos;
		    charPointerPos = date.indexOf('-',charPointerPos+1);
		    if(charPointerPos==-1){
		    	storageString[currentMax++] = date.substring(initialPos+1);
		    }else{
		    	storageString[currentMax++] = date.substring(initialPos+1, charPointerPos);
		    }
		}
		try{
		switch(format){
		case "ymd":
			year = Integer.parseInt(storageString[0]);
			month = Integer.parseInt(storageString[1]);
			day = Integer.parseInt(storageString[2]);
			_date = LocalDate.of(year, month, day);
			break;
		case "ydm":
			year = Integer.parseInt(storageString[0]);
			month = Integer.parseInt(storageString[2]);
			day = Integer.parseInt(storageString[1]);
			_date = LocalDate.of(year, month, day);
			break;
		case "dmy":
			year = Integer.parseInt(storageString[2]);
			month = Integer.parseInt(storageString[1]);
			day = Integer.parseInt(storageString[0]);
			_date = LocalDate.of(year, month, day);
			break;
		case "mdy":
			year = Integer.parseInt(storageString[2]);
			month = Integer.parseInt(storageString[0]);
			day = Integer.parseInt(storageString[1]);
			_date = LocalDate.of(year, month, day);
			break;
		default:
			throw new DateTimeParseException("Date format not given correctly.","",0);
		}
		}catch(NumberFormatException nfe){
			throw new DateTimeParseException("Invalid date: ",date,0);
		}catch(DateTimeException dte){
			throw new DateTimeParseException("Invalid date: ",date,0);
		}
	}
}
