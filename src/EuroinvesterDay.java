import java.util.Date;


public class EuroinvesterDay {

	Date date;
	double close;
	
	// SET
	
	public void set_date(Date data) {
		
		date = data;
		
	}
	
	public void set_close(double data) {
		
		close = data;
		
	}
	
	
	// GET
	
	public Date get_date() {
		
		return date;
		
	}
	
	public double get_cose() {
		
		return close;
		
	}
}


