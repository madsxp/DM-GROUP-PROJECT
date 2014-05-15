package App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import App.Data.PROPERTY;
import Models.Day;

public class App {

	DataManager dataManager;	
	
	public void run() throws IOException {

		// Load file
		CSVFileReader reader = new CSVFileReader();
		
//		String[][] wundergroundData = reader.readWunderground("/Users/mads/Google Drive/DEV/S3/DM-GROUP-PROJECT/data/weather.csv", false);
//		String[] wundergroundHeaders = reader.readHeaders("/Users/mads/Google Drive/DEV/S3/DM-GROUP-PROJECT/data/weather.csv");
//		
//		String[][] euroinvesterData = reader.readEuroinvester("/Users/mads/Google Drive/DEV/S3/DM-GROUP-PROJECT/data/OMX C20 and OMX C20 CAP.csv", false);
//		String[] euroinvesterHeaders = reader.readHeaders("/Users/mads/Google Drive/DEV/S3/DM-GROUP-PROJECT/data/OMX C20 and OMX C20 CAP.csv");
//		
		String[][] wundergroundData = reader.readWunderground("data/weather.csv", false);
		String[] wundergroundHeaders = reader.readHeaders("data/weather.csv");
		
		String[][] euroinvesterData = reader.readEuroinvester("data/OMX C20 and OMX C20 CAP.csv", false);
		String[] euroinvesterHeaders = reader.readHeaders("data/OMX C20 and OMX C20 CAP.csv");
		
		String[][] noaaData = reader.readNoaa("data/weather_noaa.csv", false);
		String[] noaaHeaders = reader.readHeadersComma("data/weather_noaa.csv");
		
		// Data manager
		dataManager = new DataManager();
		
		dataManager.fitAllWundergroundData(wundergroundData, wundergroundHeaders);
		dataManager.fitAllEuroinvesterData(euroinvesterData, euroinvesterHeaders);
                
                
		// Calculate stuff like mean, wind-chill-factor etc.
		dataManager.addAdditionalWeatherDataToWeatherDays(dataManager.weatherData);
		dataManager.addAdditionalDataToEuroinvestorDay(dataManager.euroinvesterData);
		
		dataManager.createDays();
		
		dataManager.addNoaaDataToDays(dataManager.days, noaaData);
		
		dataManager.calculateStats();
		
		// Add discrete values (for apriori)
		dataManager.addDiscreteValuesToDays();
		
		
                
                //dataManager.show(, null);
		
		// date format: (year, month, date)
		
		
		
//		Day testDay = dataManager.getDay(2005, 11, 24);
//		
//		System.out.println(testDay.get_euroinvesterDay().get_development());

		
		// Apriori
		
		Apriori apriori = new Apriori(dataManager.days);
		
//		ArrayList<PROPERTY> customList = new ArrayList<PROPERTY>();
//		customList.add(PROPERTY.price_increase);
//		customList.add(PROPERTY.directionEASTERN);
//		customList.add(PROPERTY.directionNORTHEASTERN);
//		
//		apriori.setSpecificPropertySet(customList);
		
		//apriori.printDataSetsLines(0, 100);
		apriori.run(100);
		
		//waitForInput();
		
	}
	
	public void waitForInput() throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        command(br.readLine());
        
	}
	
	public void command(String command) {
		
		String[] commands = command.split(" ");
		
		switch(commands[0]) {
			
			case "q":
				System.out.println("Quitting..");
				return;
			
			case "day":
				Day day = dataManager.getDay(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]), Integer.parseInt(commands[3]));
				System.out.println(day.getReadableData());
				break;
			default:
				break;
		
		}
		
		try {
			waitForInput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
