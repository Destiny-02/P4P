package payroll;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class InputParser {
  private BufferedReader _reader;
  private int _lineNumber = 0;
  private String[] _record;
  private static final String VALID_DATE = "[1-9][0-9]{3}-(0?[1-9]|1[0-2])-(3[0-1]|2[0-9]|1[0-9]|0?[1-9])";
  private static final String VALID_HOURS= "[0-9]+(\\.[0-9]+)?";
  private static final String VALID_NAME = "[^0-9]+";
  private static final String VALID_MONEY = "\\$(([1-9][0-9]*)|(0))(\\.[0-9]*)?";
  public InputParser(String filename) {
    try {
      _reader = new BufferedReader(new FileReader(filename));
    } catch (FileNotFoundException e) {
      System.out.println("Could not find the specified input file.");
    }
  }
  public Employee parseEmployee() {
    try {
      _lineNumber++;
      String line = _reader.readLine();
      if (line == null) {
        return null;
      }
      while (line.isEmpty() || line.startsWith("#")) {
        _lineNumber++;
        line = _reader.readLine();
        if (line == null) return null;
      }
      _record = line.split("\\t");
      checkFields();
      return makeEmployee();
    } catch (IOException e) {
      System.out.println("I/O Exception occured in the file.");
    } catch (InvalidEmployeeRecordFormatException e) {
      System.out.println(e.getMessage());
    }
    System.out.println("Fatal error: could not parse line " + _lineNumber);
    return null;
  }
  private void checkFields() throws InvalidEmployeeRecordFormatException {
    if (_record.length != 9) {
      throwLine("Missing / Incorrectly seperated fields");
    }
    for (int i = 0; i < _record.length; i++) {
      String input = _record[i];
      if (i == 0) {
        if (!input.matches("[0-9]+") && !input.substring(0, 1).equals("0")) {
          throwLine("Invalid TID.");
        }
      } else if (i == 1) {
        if(!input.contains(",") || !input.matches(VALID_NAME)) {
          throwLine("Name issue: Missing first or last name (or just the comma!)");
        }
      } else if (i == 2) {
        if (!input.equals("Salaried") && !input.equals("Hourly")) {
          throwLine("Invalid Employee type.");
        }
      } else if (i == 3 || i == 4 || i == 8) {
        String money = i == 3 ? "Rate" : i == 4 ? "YTD" : "Deduction";
        if (!input.matches(VALID_MONEY)) {
          throwLine("Invalid money format for the record's " + money + ".");
        }
      } else if (i == 5 || i == 6) {
          if(!(input.length() == 10) || !input.matches(VALID_DATE)) {
            throwLine("Invalid date.");
          }
      } else if (i == 7) {
        if(!input.matches(VALID_HOURS)) {
          throwLine("Invalid Hours.");
        }
      }
    }
  }
  private Employee makeEmployee() {
    int tid = Integer.parseInt(_record[0]);
    String[] name = _record[1].replaceAll("\\s", "").split(",");
    String type = _record[2];
    double rate = Double.parseDouble((_record[3].substring(1)));
    double ytd = Double.parseDouble((_record[4].substring(1)));
    String[] period = new String[]{_record[5], _record[6]};
    double hours = Double.parseDouble(_record[7]);
    double deductions = Double.parseDouble(_record[8].substring(1));
    return new Employee(tid, name, type, rate, ytd, period, hours, deductions);
  }
  private void throwLine(String msg) throws InvalidEmployeeRecordFormatException {
    throw new InvalidEmployeeRecordFormatException(_lineNumber, msg);
  }
}
