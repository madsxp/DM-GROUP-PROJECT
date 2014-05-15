package models;
import java.util.ArrayList;
import java.util.Date;

import app.Data;
import app.Data.EUROINVESTOR_ATTRIBUTE;
import app.Data.PROPERTY;
import app.Data.WEATHERDAY_ATTRIBUTE;

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
				+ "   Close : " + get_close() + "\n"; 
		
	}
	
	public Object get(EUROINVESTOR_ATTRIBUTE attr) {
    	
    	switch(attr) {
    		
	    	case close:
	    		return get_close();
	    	case development:
	    		return get_development();
	    	case date:
	    		return get_date();
    		default:
    			return null;
    		
    	}
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
	
	public double get_close() {
		
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


