package app;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.QuickChart;
import com.xeiam.xchart.Series;
import com.xeiam.xchart.SeriesMarker;
import com.xeiam.xchart.SwingWrapper;

import models.Day;

public class App extends Data {

	DataManager dataManager;	
	
	public void run() throws IOException {

		// CSV file reader
		CSVFileReader reader = new CSVFileReader();
		
		// Load all our data sets
		String[][] wundergroundData = reader.readWunderground("data/weather.csv", false);
		String[] wundergroundHeaders = reader.readHeaders("data/weather.csv");
		
		String[][] yahooData = reader.readYahooFinanceData("data/OMX C20 and OMX C20 CAP.csv", false);
		String[] yahooHeaders = reader.readHeaders("data/OMX C20 and OMX C20 CAP.csv");
//		String[][] yahooData = reader.readYahooFinanceData("data/MAERSK-A.CO.csv", false);
//		String[] yahooHeaders = reader.readHeaders("data/MAERSK-A.CO.csv");
		
		String[][] noaaData = reader.readNoaa("data/weather_noaa.csv", false);
		String[] noaaHeaders = reader.readHeadersComma("data/weather_noaa.csv");
		
		// Find all google trend files in data directory
		File dir = new File("data");
		File[] matches = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith("google_trends_") && name.endsWith(".csv");
			}
		});
		
		for (File file : matches) {
	
			google_trends.add(file.getName().substring(14, file.getName().length()-4));
			
		}

		String[][] googleTrendsData = readAndCombineAllGoogleTrends(reader);
		
		// Data manager
		dataManager = new DataManager();
		
		dataManager.fitAllWundergroundData(wundergroundData, wundergroundHeaders);
		dataManager.fitAllSecondaryData(yahooData, yahooHeaders);       
                
		// Calculate stuff like mean, wind-chill-factor etc.
		dataManager.addAdditionalWeatherDataToWeatherDays(dataManager.weatherData);
		dataManager.addAdditionalDataToSecondaryDay(dataManager.secondaryData);
		
		// Combine weatherDays and secondaryDays into Days
		// Hash on Date
		dataManager.createDays();
		
		dataManager.addNoaaDataToDays(dataManager.days, noaaData);
		dataManager.addGoogleTrendsToSecondaryDays(googleTrendsData, getGoogleTrendsHeaders());
		
		// Min, Max, mean on all numeric attributes
		dataManager.calculateStats();
		
		// Add discrete values (for apriori)
		dataManager.addDiscreteValuesToDays();
		
		dataManager.calculateAllNormalizedValues();
		
		// date format: (year, month, date)
		
//		Visualization visualization = new Visualization();
//		
//		visualization.showStringArrayData(googleTrendsData, getGoogleTrendsHeaders());
		
		//visualization.showDaysTable(filterOutDaysWithoutYahoo(filterDaysWithWeatherData(dataManager.getDaysAsList())));
		
//		KNN knn = new KNN(dataManager);
//
//		ArrayList<Object> attributes = new ArrayList<Object>();
//		attributes.add(WEATHERDAY_ATTRIBUTE.precipitation);
//		knn.setRestrictedAttributes(attributes);
//		
//		runRandomKNN(knn, 500, SECONDARY_ATTRIBUTE.positive_development, 20, filterDaysWithWeatherData(filterOutDaysWithoutYahoo(dataManager.getDaysAsList())), false);
//		
//		ArrayList<Object> attributes = new ArrayList<Object>();
//		
//		attributes.add(WEATHERDAY_ATTRIBUTE.cloud_cover);
//		attributes.add(WEATHERDAY_ATTRIBUTE.dew_point);
//		attributes.add(WEATHERDAY_ATTRIBUTE.events);
//		attributes.add(WEATHERDAY_ATTRIBUTE.gust_speed);
//		attributes.add(WEATHERDAY_ATTRIBUTE.heat_index);
//		attributes.add(WEATHERDAY_ATTRIBUTE.humidity_max);
//		attributes.add(WEATHERDAY_ATTRIBUTE.precipitation);
//		attributes.add(WEATHERDAY_ATTRIBUTE.pressure);
//		attributes.add(WEATHERDAY_ATTRIBUTE.temperature_max);
//		attributes.add(WEATHERDAY_ATTRIBUTE.visibility);
//		attributes.add(WEATHERDAY_ATTRIBUTE.wind_chill_factor);
//		attributes.add(WEATHERDAY_ATTRIBUTE.wind_direction);
//		attributes.add(WEATHERDAY_ATTRIBUTE.wind_speed_mean);
//		attributes.add(WEATHERDAY_ATTRIBUTE.gust_speed);
		
		
		
