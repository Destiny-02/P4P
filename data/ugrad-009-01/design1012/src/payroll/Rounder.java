package payroll;
import java.lang.Math;
public class Rounder {
	double roundToTwoDecimalPlaces(double doubleValue){
		return Math.round(doubleValue * 100.0) / 100.0;
	}
}
