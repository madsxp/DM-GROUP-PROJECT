package models;
import java.util.ArrayList;
import java.util.Date;

import app.Data;
import app.Data.PROPERTY;

public class EuroinvesterDay {

	private Date date;
	private Double close;
	private Double development;
	
	// discrete values (for apriori)
	public ArrayList<PROPERTY> discreteValues;
	
	// CONSTRUCTOR
	
	public EuroinvesterDay() {
		
		discreteValues = new ArrayList<PROPERTY>();
		
	}
	
	public String getReadableData() {
		
		return "\n"
				+ "   Close : " + get_cose() + "\n"; 
		
	}
	
	// DATE
	
	public void set_date(Date data) {
		
		date = data;
		
	}

	public Date get_date() {
		
		return date;
		
	}

		
	// CLOSE
	
	public void set_close(double data) {
		
		close = data;
		
	}
	
	public double get_cose() {
		
		return close;
		
	}
	
	// DEVELOPMENT

	public void set_development(double data) {
		
		development = data;
		
	}
	
	public double get_development() {
		
		return development;
		
	}

}


