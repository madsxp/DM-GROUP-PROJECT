import java.io.IOException;
import java.util.Date;

public class Main {

	public static void main(String[] args) throws IOException {
		
		// Load file
		CSVFileReader reader = new CSVFileReader();
		String[][] data = reader.readWunderground("data/weather.csv", false);
		String[] headers = reader.readHeaders("data/weather.csv");
		
		// Data manager
		DataManager dataManager = new DataManager();
		
		dataManager.fitAllWundergroundData(data, headers);
		
		dataManager.createDays();

		// year, month, date
		Day testDay = dataManager.getDay(1996, 8, 8);
		System.out.println(testDay.weatherDay.get_temperature_max());
		
		
		
//		for (WeatherDay wd : dataManager.weatherData) {
//			
//			System.out.println("date: " + wd.get_date());
//			System.out.println("events: " + wd.get_events());
//			System.out.println("max temp: " + wd.get_temperature_max());
//			System.out.println("");
//			
//		}
	}
}
