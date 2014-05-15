package models;
import java.util.Date;


public class Day {

	public Date date;
	private WeatherDay weatherDay;
	private EuroinvesterDay euroinvesterDay;
	
	public String getReadableData() {
		
		return	
				"------------------------------------------------------\n"
		+ 		"Day\n"
		+ 		"Date: " + date.toString() + "\n"
		+		"Weather data: " + weatherDay.getReadableData()
		+		"Euroinvestor data: " + euroinvesterDay.getReadableData()
		+		"-----------------------------------------------------\n";
		
	}
	
	// WEATHERDAY
	public void set_weatherDay(WeatherDay data) {
		
		weatherDay = data;
		
	}
	
	public WeatherDay get_weatherDay() {
		
		WeatherDay value = null;
        try {
            value = weatherDay;
        } catch (Exception e) {
        }
        return value;
		
	}
	
	// EUROINVESTERDAY
    public void set_euroinvesterDay(EuroinvesterDay data) {
		
		euroinvesterDay = data;
		
	}
	
	public EuroinvesterDay get_euroinvesterDay() {
		
		EuroinvesterDay value = null;
        try {
            value = euroinvesterDay;
        } catch (Exception e) {
        }
        return value;
		
	}

}
