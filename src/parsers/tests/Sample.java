package parsers.languages.__tests__.input;

import java.util.ArrayList;
import java.beans.*;
import java.util.List;

// import from another package
import nz.school.app.cordovaSmartCardPlugin.SmartCardReader;
import nz.school.app.cordovaGeoLocationPlugin.*;

// import from our own package
import parsers.languages.Whatever;
import parsers.languages.*;

public class Sample {
  /**
   * Sample is a class
   */
  public int get_annual_income_deduction(Map<String, Integer> myFirstArg, int secondArg) {
    int a = 1;
    int b = 5;

    List<String, String> employees = new ArrayList<>();

    // this is a comment
    return a + b;
  }

  private static final double paye_tax_bracket = 17.5;

  protected String get_payslip_for_paye(int arg1, String arg2, int... spreadArguments) {

    // these are all globals that should not be considered
    System.out.println(new String());

    return "";
  }

  enum MyEnum {
    ENUM_MEMBER_1,
    ENUM_MEMBER_2,
  }

  interface MyIFace {
    int myInterfaceProperty;
  }

  static {
    SOME_CONST = paye_tax_bracket * 2;
  }
}
