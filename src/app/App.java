package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import models.Day;
import app.Data.DATA_MODEL;
import app.Data.DATA_TYPE;
import app.Data.SECONDARY_ATTRIBUTE;
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
		
		String[][] euroinvesterData = reader.readYahooFinanceData("data/OMX C20 and OMX C20 CAP.csv", false);
		String[] euroinvesterHeaders = reader.readHeaders("data/OMX C20 and OMX C20 CAP.csv");
		
		String[][] noaaData = reader.readNoaa("data/weather_noaa.csv", false);
		String[] noaaHeaders = reader.readHeadersComma("data/weather_noaa.csv");
		
		String[][] googleTrendsData = readAndCombineAllGoogleTrends(reader);
		
		// Data manager
		dataManager = new DataManager();
		
		dataManager.fitAllWundergroundData(wundergroundData, wundergroundHeaders);
		dataManager.fitAllSecondaryData(euroinvesterData, euroinvesterHeaders);
                
                
		// Calculate stuff like mean, wind-chill-factor etc.
		dataManager.addAdditionalWeatherDataToWeatherDays(dataManager.weatherData);
		dataManager.addAdditionalDataToSecondaryDay(dataManager.secondaryData);
		
		dataManager.createDays();
		
		dataManager.addNoaaDataToDays(dataManager.days, noaaData);
		dataManager.addGoogleTrendsToSecondaryDays(googleTrendsData, getGoogleTrendsHeaders());
		
		dataManager.calculateStats();
		
		// Add discrete values (for apriori)
		dataManager.addDiscreteValuesToDays();
		
		dataManager.calculateAllNormalizedValues();
		
		// date format: (year, month, date)
		
		//Visualization visualization = new Visualization();
		
		//visualization.showStringArrayData(googleTrendsData, getGoogleTrendsHeaders());
		
		//visualization.showDaysTable(dataManager.days);
		
		//KNN knn = new KNN(dataManager);
		
		//ArrayList<WEATHERDAY_ATTRIBUTE> restrictedWeatherdayAttributes = new ArrayList<WEATHERDAY_ATTRIBUTE>();
		
		//restrictedWeatherdayAttributes.add(WEATHERDAY_ATTRIBUTE.temperature_min);
		
		//knn.setRestrictedAttributes(restrictedWeatherdayAttributes, null);
		
		//ArrayList<Day> trainingSet = dataManager.getDaysAsList();
		//ArrayList<Day> trainingSetOnlyTrends = filterOutDaysWithoutTrends(trainingSet);
		
		//runRandomKNN(knn, 100, SECONDARY_ATTRIBUTE.trend_afbudsrejser, 100, trainingSetOnlyTrends, false);
		
		// Apriori
		
		
		Apriori apriori = new Apriori(filterDaysWithWeatherData(dataManager.getDaysAsList()));
//		
		
		ArrayList<PROPERTY> weatherProbs = new ArrayList<PROPERTY>(Arrays.asList(PROPERTY.values()));
		
		weatherProbs.remove(PROPERTY.price_decrease);
		weatherProbs.remove(PROPERTY.price_increase);
		weatherProbs.remove(PROPERTY.price_no_change);
		
		apriori.setSpecificPropertySet(weatherProbs);
		apriori.run(100);
