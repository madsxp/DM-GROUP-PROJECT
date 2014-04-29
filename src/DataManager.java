import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class DataManager extends Data {

	public ArrayList<WeatherDay> weatherData;
	private SimpleDateFormat dateParser;
	public HashMap<Date, Day> days;
	
	public DataManager() {
		
		dateParser = new SimpleDateFormat("yyyy-MM-dd");
		
	}
	
	
	public void fitAllWundergroundData(String[][] data, String[] headers) {
		
		if (data.length > 0) {
			weatherData = new ArrayList<WeatherDay>();
			
			for (String[] row : data) {
				
				weatherData.add(fitWundergroundEntry(row, headers));
				
			}
		}
	}
	
	// Create a WeatherDay object and fit data to it
	public WeatherDay fitWundergroundEntry(String[] data, String[] headers) {
		
		WeatherDay wd = new WeatherDay();
		
		// Events
		int index_events = Arrays.asList(headers).indexOf("Events");
		String[] string_events = data[index_events].replaceAll("\\s+","").split("-");
		ArrayList<EVENT> events = new ArrayList<EVENT>();

		for (String event : string_events) {
			
			switch (event.toLowerCase()) {
				case "rain":
					events.add(EVENT.RAIN);
					break;
				case "snow":
					events.add(EVENT.SNOW);
					break;
				case "fog":
					events.add(EVENT.FOG);
					break;
				case "thunderstorm":
					events.add(EVENT.THUNDERSTORM);
					break;
				case "hail":
					events.add(EVENT.HAIL);
					break;
				case "":
					// empty string
					break;
				default:
					System.out.println("Unknown event : " + event);
					System.out.println(data[index_events]);
					break;
			
			}	
		}
		
		wd.set_events(events);
		
		// Date
		int index_date = Arrays.asList(headers).indexOf("CET");
		Date date = new Date();
		
		try {
			date = dateParser.parse(data[index_date]);
		} catch (ParseException e) {
			date = null;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (date != null) {
			
			wd.set_date(date);
			
		}
		
		// Temperature max
		int index_temperature_max = Arrays.asList(headers).indexOf("MaxTemperatureC");
		Double temperature_max = null;
		try {
			temperature_max = Double.parseDouble(data[index_temperature_max]);
		} catch (Exception e) {
			
		}
		
		if (temperature_max != null) {
			
			wd.set_temperature(temperature_max);
			
		}
		
		return wd;
		
	}
	
	public void createDays() {
		
		days = new HashMap<Date, Day>();
		
		// weatherdays
		for (WeatherDay wd : weatherData) {
			
			Date date = wd.get_date();
			
			if (days.containsKey(date)) {
				
				System.out.println("tood.. contains date already");
				
			}
			else {
				
				Day day = new Day();
				day.weatherDay = wd;
				
				days.put(date, day);
				
			}
			
		}
		
	}
	
	public Day getDay(int year, int month, int day) {
		
		return days.get(date(year, month, day));
			
	}
	
	public Date date(int year, int month, int day) {
		
		Date date = new Date();
		
		try {
			date = dateParser.parse(year + "-" + month + "-" + day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			date = null;
			e.printStackTrace();
		}
		
		return date;
		
	}
	
	
}
 