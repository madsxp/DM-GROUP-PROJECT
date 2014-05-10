package App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Models.Day;
import Models.EuroinvesterDay;
import Models.WeatherDay;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
        int index_date = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.date));
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
        int index_events = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.events));
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
        int index_temperature_max = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.temperature_max));
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
        int index_temperature_min = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.temperature_min));
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
        int index_windspeed_max = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.wind_speed_max));
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
        int index_windspeed_mean = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.wind_speed_mean));
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
        int index_humidity_max = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.humidity_max));
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
        int index_humidity_min = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.humidity_min));
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
        int index_dew_point = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.dew_point));
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
        int index_pressure = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.pressure));
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
        int index_visibility = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.visibility));
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
        int index_gust_speed = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.gust_speed));
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
        int index_precipitation = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.precipitation));
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
        int index_wind_direction = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.wind_direction));
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
        int index_cloud_cover = Arrays.asList(headers).indexOf(get_weatherday_label(WEATHERDAY_ATTRIBUTES.cloud_cover));
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

    public void addAdditionalWeatherDataToDays(ArrayList<Day> days) {

        for (Day d : days) {

            addAdditionalWeatherDataToDay(d.get_weatherDay());

            //TODO: maybe update the weatherData array too?

            // like this? addAdditionalWeatherDataToDays(weatherData.get(d.weatherDay.id));

        }
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
    	
    	public Double maxTempMax;
    	public Double maxTempMean;
    	public Double minTempMin;
    	
    }
    
    // Min, max, mean and so on
    public void calculateStats() {
    	
    	Iterator it = days.entrySet().iterator();
        
    	// stats
    	stats.maxTempMax = Double.NEGATIVE_INFINITY;
    	stats.maxTempMean = Double.NEGATIVE_INFINITY;
    	stats.minTempMin = Double.POSITIVE_INFINITY;
    	
    	Day day2 = new Day();
    	
        while (it.hasNext()) {
            
        	Map.Entry pairs = (Map.Entry)it.next(); 
            
            Day day = (Day) pairs.getValue();
            
            // maxTempMax
            if (day.get_weatherDay().get_temperature_max() > stats.maxTempMax) {
            	
            	stats.maxTempMax = day.get_weatherDay().get_temperature_max();
            	
            }
            
            // maxTempMean
            if (day.get_weatherDay().get_temperature_mean() > stats.maxTempMean) {
            	
            	stats.maxTempMean = day.get_weatherDay().get_temperature_mean();
            	
            }
            
            // minTempMin
            if (day.get_weatherDay().get_temperature_min() < stats.minTempMin) {
            	
            	stats.minTempMin = day.get_weatherDay().get_temperature_min();
            	
            }
            
            
            
        }
        
        
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
