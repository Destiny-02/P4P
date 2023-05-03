package payroll;
import java.util.Comparator;
public class Employee extends Prints implements Comparable<Employee> {
    public int compareTo(Employee e){
        return Integer.parseInt(_ID)-Integer.parseInt(e._ID);
}
    public class Compare implements Comparator<Employee> {
        public int compare(Employee a, Employee b){
            String[] nameA = a._name.split(", ");
            String[] nameB = b._name.split(", ");
            if(nameA[0].compareTo(nameB[0]) == 0){
                return nameA[1].compareTo(nameB[1]);
            }else{
                return nameA[0].compareTo(nameB[0]);
            }
        }
    }
    private final String _ID,_name, _employment, _start, _end;
    private final double  _YTD, _deductions, _hours, _rate;
    public Employee(String[] line){
        _ID = (line[0]);
        _name = line[1];
        _employment = line[2];
        _rate = Double.parseDouble(line[3].replace("$",""));
        _YTD = Double.parseDouble(line[4].replace("$",""));
        _start = line[5];
        _end = line[6];
        _hours = Double.parseDouble(line[7]);
        _deductions = Double.parseDouble(line[8].replace("$",""));
    }
    public String GetTimePeriod(){
        return _start + " to " + _end;
    }
    public double CalGross(){
        double gross;
        double hours;
        if(_hours>40 && _hours<=43){
            hours = (_hours-40)*1.5 + 40;
        }else if(_hours>43){
            hours = ((_hours-43)*2 + 40) + 1.5*3;
        } else {
            hours = _hours;
        }
        if(_employment.equals("Hourly")){
            gross = (double)Math.round(hours*_rate*100)/100;
        }else{
            gross = (double)Math.round(_rate/52*100)/100;
        }
        return gross;
    }
    public double CalPAYE(double gross){
        double tax;
        gross = gross*52;
        if(gross<=14000){
            tax = gross*0.105;
        } else if(gross>14000 && gross<=48000){
            tax = (gross-14000)*0.175 +1470;
        } else if(gross>48000 && gross<=70000){
            tax = (gross-48000)*0.3 +7420;
        }else{
            tax = (gross-70000)*0.33 +14020;
        }
        return (double)Math.round(tax/52*100)/100;
    }
    public double CalNett(double gross, double PAYE){
        return (double)Math.round((gross-_deductions-PAYE)*100)/100;
    }
    public void PrintOut(double gross,double PAYE, double Nett){
        String[] Name = _name.split(", ");
        double YTD = _YTD + gross;
        System.out.print(_ID+". "+Name[1]+" "+Name[0]+", ");
        System.out.printf("Period: %s. Gross: $%.2f, PAYE: $%.2f, Deductions: $%.2f ",GetTimePeriod(),gross,PAYE,_deductions);
        System.out.printf("Nett: $%s YTD: $%.2f",Nett,YTD);
    }
    public void PrintOut(double gross){
        double YTD = _YTD+gross;
        System.out.printf("%s (%s) %s, $%.2f YTD:$%.2f\n",_name,_ID,_employment,_rate,YTD);
    }
}