//		
		//waitForInput();

	}
	
	public ArrayList<Day> filterDaysWithWeatherData(ArrayList<Day> set) {
		
		ArrayList<Day> newSet = new ArrayList<Day>();
		
		for (Day day : set) {
			
			if (day.get_weatherDay() != null) {

				newSet.add(day);	
				
			}
		}
		
		return newSet;
	}
	
	public ArrayList<Day> filterDaysWithPositiveDevelopmen(ArrayList<Day> set) {
		
		ArrayList<Day> newSet = new ArrayList<Day>();
		
		for (Day day : set) {
			
			if (day.get_secondaryDay() != null) {
				if (day.get_secondaryDay().get_positive_development() == true) {
					
					newSet.add(day);
					
				}
			}
		}
		
		return newSet;
	}
	
	public ArrayList<Day> filterDaysWithNegativeDevelopmen(ArrayList<Day> set) {
		
		ArrayList<Day> newSet = new ArrayList<Day>();
		
		for (Day day : set) {
			
			if (day.get_secondaryDay() != null) {
				if (day.get_secondaryDay().get_positive_development() == false) {
					
					newSet.add(day);
					
				}
			}
		}
		
		return newSet;
	}
	
	public ArrayList<Day> filterOutDaysWithoutTrends(ArrayList<Day> set) {
		
		ArrayList<Day> trainingSetOnlyTrends = new ArrayList<Day>();

		for (Day day : set) {
			
			if (day.get_secondaryDay() != null) {
				if (day.get_secondaryDay().get_trend_afbudsrejser() != null) {
					
					trainingSetOnlyTrends.add(day);
					
				}
			}
		}
		
		return trainingSetOnlyTrends;
	}
	
	public ArrayList<Day> filterOutDaysWithoutYahoo(ArrayList<Day> days) {
		
        // Remove days that has no weather or euroinvestor data
        ArrayList<Day> toBeRemoved = new ArrayList<Day>();
        
        for (Day day : days) {

            if (day.get_weatherDay() == null || day.get_secondaryDay() == null) {
            	
            	toBeRemoved.add(day);
            	
            }
        }
        
        for (Day day : toBeRemoved) {
        	
        	days.remove(day);
        	
        }
        
        return days;
		
	}
	
	public String[] getGoogleTrendsHeaders() {
		
		String[] headers = new String[google_trends.length+1];
		
		headers[0] = "Date";
		
		for (int i = 1; i < google_trends.length+1; i++) {
			
			headers[i] = google_trends[i-1];
			
		}
		
		return headers;
		
	}
	
	public String[][] readAndCombineAllGoogleTrends(CSVFileReader reader) throws IOException {
				
		String[][] googleTrends = null;
		
		for (int i = 0; i < google_trends.length; i++) {
			
			String[][] trend = reader.readGoogleTrends("data/google_trends_" + google_trends[i] + ".csv", false);
			
			if (googleTrends == null) {
				
				googleTrends = new String[trend.length][google_trends.length+1];
				
				for (int row = 0; row < trend.length; row++) {
					
					googleTrends[row][0] = trend[row][0];
					
				}
			}
			
			for (int row = 0; row < trend.length; row++) {
				if (!googleTrends[row][0].equals(trend[row][0])) {
					
					System.out.println("google trends data not lined up");
					
				}
				googleTrends[row][i+1] = trend[row][1];
				
			}
		}
		
		return googleTrends;
		
	}

	public void runRandomKNN(KNN knn, int numOfRuns, Object classLabel, int K, ArrayList<Day> trainingSet, boolean outputEachRun) {
		
		DATA_MODEL class_label_data_model;
		String class_label_data_model_str = classLabel.getClass().getSimpleName();
		
		if (class_label_data_model_str.equals("WEATHERDAY_ATTRIBUTE")) {
			
			class_label_data_model = DATA_MODEL.WeatherDay;
			
		}
		else if (class_label_data_model_str.equals("SECONDARY_ATTRIBUTE")) {
			
			class_label_data_model = DATA_MODEL.SecondaryDay;
			
		}
		else {
			
			//TODO: error
			System.out.println("not a valid classLabel");
			return;
			
		}		
		
		int numberOfCorrects = 0;
		Double sum = 0.;
		
		for (int i = 0; i<numOfRuns; i++) {
			
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
			else if (class_label_data_model == DATA_MODEL.SecondaryDay) {
				
				Object result = knn.run(testDay, classLabel, 200, outputEachRun);
				
				if (outputEachRun) {
	
					System.out.println("\n  * On day: " + testDay.date);
					System.out.println("  * Guessed: " + result);
					
				}
				
				Object was = testDay.get_secondaryDay().get((SECONDARY_ATTRIBUTE) classLabel);

				if (get_secondary_type((SECONDARY_ATTRIBUTE) classLabel) == DATA_TYPE.numeric) {
					
					Double offBy = Math.abs((Double) result - (Double) was);
					sum += offBy;
					if (outputEachRun) {
						System.out.println("  * Was: " + testDay.get_secondaryDay().get((SECONDARY_ATTRIBUTE) classLabel));
						System.out.println("  * --- off by " + offBy);
					}
				}
				else {
					
					Boolean guessedCorrect = result.equals(was);
					if (guessedCorrect) {
						
						numberOfCorrects ++;
						
					}
					if (outputEachRun) {
						System.out.println("  * Was: " + testDay.get_secondaryDay().get((SECONDARY_ATTRIBUTE) classLabel));
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
