package payroll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
class CompanyRecords {
	private ArrayList<Employee> companyList = new ArrayList<Employee>();
	private double _totalBurden = 0;
	private double _totalPAYE = 0;
	public void addSalariedEmployee(int TID,String name,double salary,double YTD,String start,String end,double hr,double d){
		double gross = MoneyCalculator.roundNearestCent(salary/52);
		double paye = MoneyCalculator.roundNearestCent(MoneyCalculator.calcTax(salary)/52);
		double nett = gross - paye - d;
		double newytd = YTD + gross;
		SalariedEmployee nextEmployee = new SalariedEmployee(TID,name,salary,newytd,start,end,hr,d,gross,paye,nett);
		companyList.add(nextEmployee);
		_totalBurden += gross;
		_totalPAYE += paye;
	}
	public void addHourlyEmployee(int TID,String name,double rate,double YTD,String start,String end,double hr,double d){
		double gross = MoneyCalculator.roundNearestCent(MoneyCalculator.hourlyRate(rate,hr));
		double paye = MoneyCalculator.roundNearestCent(MoneyCalculator.calcTax(gross*52)/52);
		double nett = gross - paye - d;
		double newytd = YTD + gross;
		HourlyEmployee nextEmployee = new HourlyEmployee(TID,name,rate,newytd,start,end,hr,d,gross,paye,nett);
		companyList.add(nextEmployee);
		_totalBurden += gross;
		_totalPAYE += paye;
	}
	public void printAllPayslips(){
		printCurrentDate();
		for(int i = 0; i < companyList.size();i++){
			companyList.get(i).printPayslip();;
		}
	}
	public void printAllEmployees(){
		printCurrentDate();
		for(int i = 0; i < companyList.size();i++){
			companyList.get(i).printEmployee();;
		}
	}
	public void printTotalBurden(){
		printCurrentDate();
		companyList.get(0).printPayPeriod();
		System.out.printf("Total Burden: $%.2f\n",MoneyCalculator.roundNearestCent(_totalBurden));
	}
	public void printTotalPAYE(){
		printCurrentDate();
		companyList.get(0).printPayPeriod();
		System.out.printf("Total PAYE: $%.2f\n",MoneyCalculator.roundNearestCent(_totalPAYE));
	}
	public void nameSort(){
		Collections.sort(companyList, new Employee.NameCompare());
	}
	public void TIDSort(){
		Collections.sort(companyList, new Employee.TIDCompare());
	}
	public void printCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		System.out.println(dateFormat.format(currentDate));
	}
}
