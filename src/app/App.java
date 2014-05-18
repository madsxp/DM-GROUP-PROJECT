package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import models.Day;
import app.Data.DATA_MODEL;
import app.Data.DATA_TYPE;
import app.Data.EUROINVESTOR_ATTRIBUTE;
import app.Data.WEATHERDAY_ATTRIBUTE;

public class App extends Data {

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
		
		// date format: (year, month, date)
		
		//Visualization visualization = new Visualization();
		
		//visualization.showDaysTable(dataManager.days);
		
		
		runRandomKNN(10, WEATHERDAY_ATTRIBUTE.temperature_max, 100, false);
		// Apriori
		
		Apriori apriori = new Apriori(dataManager.days);
		
		apriori.run(370);
		
		//waitForInput();
		
	}

	public void runRandomKNN(int numOfRuns, Object classLabel, int K, boolean outputEachRun) {
		
		KNN knn = new KNN(dataManager);
		
		DATA_MODEL class_label_data_model;
		String class_label_data_model_str = classLabel.getClass().getSimpleName();
		
		if (class_label_data_model_str.equals("WEATHERDAY_ATTRIBUTE")) {
			
			class_label_data_model = DATA_MODEL.WeatherDay;
			
		}
		else if (class_label_data_model_str.equals("EUROINVESTOR_ATTRIBUTE")) {
			
			class_label_data_model = DATA_MODEL.EuroinvestorDay;
			
		}
		else {
			
			//TODO: error
			System.out.println("not a valid classLabel");
			return;
			
		}		
		
		int numberOfCorrects = 0;
		Double sum = 0.;
		
		for (int i = 0; i<numOfRuns; i++) {
			
			ArrayList<Day> trainingSet = dataManager.getDaysAsList();
			
			int random = (int)(Math.random() * ((trainingSet.size()-1) + 1));
			
			Day testDay = trainingSet.get(random);
			trainingSet.remove(testDay);
			
			knn.setTrainingSet(trainingSet);
			
			if (class_label_data_model == DATA_MODEL.WeatherDay) {
				
				Object result = knn.run(testDay, classLabel, 200, outputEachRun);
				
				if (outputEachRun) {
	
					System.out.println("\n  * On day: " + testDay.date);
					System.out.println("  * Guessed: " + result);
					
				}
				
				Object was = testDay.get_weatherDay().get((WEATHERDAY_ATTRIBUTE) classLabel);

				if (get_weatherday_type((WEATHERDAY_ATTRIBUTE) classLabel) == DATA_TYPE.numeric) {
					
					Double offBy = Math.abs((Double) result - (Double) was);
					sum += offBy;
					if (outputEachRun) {
						System.out.println("  * Was: " + testDay.get_weatherDay().get((WEATHERDAY_ATTRIBUTE) classLabel));
						System.out.println("  * --- off by " + offBy);
					}
				}
				else {
					
					Boolean guessedCorrect = result.equals(was);
					if (guessedCorrect) {
						
						numberOfCorrects ++;
						
					}
					if (outputEachRun) {
						System.out.println("  * Was: " + testDay.get_weatherDay().get((WEATHERDAY_ATTRIBUTE) classLabel));
						System.out.println("--- " + guessedCorrect);
					}
				}
			}
			else if (class_label_data_model == DATA_MODEL.EuroinvestorDay) {
				
				Object result = knn.run(testDay, classLabel, 200, outputEachRun);
				
				if (outputEachRun) {
	
					System.out.println("\n  * On day: " + testDay.date);
					System.out.println("  * Guessed: " + result);
					
				}
				
				Object was = testDay.get_euroinvesterDay().get((EUROINVESTOR_ATTRIBUTE) classLabel);

				if (get_euroinvestor_type((EUROINVESTOR_ATTRIBUTE) classLabel) == DATA_TYPE.numeric) {
					
					Double offBy = Math.abs((Double) result - (Double) was);
					sum += offBy;
					if (outputEachRun) {
						System.out.println("  * Was: " + testDay.get_euroinvesterDay().get((EUROINVESTOR_ATTRIBUTE) classLabel));
						System.out.println("  * --- off by " + offBy);
					}
				}
				else {
					
					Boolean guessedCorrect = result.equals(was);
					if (guessedCorrect) {
						
						numberOfCorrects ++;
						
					}
					if (outputEachRun) {
						System.out.println("  * Was: " + testDay.get_euroinvesterDay().get((EUROINVESTOR_ATTRIBUTE) classLabel));
						System.out.println("--- " + guessedCorrect);
					}
				}
			}
			if (outputEachRun) {
				System.out.println("----------------------------------------");
			}
			else {
				
				System.out.println(i + "/" + numOfRuns);
				
			}
		}
		
		if (sum>0) {
			
			System.out.println("\nOff by mean : " + sum/numOfRuns);
			
		}
		else {
		
			System.out.println("\nCorrects : " + numberOfCorrects);
			System.out.println(numberOfCorrects + "/" + numOfRuns);
			System.out.println(((double) numberOfCorrects / (double) numOfRuns * 100) + "%");
			
		}
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