//		for (WEATHERDAY_ATTRIBUTE attr : WEATHERDAY_ATTRIBUTE.values()) {
//			
//			ArrayList<Object> attributes = new ArrayList<Object>(Arrays.asList(WEATHERDAY_ATTRIBUTE.values()));
//			knn.setRestrictedAttributes(attributes);
//
//			runRandomKNN(knn, 10, attr, 20, filterDaysWithWeatherData(filterDaysWithTrends(dataManager.getDaysAsList())), false);
//			
//		}
		
//		for (String trend : google_trends) {
//			KNN knn = new KNN(dataManager);
//			ArrayList<Object> attributes = new ArrayList<Object>(Arrays.asList(WEATHERDAY_ATTRIBUTE.values()));
//			knn.setRestrictedAttributes(attributes);
//			runRandomKNN(knn, 500, trend, 10, filterDaysWithWeatherData(filterDaysWithTrends(dataManager.getDaysAsList())), false);
//		}
		//		
//		System.out.println(dataManager.stats.getMax(WEATHERDAY_ATTRIBUTE.precipitation));
//		System.out.println(dataManager.stats.getMin(WEATHERDAY_ATTRIBUTE.precipitation));
		
		// Apriori
		
//		ArrayList<Day> dataSet = filterOutDaysWithoutYahoo(filterDaysWithWeatherData(dataManager.getDaysAsList()));
//		
//		Apriori apriori = new Apriori(dataSet, null);
//		apriori.run(10, 3);
//		
//		ArrayList<PROPERTY> proplist = new ArrayList<PROPERTY>();
//		proplist.add(PROPERTY.price_decrease);
//		proplist.add(PROPERTY.price_increase);
//		proplist.add(PROPERTY.price_no_change);
//		
//		apriori.outputAssociationRulesOnlyWith(proplist);	

		
		
		// HER!!!!!!
//		ArrayList<Day> dataSet = filterDaysWithWeatherData(dataManager.getDaysAsList());
//		
//		Apriori apriori = new Apriori(dataSet, null);
//		apriori.setSpecificPropertySet(weatherProberties());
//		apriori.run(100, 0);
//		
//		apriori.outputAssociationRules();	
		
		//waitForInput();
		
		
//		ArrayList<String> trends = new ArrayList<String>();
//		trends.add("nespresso");
//		runAprioriOnTrend(trends, 10);
	
