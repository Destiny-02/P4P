package payroll;
public class Data implements Comparable<Data>{
		protected String firstName;
		protected String lastName;
		protected String Employment;
		protected double Rate;
		protected int TaxID;
		protected double YTD;
		protected String Start;
		protected String End;
		protected double Hours;
		protected double Deduction;
		 public Data(int TaxID,String lastName,String firstName, String Employment, double Rate,double YTD,double Hours, double Deduction) {
			 			this.TaxID=TaxID;
			 	        this.firstName = firstName;
			 	        this.lastName = lastName;
			 	        this.Employment = Employment;
			 	        this.Rate=Rate;
			 	        this.YTD = YTD;
			 	        this.Hours = Hours;
			 	        this.Deduction = Deduction;
			 	    }
		 public Data(String Employment,double Rate,double Hours) {
	 	        this.Employment = Employment;
	 	        this.Rate=Rate;
	 	        this.Hours = Hours;
	 	    }
		 public Data(int TaxID,String lastName,String firstName, String Employment,double Rate,double YTD,double Hours) {
	 			this.TaxID=TaxID;
	 	        this.firstName = firstName;
	 	        this.lastName = lastName;
	 	        this.Employment = Employment;
	 	        this.Rate=Rate;
	 	        this.YTD = YTD;
	 	    }
		 public int compareTo(Data in){
				return this.TaxID > in.TaxID ? +1 : this.TaxID < in.TaxID ? -1 : 0;
			}
}
