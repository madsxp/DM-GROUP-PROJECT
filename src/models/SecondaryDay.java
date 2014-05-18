package models;
import java.util.ArrayList;
import java.util.Date;

import app.Data.PROPERTY;
import app.Data.SECONDARY_ATTRIBUTE;

public class SecondaryDay {
	
	// Date
	private Date date;
	
	// Yahoo finance data
	private Double close;
	private Double development;
	private Boolean positive_development;
	
	// discrete values (for apriori)
	public ArrayList<PROPERTY> discreteValues;
	
	// CONSTRUCTOR
	
	public SecondaryDay() {
		
		discreteValues = new ArrayList<PROPERTY>();
		
	}
	
	public String getReadableData() {
		
		return "\n"
				+ "   Close : " + get_close() + "\n"; 
		
	}
	
	public Object get(SECONDARY_ATTRIBUTE attr) {
    	
    	switch(attr) {
    		
	    	case close:
	    		return get_close();
	    	case development:
	    		return get_development();
	    	case date:
	    		return get_date();
	    	case positive_development:
	    		return get_positive_development();
    		default:
    			return null;
    		
    	}
    }
	
	public Object set(SECONDARY_ATTRIBUTE attr, Object value) {
    	
    	switch(attr) {
    		
	    	case close:
	    		set_close((Double) value);
	    	case development:
	    		set_development((Double) value);
	    	case date:
	    		set_date((Date) value);
	    	case positive_development:
	    		set_positive_development((Boolean) value);
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

	// POSITIVE_DEVELOPMENT	
	
	public void set_positive_development(Boolean data) {
		
		positive_development = data;
		
	}
	
	public Boolean get_positive_development() {
		
		return positive_development;
		
	}
}


