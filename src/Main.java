import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		
		// Load file
		CSVFileReader reader = new CSVFileReader();
		String[][] data = reader.read("data/weather.csv", false);
		String[] headers = reader.readHeaders("data/weather.csv");
		
		// Data manager
		DataManager dataManager = new DataManager();
		
		dataManager.fitAllData(data, headers);
		
		for (WeatherDay wd : dataManager.weatherData) {
			
			System.out.println(wd.get_date() + " : " + wd.get_events());
			
		}
	}
}