//		for (WEATHERDAY_ATTRIBUTE wa : WEATHERDAY_ATTRIBUTE.values()) {
//			
//			System.out.println("");
//			System.out.println(wa);
//			System.out.println("max: " + dataManager.stats.getMax(wa));
//			System.out.println("min: " + dataManager.stats.getMin(wa));
//			System.out.println("");
//		}
//		
		
		
		//showChart();
		//showOneYearTrend();
	}
	
	public void showChart() {
		
		// Create Chart
		Chart chart = new ChartBuilder().width(800).height(600).title("Year Scale").build();
		chart.getStyleManager().setLegendVisible(true);
		
		// generate data
		List<Date> solbriller_x = new ArrayList<Date>();
		List<Double> solbriller_y = new ArrayList<Double>();
		
		List<Date> close_x = new ArrayList<Date>();
		List<Double> close_y = new ArrayList<Double>();
		 		 
		for (Day day : filterOutDaysWithoutYahoo(filterDaysWithTrends(dataManager.getDaysAsList()))) {
			
			solbriller_x.add(day.date);
			solbriller_y.add(dataManager.getNormalizedValueOnDate(day.date, WEATHERDAY_ATTRIBUTE.temperature_max));
			
			close_x.add(day.date);
			close_y.add(dataManager.getNormalizedValueOnDate(day.date, SECONDARY_ATTRIBUTE.close));
			
		}
		
		Series series1 = chart.addSeries("Temperature", solbriller_x, solbriller_y);
		Series series2 = chart.addSeries("C20", close_x, close_y);
		
		series1.setMarker(SeriesMarker.NONE);
		series2.setMarker(SeriesMarker.NONE);
		
		// Show it
		new SwingWrapper(chart).displayChart();

	    
	}
	
	public void showOneYearTrend() {
		
		// Create Chart
		Chart chart = new ChartBuilder().width(800).height(600).title("Year Scale").build();
		chart.getStyleManager().setLegendVisible(true);
		
		// generate data
		List<Date> trend_x = new ArrayList<Date>();
		List<Double> trend_y = new ArrayList<Double>();
		 	
		ArrayList<Day> days = filterDaysWithTrends(dataManager.getDaysAsList());
		ArrayList<Day> twoYears = new ArrayList<Day>();
		
		for (int i = 0; i < 300; i++) {
		
			twoYears.add(days.get(i+880));
			
		}
		
		for (Day day : days) {
			
			trend_x.add(day.date);
			trend_y.add(day.get_trend("solbriller"));

		}
		
		Series series1 = chart.addSeries("Trend <solbriller>", trend_x, trend_y);
		
		series1.setMarker(SeriesMarker.NONE);
		
		// Show it
		new SwingWrapper(chart).displayChart();
	}
	
	public void runAprioriOnTrend(ArrayList<String> trends, int K) {
		
		ArrayList<Day> dataSet = filterDaysWithTrends(filterDaysWithWeatherData(dataManager.getDaysAsList()));
		
		Apriori apriori = new Apriori(dataSet, trends);		
		
		apriori.setSpecificPropertySet(weatherProberties());
		
		apriori.run(K, 3);
		
		apriori.outputAssociationRules(trends);
	}
	
	public ArrayList<Object> allAttributes() {
		
		ArrayList<WEATHERDAY_ATTRIBUTE> weatherdayAttributes = new ArrayList<WEATHERDAY_ATTRIBUTE>(Arrays.asList(WEATHERDAY_ATTRIBUTE.values()));
		ArrayList<SECONDARY_ATTRIBUTE> secondaryAttributes = new ArrayList<SECONDARY_ATTRIBUTE>(Arrays.asList(SECONDARY_ATTRIBUTE.values()));
		
		ArrayList<Object> allAttributes = new ArrayList<Object>();
		
		for (WEATHERDAY_ATTRIBUTE weatherdayAttribute : weatherdayAttributes) {
			
			allAttributes.add(weatherdayAttribute);
			
		}
		
		for (SECONDARY_ATTRIBUTE secondaryAttribute : secondaryAttributes) {
			
			allAttributes.add(secondaryAttribute);
			
		}
		
		return allAttributes;
		
	}
	
	public ArrayList<PROPERTY> weatherProberties() {
		
		ArrayList<PROPERTY> weatherProbs = new ArrayList<PROPERTY>(Arrays.asList(PROPERTY.values()));
		
		weatherProbs.remove(PROPERTY.price_decrease);
		weatherProbs.remove(PROPERTY.price_increase);
		weatherProbs.remove(PROPERTY.price_no_change);
		
		return weatherProbs;
	
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
	
	public ArrayList<Day> filterDaysWithTrends(ArrayList<Day> set) {
		
		ArrayList<Day> trainingSetOnlyTrends = new ArrayList<Day>();

		for (Day day : set) {

			if (day.google_trends.size() > 0) {
				
				trainingSetOnlyTrends.add(day);
				
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
		
		String[] headers = new String[google_trends.size()+1];
		
		headers[0] = "Date";
		
		for (int i = 1; i < google_trends.size()+1; i++) {
			
			headers[i] = google_trends.get(i-1);
			
		}
		
		return headers;
		
	}
	
	public String[][] readAndCombineAllGoogleTrends(CSVFileReader reader) throws IOException {
				
		String[][] googleTrends = null;
		
		for (int i = 0; i < google_trends.size(); i++) {
			
			String[][] trend = reader.readGoogleTrends("data/google_trends_" + google_trends.get(i) + ".csv", false);
			
			if (googleTrends == null) {
				
				googleTrends = new String[trend.length][google_trends.size()+1];
				
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
		else if (class_label_data_model_str.equals("String")) {
			
			class_label_data_model = DATA_MODEL.Trend;
			
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
			
			// WEATHER DAY
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
			// SECONDARY DAY
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
			// TREND
			else if (class_label_data_model == DATA_MODEL.Trend) {
				
				Object result = knn.run(testDay, classLabel, 200, outputEachRun);
				
				if (outputEachRun) {
	
					System.out.println("\n  * On day: " + testDay.date);
					System.out.println("  * Guessed: " + result);
					
				}
				
				Object was = testDay.get_trend((String) classLabel);
				
				Double offBy = Math.abs((Double) result - (Double) was);
				sum += offBy;
				if (outputEachRun) {
					System.out.println("  * Was: " + testDay.get_secondaryDay().get((SECONDARY_ATTRIBUTE) classLabel));
					System.out.println("  * --- off by " + offBy);
				}
			}
			
			if (outputEachRun) {
				System.out.println("----------------------------------------");
			}
			else {
				
				//System.out.println(i + "/" + numOfRuns);
				
			}
		}
		
		System.out.println("Class label: " + classLabel);
		System.out.println("K: " + K);
		
		if (sum>0) {
			
			System.out.println("\nOff by mean : " + sum/numOfRuns);
			
		}
		else {
		
			System.out.println("\nCorrects : " + numberOfCorrects);
			System.out.println(numberOfCorrects + "/" + numOfRuns);
			System.out.println(((double) numberOfCorrects / (double) numOfRuns * 100) + "%");
			
		}
		
		System.out.println("");
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
