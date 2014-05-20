package models;
import java.util.ArrayList;
import java.util.Date;


public class Day {

	public Date date;
	private WeatherDay weatherDay;
	private SecondaryDay secondaryDay;
	
	// google trends
	public ArrayList<String[]> google_trends;
	
	public Day() {
		
		google_trends = new ArrayList<String[]>();
		
	}
	
	public String getReadableData() {
		
		return	
				"------------------------------------------------------\n"
		+ 		"Day\n"
		+ 		"Date: " + date.toString() + "\n"
		+		"Weather data: " + weatherDay.getReadableData()
		+		"Euroinvestor data: " + secondaryDay.getReadableData()
		+		"-----------------------------------------------------\n";
		
	}
	
	// GOOGLE TRENDS
	
	public Double get_trend(String trend) {
		Double result = null;
		for (String[] trend_a : google_trends) {
			
			if (trend_a[0].equals(trend)) {
				result = Double.parseDouble(trend_a[1]);
				break;
			}
			
		}
		return result;
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
