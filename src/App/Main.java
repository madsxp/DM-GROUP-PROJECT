package App;
import java.io.IOException;
import java.util.Date;

import Models.Day;

public class Main {

	public static void main(String[] args) throws IOException {
		
		// Load file
		CSVFileReader reader = new CSVFileReader();
		String[][] wundergroundData = reader.readWunderground("data/weather.csv", false);
		String[] wundergroundHeaders = reader.readHeaders("data/weather.csv");
		
		String[][] euroinvesterData = reader.readEuroinvester("data/OMX C20 and OMX C20 CAP.csv", false);
		String[] euroinvesterHeaders = reader.readHeaders("data/OMX C20 and OMX C20 CAP.csv");
		
		// Data manager
		DataManager dataManager = new DataManager();
		
		dataManager.fitAllWundergroundData(wundergroundData, wundergroundHeaders);
		dataManager.fitAllEuroinvesterData(euroinvesterData, euroinvesterHeaders);
		
		// Calculate stuff like mean, wind-chill-factor etc.
		dataManager.addAdditionalWeatherData(dataManager.weatherData);
		
		dataManager.createDays();
		
		// date format: (year, month, date)
		
		Day testDay = dataManager.getDay(2001, 1, 1);		
		

	}
}
