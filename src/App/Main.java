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
		
		dataManager.createDays();

		// date format: (year, month, date)
		
		Day testDay = dataManager.getDay(2001, 1, 1);		
		
		System.out.println(testDay.date + " - max temp: " + testDay.weatherDay.get_temperature_max() + " degrees, close price: " + testDay.euroEuroinvesterDay.get_cose() + ", events: " + testDay.weatherDay.get_events());
		
		// classifying a "good day".. grade?
		
		// apriori
		// id3
		
		// required: 2 methods from 2 different areas
		
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
