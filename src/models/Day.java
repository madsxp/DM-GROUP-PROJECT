package models;
import java.util.Date;


public class Day {

	public Date date;
	private WeatherDay weatherDay;
	private SecondaryDay euroinvesterDay;
	
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
    public void set_euroinvesterDay(SecondaryDay data) {
		
		euroinvesterDay = data;
		
	}
	
	public SecondaryDay get_euroinvesterDay() {
		
		SecondaryDay value = null;
        try {
            value = euroinvesterDay;
        } catch (Exception e) {
        }
        return value;
		
	}

}
