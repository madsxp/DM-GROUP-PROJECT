import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DataManager extends Data {

	public ArrayList<WeatherDay> weatherData;
	private SimpleDateFormat dateParser;
	
	public DataManager() {
		
		dateParser = new SimpleDateFormat("yyyy-MM-dd");
		
	}
	
	
	public void fitAllData(String[][] data, String[] headers) {
		
		if (data.length > 0) {
			weatherData = new ArrayList<WeatherDay>();
			
			for (String[] row : data) {
				
				weatherData.add(fitEntryToModel(row, headers));
				
			}
		}
	}
	
	// Create a WeatherDay object and fit data to it
	public WeatherDay fitEntryToModel(String[] data, String[] headers) {
		
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
		return wd;
		
	}
	
	
}
 