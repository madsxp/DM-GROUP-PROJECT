package models;
import java.util.Date;


public class Day {

	public Date date;
	private WeatherDay weatherDay;
	private SecondaryDay secondaryDay;
	
	public String getReadableData() {
		
		return	
				"------------------------------------------------------\n"
		+ 		"Day\n"
		+ 		"Date: " + date.toString() + "\n"
		+		"Weather data: " + weatherDay.getReadableData()
		+		"Euroinvestor data: " + secondaryDay.getReadableData()
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
    public void set_secondaryDay(SecondaryDay data) {
		
		secondaryDay = data;
		
	}
	
	public SecondaryDay get_secondaryDay() {
		
		SecondaryDay value = null;
        try {
            value = secondaryDay;
        } catch (Exception e) {
        }
        return value;
		
	}

}
