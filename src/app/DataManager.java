package app;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.text.html.MinimalHTMLWriter;

import models.Day;
import models.EuroinvesterDay;
import models.WeatherDay;

public class DataManager extends Data {

    public ArrayList<WeatherDay> weatherData;
    public ArrayList<EuroinvesterDay> euroinvesterData;
    private SimpleDateFormat dateParser;
    public HashMap<Date, Day> days;
    public String[] headers;
    public Stats stats;
    

    public DataManager() {

        dateParser = new SimpleDateFormat("yyyy-MM-dd");
        stats = new Stats();
        
    }

    public void fitAllWundergroundData(String[][] data, String[] headers) {
    	
    	System.out.println("----------------------------------------" );
		System.out.println("Creating WeatherDays" );
		System.out.println("----------------------------------------" );
		
        if (data.length > 0) {
            weatherData = new ArrayList<WeatherDay>();
            
            int skipped = 0;

            for (String[] row : data) {
            	
            	WeatherDay wd = fitWundergroundEntry(row, headers);
            	if (wd != null) 
            		weatherData.add(wd);
            	else 
            		skipped++;

            }
            
            System.out.println("  * Skipped " + skipped + " day(s)");
    		System.out.println("----------------------------------------\n" );
            
        }
		
    }

    // Create a WeatherDay object and fit data to it
    public WeatherDay fitWundergroundEntry(String[] data, String[] headers) {
    	
    	// Create a new WeatherDay
        WeatherDay wd = new WeatherDay();
        
        // Errors in parsing
        int errors = 0;
        	
        // Add attributes from data
        
        // DATE
        int index_date = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.date));
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
        
        // EVENTS
        int index_events = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.events));
        String[] string_events = data[index_events].replaceAll("\\s+", "").split("-");
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
                case "tornado":
                    events.add(EVENT.TORNADO);
                    break;
                case "":
                    // empty string
                    break;
                default:
                    System.out.println("Unknown event : " + event);
                    System.out.println(data[index_events]);
                    System.out.println(data[0]);
                    break;

            }
        }

        wd.set_events(events);
        
        // TEMPERATURE MAX
        int index_temperature_max = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.temperature_max));
        Double temperature_max = null;
        try {
            temperature_max = Double.parseDouble(data[index_temperature_max]);
        } catch (Exception e) {
        	errors++;
        }

        if (temperature_max != null) {

            wd.set_temperature_max(temperature_max);

        }

        // TEMPERATURE MIN
        int index_temperature_min = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.temperature_min));
        Double temperature_min = null;
        try {
            temperature_min = Double.parseDouble(data[index_temperature_min]);
        } catch (Exception e) {
        	errors++;
        }

        if (temperature_max != null) {

            wd.set_temperature_min(temperature_min);

        }

        // WINDSPEED MAX
        int index_windspeed_max = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.wind_speed_max));
        Double windspeed_max = null;
        try {
            windspeed_max = Double.parseDouble(data[index_windspeed_max]);
        } catch (Exception e) {
        	errors++;
        }

        if (windspeed_max != null) {

            wd.set_wind_speed_max(windspeed_max);

        }
        
        // WINDSPEED MEAN
        int index_windspeed_mean = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.wind_speed_mean));
        Double windspeed_mean = null;
        try {
            windspeed_mean = Double.parseDouble(data[index_windspeed_mean]);
        } catch (Exception e) {
        	errors++;
        }

        if (windspeed_mean != null) {

            wd.set_wind_speed_mean(windspeed_mean);

        }
        
        // HUMIDITY MAX
        int index_humidity_max = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.humidity_max));
        Double humidity_max = null;
        try {
            humidity_max = Double.parseDouble(data[index_humidity_max]);
        } catch (Exception e) {
        	errors++;
        }

        if (humidity_max != null) {

            wd.set_humidity_max(humidity_max);
        }
        
        // HUMIDITY MIN
        int index_humidity_min = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.humidity_min));
        Double humidity_min = null;
        try {
            humidity_min = Double.parseDouble(data[index_humidity_min]);
        } catch (Exception e) {
        	errors++;
        }

        if (humidity_min != null) {

            wd.set_humidity_min(humidity_min);
        }
        
        // DEW POINT
        int index_dew_point = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.dew_point));
        Double dew_point = null;
        try {
        	dew_point = Double.parseDouble(data[index_dew_point]);
        } catch (Exception e) {
        	errors++;
        }

        if (dew_point != null) {

            wd.set_dew_point(dew_point);
        }
        
        // PRESSURE
        int index_pressure = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.pressure));
        Double pressure = null;
        try {
        	pressure = Double.parseDouble(data[index_pressure]);
        } catch (Exception e) {
        	errors++;
        }

        if (pressure != null) {

            wd.set_pressure(pressure);
        }
        
        // VISIBILITY
        int index_visibility = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.visibility));
        Double visibility = null;
        try {
        	visibility = Double.parseDouble(data[index_visibility]);
        } catch (Exception e) {
        	errors++;
        }

        if (visibility != null) {

            wd.set_visibility(visibility);
        }
        
        // GUST SPEED
        int index_gust_speed = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.gust_speed));
        Double gust_speed = null;
        try {
        	gust_speed = Double.parseDouble(data[index_gust_speed]);
        } catch (Exception e) {
        	errors++;
        }

        if (gust_speed != null) {

            wd.set_gust_speed(gust_speed);
        }
        
        // PRECIPITATION
        int index_precipitation = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.precipitation));
        Double precipitation = null;
        try {
        	precipitation = Double.parseDouble(data[index_precipitation]);
        } catch (Exception e) {
        	errors++;
        }

        if (precipitation != null) {

            wd.set_precipitation(precipitation);
        }
        
        // WIND DIRECTION
        int index_wind_direction = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.wind_direction));
        Double wind_direction = null;
        try {
        	wind_direction = Double.parseDouble(data[index_wind_direction]);
        } catch (Exception e) {
        	errors++;
        }

        if (wind_direction != null) {

            wd.set_wind_direction(wind_direction);
        }
        
        // CLOUD COVER
        int index_cloud_cover = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTE.cloud_cover));
        Double cloud_cover = null;
        try {
        	cloud_cover = Double.parseDouble(data[index_cloud_cover]);
        } catch (Exception e) {
        	errors++;
        }

        if (cloud_cover != null) {

            wd.set_cloud_cover(cloud_cover);
        }
        
        // TODO: 3?
        if (errors > 3) {
        	return null;
        }
        else {
        	return wd;
        }
    }

    public void addNoaaDataToDays(HashMap<Date, Day> days, String[][] noaaData) { 
    	
    	System.out.println("----------------------------------------" );
		System.out.println("Adding NOAA data to weather days");
		System.out.println("----------------------------------------" );
		System.out.println("  * Precipitation");
    	
		SimpleDateFormat noaaDateParser = new SimpleDateFormat("yyyyMMdd");
		
		for (int i = 1; i < noaaData.length; i++) {
			
			Date date = null;
			
			try {
				date = noaaDateParser.parse(noaaData[i][2].replace(" ", ""));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (date != null) {
				
				Day day = days.get(date);
				
				if (day != null) {
					
					Double precipitation = Double.parseDouble(noaaData[i][19].replaceAll("[^\\d.]", ""));
					// From inches to mm
					Double precipitationInMM = precipitation * 25.4;
					day.get_weatherDay().set_precipitation(precipitationInMM);
					
				}
				
				
				
			}
		}
		
		System.out.println("----------------------------------------\n" );
		
    }
    
    public void addAdditionalDataToEuroinvestorDay(ArrayList<EuroinvesterDay> euroinvestorDays) {
    	
    	System.out.println("----------------------------------------" );
		System.out.println("Adding additional data to EuroinvestorDays");
		System.out.println("----------------------------------------" );
		System.out.println("  * Price development");
		
		EuroinvesterDay previousDay = new EuroinvesterDay();
		previousDay.set_close(0);
		
        for (EuroinvesterDay ed : euroinvestorDays) {
        	
        	addAdditionalEuroinvestorDataToDay(ed, previousDay);
        	previousDay = ed;

        }
        
        System.out.println("----------------------------------------\n" );
        
    }
    
    public void addAdditionalEuroinvestorDataToDay(EuroinvesterDay euroinvestorDay, EuroinvesterDay previousDay) {
    	
    	// development
    	euroinvestorDay.set_development(euroinvestorDay.get_close() - previousDay.get_close());
    	
    	// positive development bool
    	// TODO: 0 is not possible
    	euroinvestorDay.set_positive_development(euroinvestorDay.get_development() >= 0);
    	
    }
    
    public void addAdditionalWeatherDataToWeatherDays(ArrayList<WeatherDay> weatherDays) {
    	
    	System.out.println("----------------------------------------" );
		System.out.println("Adding additional data to WeatherDays");
		System.out.println("----------------------------------------" );
		System.out.println("  * mean temperature");
		System.out.println("  * wind chill factor");
		System.out.println("  * heat index");
		
        for (WeatherDay wd : weatherDays) {
        	
            addAdditionalWeatherDataToDay(wd);

        }
        
        System.out.println("----------------------------------------\n" );
        
    }

    public void addAdditionalWeatherDataToDay(WeatherDay weatherDay) {
    	
    	// TEMPERATURE MEAN
    	weatherDay.set_temperature_mean((weatherDay.get_temperature_min() + weatherDay.get_temperature_max()) / 2);
    	
    	// WIND CHILL FACTOR
        weatherDay.set_wind_chill_factor(13.12 + 0.6215 * weatherDay.get_temperature_mean()
                - 13.96 * Math.pow(weatherDay.get_wind_speed_mean(), 0.16)
                + 0.4867 * weatherDay.get_temperature_mean()
                * Math.pow(weatherDay.get_wind_speed_mean(), 0.16));

        double mean_temperature = (weatherDay.get_temperature_mean())*9/5+32; // Celcius to Fahrenheit
        
        // HEAT INDEX
        if (weatherDay.get_temperature_mean() > 26 && weatherDay.get_humidity_max() > 40) {
             
        	double relative_humidity = weatherDay.get_humidity_max();
        	double c1 = -42.379;
        	double c2 = 2.04901523;
        	double c3 = 10.1433127;
        	double c4 = -0.22475541;
        	double c5 = -6.83783*Math.pow(10, -3);
        	double c6 = -5.481717*Math.pow(10, -2);
        	double c7 = 1.22874*Math.pow(10, -3);
        	double c8 = 8.5282*Math.pow(10, -4);
        	double c9 = -1.99*Math.pow(10, -6);
        	
        	double heatindex_in_Fahrenheit = 
        			c1 + 
        			(c2*mean_temperature) + 
        			(c3*relative_humidity) + 
        			(c4*mean_temperature*relative_humidity) + 
        			(c5*Math.pow(mean_temperature, 2)) + 
        			(c6*Math.pow(relative_humidity, 2))+
        			(c7*Math.pow(mean_temperature, 2)*relative_humidity)+
        			(c8*mean_temperature*Math.pow(relative_humidity, 2))+
        			(c9*Math.pow(relative_humidity, 2)*Math.pow(mean_temperature, 2));
            
            weatherDay.set_heat_index((heatindex_in_Fahrenheit-32)*5/9);
        
        }
    }
    
    public class Stats {
    	
    	// WeatherDay stats
    	public Double maxTempMax;
    	public Double maxTempMean;
    	public Double minTempMin;
    	public Double meanHumidity;
    	public Double meanPrecipitation;
    	public Double maxDewpoint;
    	public Double minDewpoint;
    	public Double maxGustspeed;
    	public Double minGustspeed;
    	public Double maxHeatindex;
    	public Double minHeatindex;
    	public Double maxCloudcover;
    	// return max for mean always
    	public Double maxHumidity;
    	public Double maxPrecipitation;
    	public Double maxPressure;
    	public Double maxVisibility;
    	public Double maxWindchillfactor;
    	public Double maxWinddirection;
    	public Double maxWindspeed;
    	public Double minCloudcover;
    	// return max min mean always
    	public Double minHumidity;
    	public Double minPrecipitation;
    	public Double minPressure;
    	public Double minVisibility;
    	public Double minWindchillfactor;
    	public Double minWinddirection;
    	public Double minWindspeed;
    	
    	// Euroinvestor stats
    	public Double maxDevelopment;
    	public Double minDevelopment;
    	public Double maxClose;
    	public Double minClose;
    	
    	public void setMax(WEATHERDAY_ATTRIBUTE attr, Double value) {
    		
    		switch (attr) {
	    		case cloud_cover:
	    			maxCloudcover = value;
	    			break;
	    		case dew_point:
	    			maxDewpoint = value;
	    			break;
	    		case gust_speed:
	    			maxGustspeed = value;
	    			break;
	    		case heat_index:
	    			maxHeatindex = value;
	    			break;
	    		case humidity_max:
	    		case humidity_min:
	    			maxHumidity = value;
	    			break;
	    		case precipitation:
	    			maxPrecipitation = value;
	    			break;
	    		case pressure:
	    			maxPressure = value;
	    			break;
	    		case temperature_max:
	    		case temperature_mean:
	    		case temperature_min:
	    			maxTempMax = value;
	    			break;
	    		case visibility:
	    			maxVisibility = value;
	    			break;
	    		case wind_chill_factor:
	    			maxWindchillfactor = value;
	    			break;
	    		case wind_direction:
	    			maxWinddirection = value;
	    			break;
	    		case wind_speed_max:
	    		case wind_speed_mean:
	    			maxWindspeed = value;
	    			break;
	    		default:
	    			System.out.println("error in stats.setMax(), attr: " + attr);
	    			break;
    		}    		
    	}
    	
    	public Double getMax(WEATHERDAY_ATTRIBUTE attr) {
    		
    		switch(attr) {
	    		case cloud_cover:
	    			return maxCloudcover;
	    		case dew_point:
	    			return maxDewpoint;
	    		case gust_speed:
	    			return maxGustspeed;
	    		case heat_index:
	    			return maxHeatindex;
	    		case humidity_max:
	    		case humidity_min:
	    			return maxHumidity;
	    		case precipitation:
	    			return maxPrecipitation;
	    		case pressure:
	    			return maxPressure;
	    		case temperature_max:
	    		case temperature_mean:
	    		case temperature_min:
	    			return maxTempMax;
	    		case visibility:
	    			return maxVisibility;
	    		case wind_chill_factor:
	    			return maxWindchillfactor;
	    		case wind_direction:
	    			return maxWinddirection;
	    		case wind_speed_max:
	    		case wind_speed_mean:
	    			return maxWindspeed;
	    		default:
	    			System.out.println("error in stats.getMax(), attr: " + attr);
	    			return 0.;
    		}
    	}
    	
    	public void setMax(EUROINVESTOR_ATTRIBUTE attr, Double value) {
    		
    		switch (attr) {
    			case development:
    				maxDevelopment = value;
    				break;
    			case close:
    				maxClose = value;
    				break;
    			default:
    				System.out.println("error in stats.setMax(), attr: " + attr);
    				break;
    				
    		}
    	}
    	
    	public Double getMax(EUROINVESTOR_ATTRIBUTE attr) {
    		
    		switch(attr) {
	    		case development:
	    			return maxDevelopment;
	    		case close:
	    			return maxClose;
	    		default:
	    			System.out.println("error in stats.getMin(), attr: " + attr);
	    			return 0.;
			
			}
    		
    	}
    	
    	public void setMin(WEATHERDAY_ATTRIBUTE attr, Double value) {
    		
    		switch (attr) {
	    		case cloud_cover:
	    			minCloudcover = value;
	    			break;
	    		case dew_point:
	    			minDewpoint = value;
	    			break;
	    		case gust_speed:
	    			minGustspeed = value;
	    			break;
	    		case heat_index:
	    			minHeatindex = value;
	    			break;
	    		case humidity_max:
	    		case humidity_min:
	    			minHumidity = value;
	    			break;
	    		case precipitation:
	    			minPrecipitation = value;
	    			break;
	    		case pressure:
	    			minPressure = value;
	    			break;
	    		case temperature_max:
	    		case temperature_mean:
	    		case temperature_min:
	    			minTempMin = value;
	    			break;
	    		case visibility:
	    			minVisibility = value;
	    			break;
	    		case wind_chill_factor:
	    			minWindchillfactor = value;
	    			break;
	    		case wind_direction:
	    			minWinddirection = value;
	    			break;
	    		case wind_speed_max:
	    		case wind_speed_mean:
	    			minWindspeed = value;
	    			break;
	    		default:
	    			System.out.println("error in stats.setMin(), attr: " + attr);   
	    			break;
    		}    		
    	}
    	public Double getMin(WEATHERDAY_ATTRIBUTE attr) {
    		
    		switch(attr) {
	    		case cloud_cover:
	    			return minCloudcover;
	    		case dew_point:
	    			return minDewpoint;
	    		case gust_speed:
	    			return minGustspeed;
	    		case heat_index:
	    			return minHeatindex;
	    		case humidity_max:
	    		case humidity_min:
	    			return minHumidity;
	    		case precipitation:
	    			return minPrecipitation;
	    		case pressure:
	    			return minPressure;
	    		case temperature_max:
	    		case temperature_mean:
	    		case temperature_min:
	    			return minTempMin;
	    		case visibility:
	    			return minVisibility;
	    		case wind_chill_factor:
	    			return minWindchillfactor;
	    		case wind_direction:
	    			return minWinddirection;
	    		case wind_speed_max:
	    		case wind_speed_mean:
	    			return minWindspeed;
	    		default:
	    			System.out.println("error in stats.getMin(), attr: " + attr);
	    			return 0.;
			}
    		
    	}
    	
    	public void setMin(EUROINVESTOR_ATTRIBUTE attr, Double value) {
    		
    		switch (attr) {
				case development:
					minDevelopment = value;
					break;
				case close:
					minClose = value;
					break;
				default:
					System.out.println("error in stats.setMin(), attr: " + attr);
					break;
				
    		}
    	}
    	
    	public Double getMin(EUROINVESTOR_ATTRIBUTE attr) {
    		
    		switch(attr) {
	    		case development:
	    			return minDevelopment;
	    		case close:
	    			return minClose;
	    		default:
	    			System.out.println("error in stats.getMin(), attr: " + attr);
	    			return 0.;
    		
    		}
    	}
    	

    }
    
    // Min, max, mean and so on
    public void calculateStats() {
    	
    	System.out.println("----------------------------------------" );
		System.out.println("Calculating stats");
		System.out.println("----------------------------------------" );
		System.out.println("  * Max temperature / day");
		System.out.println("  * Min temperature / day");
		System.out.println("  * Mean temperature / day");
		System.out.println("  * Mean humidity / day");
		System.out.println("  * Min dewpoint / day");
		System.out.println("  * Max dewpoint / day");
		System.out.println("  * Mean precipitation / day (when it rains)");
		System.out.println("----------------------------------------\n" );
		
    	Iterator it = days.entrySet().iterator();
        
    	// stats
    	stats.meanHumidity = 0.;
    	stats.meanPrecipitation = 0.;
    	
    	// Set all min and max values to positive and negative infinity
    	// Weatherday
    	for (WEATHERDAY_ATTRIBUTE attr : WEATHERDAY_ATTRIBUTE.values()) {
    		
    		if (get_weatherday_type(attr) == DATA_TYPE.numeric) {
    			stats.setMax(attr, Double.NEGATIVE_INFINITY);
    			stats.setMin(attr, Double.POSITIVE_INFINITY);
    		}
    	}
    	// Euroinvestorday
    	for (EUROINVESTOR_ATTRIBUTE attr : EUROINVESTOR_ATTRIBUTE.values()) {
    		
    		if (get_euroinvestor_type(attr) == DATA_TYPE.numeric) {
    			stats.setMax(attr, Double.NEGATIVE_INFINITY);
    			stats.setMin(attr, Double.POSITIVE_INFINITY);
    		}
    	}
  
        while (it.hasNext()) {
            
        	Map.Entry pairs = (Map.Entry)it.next(); 
            
            Day day = (Day) pairs.getValue();
            
            // mean humidity
            
            stats.meanHumidity += day.get_weatherDay().get_humidity_max();
            
            
            // Precipitation
            
            Double precipitation = day.get_weatherDay().get_precipitation();
            
            if (precipitation > 0 && precipitation != 99.99) {
            	
            	stats.meanPrecipitation += precipitation;
            	
            }
            
            // all max and min
            // Weatherday
            for (WEATHERDAY_ATTRIBUTE attr : WEATHERDAY_ATTRIBUTE.values()) {
            	
            	if (get_weatherday_type(attr) == DATA_TYPE.numeric) {
            		Double value = (Double) day.get_weatherDay().get(attr);
            		if (value > stats.getMax(attr)) {

            			stats.setMax(attr, value);
            			
                	}
            		if (value < stats.getMin(attr)) {
                		
            			stats.setMin(attr, value);
            			
                	}
            	}
            }
            // Euroinvestorday
            for (EUROINVESTOR_ATTRIBUTE attr : EUROINVESTOR_ATTRIBUTE.values()) {
            	
            	if (get_euroinvestor_type(attr) == DATA_TYPE.numeric) {
            		Double value = (Double) day.get_euroinvesterDay().get(attr);
            		if (value > stats.getMax(attr)) {
                		
            			stats.setMax(attr, value);
            			
                	}
            		if (value < stats.getMin(attr)) {
                		
            			stats.setMin(attr, value);
            			
                	}
            	}
            }
           
            
        }
        
        stats.meanPrecipitation = stats.meanPrecipitation / days.size();
        stats.meanHumidity = stats.meanHumidity / days.size();
        
    }

    public void fitAllEuroinvesterData(String[][] data, String[] headers) {

        if (data.length > 0) {
        	
        	int skipped = 0;
        	
        	System.out.println("----------------------------------------" );
    		System.out.println("Creating EuroinvestorDays" );
    		System.out.println("----------------------------------------" );
    		
            euroinvesterData = new ArrayList<EuroinvesterDay>();

            for (String[] row : data) {
            	
            	EuroinvesterDay ed = fitEuroinvesterEntry(row, headers);
            	if (ed != null)
            		euroinvesterData.add(ed);
            	else
            		skipped++;

            }
            
            System.out.println("  * Skipped " + skipped + " day(s)");
    		System.out.println("----------------------------------------\n" );
    		
        }
    }

    // Create a EuroinvesterDay object and fit data to it
    public EuroinvesterDay fitEuroinvesterEntry(String[] data, String[] headers) {

        EuroinvesterDay ed = new EuroinvesterDay();

        // Date
        int index_date = Arrays.asList(headers).indexOf("Date");
        Date date = new Date();

        try {
            date = dateParser.parse(data[index_date]);
        } catch (ParseException e) {
            date = null;
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (date != null) {

            ed.set_date(date);

        }

        // Close (price)
        int index_close = Arrays.asList(headers).indexOf("Close");
        Double close = 0.0;

        try {
            close = Double.parseDouble(data[index_close]);
        } catch (Exception e) {
        }

        if (close != null) {

            ed.set_close(close);

        }
        
        return ed;

    }

    public void addDiscreteValuesToDays() {
    	
    	System.out.println("----------------------------------------" );
		System.out.println("Adding discrete values for days");
		System.out.println("(for apriori)");
		System.out.println("----------------------------------------" );
		System.out.println("  * Price development");
		System.out.println("----------------------------------------\n" );
		
		Iterator it = days.entrySet().iterator();
        
        // Remove days that has no weather or euroinvestor data
        ArrayList<Date> toBeRemoved = new ArrayList<Date>();
        
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next(); 
            
            Day day = (Day) pairs.getValue();
            
            addDiscreteValuesToDay(day);
        
        }   
    }
    
    public void addDiscreteValuesToDay(Day day) {
    	
    	WeatherDay weatherDay = day.get_weatherDay();
    	EuroinvesterDay euroinvestorDay = day.get_euroinvesterDay();
    	
    	// Price development
       	if (euroinvestorDay.get_development() > 0) {
    		
    		euroinvestorDay.discreteValues.add(PROPERTY.price_increase);
    		
    	}
    	else if (euroinvestorDay.get_development() < 0) {
    		
    		euroinvestorDay.discreteValues.add(PROPERTY.price_decrease);
    		
    	}
    	else {
    		
    		euroinvestorDay.discreteValues.add(PROPERTY.price_no_change);
    		
    	}
       	
       	
    	// Temperature (max)
    	
    	double maxTemp = weatherDay.get_temperature_max();
    	
    	if (maxTemp < -15) {
    		
    		weatherDay.discreteValues.add(PROPERTY.TempMaxMinusInfinityToMinus15);
    		
    	}
    	else if (maxTemp < -10) {
    		
    		weatherDay.discreteValues.add(PROPERTY.TempMaxMinus15toMinus10);
    		
    	}
    	else if (maxTemp < -5) {
    		
    		weatherDay.discreteValues.add(PROPERTY.TempMaxMinus10toMinus5);
    		
    	}
		else if (maxTemp < 0) {
	    		
    		weatherDay.discreteValues.add(PROPERTY.TempMaxMinus5toZero);
    		
    	}
		else if (maxTemp < 5) {
			
			weatherDay.discreteValues.add(PROPERTY.TempMaxZeroTo5);
			
		}
		else if (maxTemp < 10) {
			
			weatherDay.discreteValues.add(PROPERTY.TempMax5To10);
			
		}
		else if (maxTemp < 15) {
			
			weatherDay.discreteValues.add(PROPERTY.TempMax10To15);
			
		}
		else if (maxTemp < 20) {
			
			weatherDay.discreteValues.add(PROPERTY.TempMax15To20);
			
		}
		else if (maxTemp < 25) {
			
			weatherDay.discreteValues.add(PROPERTY.TempMax20To25);
			
		}
		else if (maxTemp < 30) {
			
			weatherDay.discreteValues.add(PROPERTY.TempMax25To30);
			
		}
		else if (maxTemp < 35) {
			
			weatherDay.discreteValues.add(PROPERTY.TempMax30To35);
			
		}
		else if (maxTemp > 35) {
			
			weatherDay.discreteValues.add(PROPERTY.TempMax35ToInfinity);
			
		}   
    	
    	// Wind speed (max)
    	double windSpeed = weatherDay.get_wind_speed_max();
    	
    	if (windSpeed > 117) {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxHurricane);
    		
    	}
    	else if (windSpeed > 103) {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxViolentStorm);
    		
    	}
    	else if (windSpeed > 89) {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxStorm);
    		
    	}
    	else if (windSpeed > 75) {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxStrongGale);
    		
    	}
    	else if (windSpeed > 62) {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxFreshGale);
    		
    	}
    	else if (windSpeed > 50) {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxModerateGale);
    		
    	}
    	else if (windSpeed > 39) {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxStrongBreeeze);
    		
    	}
    	else if (windSpeed > 29) {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxFreshBreeze);
    		
    	}
    	else if (windSpeed > 20) {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxModerate);
    		
    	}
    	else if (windSpeed > 12) {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxGentleBreese);
    		
    	}
    	else if (windSpeed > 6) {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxLightBreeze);
    		
    	}
    	else if (windSpeed > 1) {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxLightBreeze);
    		
    	}
    	else {
    		
    		weatherDay.discreteValues.add(PROPERTY.SpeedMaxCalm);
    	}
    	
    	
    	// Wind direction
    	double windDirection = weatherDay.get_wind_direction();
    	
    	if (windDirection > 337.5) {
    		
    		// North
    		weatherDay.discreteValues.add(PROPERTY.directionNORTHERN);
    		
    	}
    	else if (windDirection > 292.5) {
    		
    		// North-East
    		weatherDay.discreteValues.add(PROPERTY.directionNORTHEASTERN);
    		
    	}
    	else if (windDirection > 247.5) {
    		
    		// East
    		weatherDay.discreteValues.add(PROPERTY.directionEASTERN);
    		
    	}
    	else if (windDirection > 202.5) {
    		
    		// South-East
    		weatherDay.discreteValues.add(PROPERTY.directionSOUTHEASTERN);
    		
    	}
    	else if (windDirection > 157.5) {
    		
    		// South
    		weatherDay.discreteValues.add(PROPERTY.directionSOUTHERN);
    		
    	}
    	else if (windDirection > 112.5) {
    		
    		// South-West
    		weatherDay.discreteValues.add(PROPERTY.directionSOUTHWESTERN);
    		
    	}
    	else if (windDirection > 67.5) {
    		
    		// West
    		weatherDay.discreteValues.add(PROPERTY.directionWESTERN);
    		
    	}
    	else if (windDirection > 22.5) {
    		
    		// North-West
    		weatherDay.discreteValues.add(PROPERTY.directionNORTHWESTERN);
    		
    	}
    	else {
    		
    		// North
    		weatherDay.discreteValues.add(PROPERTY.directionNORTHERN);
    		
    	}
    	
    	// Humidity
    	Double humidity = weatherDay.get_humidity_max();
    	
    	if ((humidity - stats.meanHumidity) > 1) {
    		
    		weatherDay.discreteValues.add(PROPERTY.humidityMaxHigh);
    		
    	}
    	else if ((humidity - stats.meanHumidity) < -1) {
    		
    		weatherDay.discreteValues.add(PROPERTY.humidityMaxLow);
    		
    	}
    	else {
    		
    		weatherDay.discreteValues.add(PROPERTY.humidityMaxMid);
    		
    	}
    	
    	// Precipitation
    	
    	Double precipitation = weatherDay.get_precipitation();
    	
    	if (precipitation == 99.99) {
    		  		
    	}
    	else if (precipitation > stats.meanPrecipitation + 20) {
    		
    		weatherDay.discreteValues.add(PROPERTY.high_rain);
    		
    	}
    	else if (precipitation > stats.meanPrecipitation - 20){
    		
    		weatherDay.discreteValues.add(PROPERTY.medium_rain);
    		
    	}
    	else if (precipitation > 0) { 
    		
    		weatherDay.discreteValues.add(PROPERTY.small_rain);
    		
    	}
    	else {
    		
    		weatherDay.discreteValues.add(PROPERTY.no_rain);
    		
    	}
    }
    
    public Double getNormalizedValue(Object attr, Double value) {
    	
    	switch (attr.getClass().getSimpleName()) {
    		
    		case "WEATHERDAY_ATTRIBUTE":
    			WEATHERDAY_ATTRIBUTE w_attr = (WEATHERDAY_ATTRIBUTE) attr;
    			if (get_weatherday_type(w_attr) == DATA_TYPE.numeric) {

    				return (value - stats.getMin(w_attr)) / (stats.getMax(w_attr) - stats.getMin(w_attr));
    				
    			}
    			break;
    		case "EUROINVESTOR_ATTRIBUTE":
    			EUROINVESTOR_ATTRIBUTE e_attr = (EUROINVESTOR_ATTRIBUTE) attr;
    			if (get_euroinvestor_type(e_attr) == DATA_TYPE.numeric) {
    				
    				return (value - stats.getMin(e_attr)) / (stats.getMax(e_attr) - stats.getMin(e_attr));
    				
    			}
    			break;
    		default:
    			break;
    	}
    	
    	System.out.println("Could not calculate normalized value fro attr " + attr);
    	return 0.0;
    			
    }
   
    public void createDays() {
    	
    	System.out.println("----------------------------------------" );
		System.out.println("Combining into Days");
		System.out.println("----------------------------------------" );
        
        days = new HashMap<Date, Day>();

        // WeatherDays
        for (WeatherDay wd : weatherData) {

            Date date = wd.get_date();

            if (days.containsKey(date)) {

                System.out.println("tood.. contains date already");

            } else {

                Day day = new Day();
                day.set_weatherDay(wd);
                day.date = date;

                days.put(date, day);

            }

        }

        // EuroinvestorDays
        for (EuroinvesterDay ed : euroinvesterData) {

            Date date = ed.get_date();

            if (days.containsKey(date)) {

                days.get(date).set_euroinvesterDay(ed);

            } else {

                Day day = new Day();
                day.set_euroinvesterDay(ed);
                day.date = date;

                days.put(date, day);

            }

        }
        
        Iterator it = days.entrySet().iterator();
        
        // Remove days that has no weather or euroinvestor data
        ArrayList<Date> toBeRemoved = new ArrayList<Date>();
        
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next(); 
            
            if (days.get(pairs.getKey()).get_weatherDay() == null || days.get(pairs.getKey()).get_euroinvesterDay() == null) {
            	
            	toBeRemoved.add((Date)pairs.getKey());
            	
            }
        }
        
        for (Date date : toBeRemoved) {
        	
        	days.remove(date);
        	
        }
        System.out.println("  * Removed " + toBeRemoved.size() + " days not overlapping");
        
        System.out.println("----------------------------------------\n" );
        
    }

    public Day getDay(int year, int month, int day) {
    	
        return days.get(date(year, month, day));

    }
    
    // ordered by date
    public ArrayList<Day> getDaysAsList() {
    	
    	ArrayList<Day> days_list = new ArrayList<Day>();
    	
    	Iterator it = days.entrySet().iterator();

        while (it.hasNext()) {
        
        	Map.Entry pairs = (Map.Entry)it.next();
        	days_list.add((Day) pairs.getValue());
             
        }
    	
        
        Collections.sort(days_list, new Comparator<Day>() {
			@Override
			public int compare(Day day1, Day day2) {
		        return day1.date.compareTo(day2.date);
		    }
        });
    	return days_list;
    	
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
    
//            //Shows data
//    public void showWeatherDayTable(ArrayList<WeatherDay> showData, String tableTitle) {
//        JTable table = new JTable(showData, headers);
//        JScrollPane scollPane = new JScrollPane(table);
//        JFrame frame = new JFrame(tableTitle);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(scollPane);
//        frame.pack();
//        frame.setVisible(true);
//        
//    }
//    
//    public String[][] convertListToString(ArrayList<WeatherDay> data) {
//        for
//        return
//    }
}
