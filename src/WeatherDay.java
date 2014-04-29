import java.util.ArrayList;
import java.util.Date;

public class WeatherDay extends Data {

	private ArrayList<EVENT> events;
	private Date date;
	private double temperature_max;

	// SET
	public void set_events(ArrayList<EVENT> data) {
		
		events = data;
		
	}
	
	public void set_date(Date data) {
		
		date = data;
		
	}
	
	public void set_events(EVENT data) {
		
		events = new ArrayList<EVENT>(); 
		events.add(data);
		
	}
	
	public void set_temperature(double data) {
		
		temperature_max = data;
		
	}
	
	// GET
	public ArrayList<EVENT> get_events() {
		
		return events;
		
	}
	
	public Date get_date() {
		
		return date;
		
	}
	
	public double get_temperature_max() {
		
		return temperature_max;
		
	}
	
}
