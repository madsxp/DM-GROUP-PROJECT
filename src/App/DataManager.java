package App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import Models.Day;
import Models.EuroinvesterDay;
import Models.WeatherDay;

public class DataManager extends Data {

    public ArrayList<WeatherDay> weatherData;
    public ArrayList<EuroinvesterDay> euroinvesterData;
    private SimpleDateFormat dateParser;
    public HashMap<Date, Day> days;

    public DataManager() {

        dateParser = new SimpleDateFormat("yyyy-MM-dd");

    }

    public void fitAllWundergroundData(String[][] data, String[] headers) {

        if (data.length > 0) {
            weatherData = new ArrayList<WeatherDay>();

            for (String[] row : data) {

                weatherData.add(fitWundergroundEntry(row, headers));

            }
        }
    }

    // Create a WeatherDay object and fit data to it
    public WeatherDay fitWundergroundEntry(String[] data, String[] headers) {

        WeatherDay wd = new WeatherDay();

        // Events
        int index_events = Arrays.asList(headers).indexOf("Events");
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

        // Date
        int index_date = Arrays.asList(headers).indexOf("CET");
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

        // Temperature max
        int index_temperature_max = Arrays.asList(headers).indexOf("MaxTemperatureC");
        Double temperature_max = null;
        try {
            temperature_max = Double.parseDouble(data[index_temperature_max]);
        } catch (Exception e) {
        }

        if (temperature_max != null) {

            wd.set_temperature_max(temperature_max);

        }

        // Temperature min
        int index_temperature_min = Arrays.asList(headers).indexOf("MinTemperatureC");
        Double temperature_min = null;
        try {
            temperature_min = Double.parseDouble(data[index_temperature_min]);
        } catch (Exception e) {
        }

        if (temperature_max != null) {

            wd.set_temperature_min(temperature_min);

        }

        // Windspeed mean
        int index_windspeed_mean = Arrays.asList(headers).indexOf("MeanWindSpeedKm/h");
        Double windspeed_mean = null;
        try {
            windspeed_mean = Double.parseDouble(data[index_windspeed_mean]);
        } catch (Exception e) {
        }

        if (windspeed_mean != null) {

            wd.set_wind_speed_mean(windspeed_mean);

        }
        
        // Relative humidity
        int index_relative_humidity = Arrays.asList(headers).indexOf("MeanHumidity");
        Double relative_humidity = null;
        try {
            relative_humidity = Double.parseDouble(data[index_relative_humidity]);
        } catch (Exception e) {
            System.out.println(e);
        }

        if (relative_humidity != null) {

            wd.set_relative_humidity(relative_humidity);
        }

        return wd;
        
    }

    public void addAdditionalWeatherDataToDays(ArrayList<Day> days) {

        for (Day d : days) {

            addAdditionalWeatherDataToDay(d.weatherDay);

            //TODO: maybe update the weatherData array too?

            // like this? addAdditionalWeatherDataToDays(weatherData.get(d.weatherDay.id));

        }
    }

    public void addAdditionalWeatherDataToWeatherDays(ArrayList<WeatherDay> weatherDays) {

        for (WeatherDay wd : weatherDays) {

            addAdditionalWeatherDataToDay(wd);

        }
    }

    public void addAdditionalWeatherDataToDay(WeatherDay weatherDay) {

        // Calculate mean temperature
        weatherDay.set_temperature_mean((weatherDay.get_temperature_min() + weatherDay.get_temperature_max()) / 2);

        // Calculate wind chill factor
        weatherDay.set_wind_chill_factor(13.12 + 0.6215 * weatherDay.get_temperature_mean()
                - 13.96 * Math.pow(weatherDay.get_wind_speed_mean(), 0.16)
                + 0.4867 * weatherDay.get_temperature_mean()
                * Math.pow(weatherDay.get_wind_speed_mean(), 0.16));

        // Calculate heat index
        double mean_temperature = (weatherDay.get_temperature_mean())*9/5+32; // Celcius to Fahrenheit
        
        if (weatherDay.get_temperature_mean() > 26 && weatherDay.get_relative_humidity() > 40) {
             
        	double relative_humidity = weatherDay.get_relative_humidity();
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

    public void fitAllEuroinvesterData(String[][] data, String[] headers) {

        if (data.length > 0) {
            euroinvesterData = new ArrayList<EuroinvesterDay>();

            for (String[] row : data) {

                euroinvesterData.add(fitEuroinvesterEntry(row, headers));

            }
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

        days = new HashMap<Date, Day>();

        // weatherdays
        for (WeatherDay wd : weatherData) {

            Date date = wd.get_date();

            if (days.containsKey(date)) {

                System.out.println("tood.. contains date already");

            } else {

                Day day = new Day();
                day.weatherDay = wd;
                day.date = date;

                days.put(date, day);

            }

        }

        // euroinvesterdays
        for (EuroinvesterDay ed : euroinvesterData) {

            Date date = ed.get_date();

            if (days.containsKey(date)) {

                days.get(date).euroEuroinvesterDay = ed;

            } else {

                Day day = new Day();
                day.euroEuroinvesterDay = ed;
                day.date = date;

                days.put(date, day);

            }

        }

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
}
