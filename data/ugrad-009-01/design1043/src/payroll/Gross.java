package payroll;
public class Gross {
	NearestCent nc = new NearestCent();
	public double calcGross(Employee employee){
		if(employee.earnType()){
			return nc.round((employee.get_rate())/52.0);
		}
		else{
			double gross = 0;
			if(employee.get_hours() <= 40){
				gross = nc.round(employee.get_rate() * employee.get_hours());
			}
			else {
				gross = nc.round(employee.get_rate() * 40);
			}
			if(employee.get_hours() >40 && employee.get_hours() <= 43){
				gross += nc.round(nc.round(employee.get_rate() * (employee.get_hours()%40)) * 1.5);
			}
			else if(employee.get_hours() >43){
				gross += nc.round(nc.round(employee.get_rate() * (employee.get_hours()%40)) * 1.5) + nc.round(nc.round(employee.get_rate() * (employee.get_hours()%43)) * 2);
			}
			return gross;
		}
	}
	public void printGross(Employee _employee){
		System.out.printf("$%.2f, ", calcGross(_employee));
	}
	public void printYTD(Employee _employee){
		System.out.printf("$%.2f", addToYTD(_employee));
	}
	public double addToYTD(Employee _employee){
		double _YTD =_employee.get_YTD() + calcGross(_employee);
		return _YTD;
	}
}
