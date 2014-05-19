package models;

import java.util.ArrayList;
import java.util.Date;

import app.Data.PROPERTY;

public class WeatherDay extends app.Data {

    public int id;
    private Date date;
    private ArrayList<EVENT> events; 	// Fog, Rain, Snow, Thunderstorm, Hail, Tornado
    private Double temperature_max; 	// Maximum temperature measured that day in degrees Celsius
    private Double temperature_min; 	// Minimum temperature measured that day in degrees Celsius
    private Double wind_speed_max; 		// Maximum wind speed measured in km/h
    private Double wind_speed_mean; 	// Average wind speed measured in km/h
    private Double humidity_min; 		// Minimum humidity measured in g/m3
    private Double humidity_max; 		// Maximum humidity measured in g/m3
    private Double dew_point; 			// Temperature at which the water vapor in air at constant barometric pressure condenses into liquid water
    private Double pressure; 			// Mean sea level pressure measured in h/Pa (hectopascal)
    private Double visibility; 			// Measured in km
    private Double gust_speed; 			// Measured in km/h
    private Double precipitation; 		// Measured in mm
    private Double wind_direction; 		// Measured in degrees zero degrees is north
    private Double cloud_cover; 		// Measured in 0-8 oktas where 0 is no clouds
    
    // post calculated data
    
    private Double heat_index; 			// Heat index is a derived value and is the factor we times with the degrees Celsius
    private Double temperature_mean; 	// Average temperature measured that day in degrees Celsius
    private Double wind_chill_factor; 	// Is a derived value and is the factor we times with the degrees Celsius

    // discrete values (for apriori)
 	public ArrayList<PROPERTY> discreteValues;
    
    // CONSTRUCTOR
    
    public WeatherDay() {

        id = weatherDayCount;
        weatherDayCount++;
        
        discreteValues = new ArrayList<PROPERTY>();
        
    }
    
    // DATA TO STRING
    public String getReadableData() {
		
    	//TODO: Add the rest of the data
		return "\n"
				+ "   Temperature - Max : " + get_temperature_max() + "\n"; 
		
	}
    
    public Object get(WEATHERDAY_ATTRIBUTE attr) {
    	
    	switch(attr) {
    	
	    	case cloud_cover:
	    		return get_cloud_cover();
	    	case date:
	    		return get_date();
	    	case dew_point:
	    		return get_dew_point();
	    	case events:
	    		return get_events();
	    	case gust_speed:
	    		return get_gust_speed();
	    	case heat_index:
	    		return get_heat_index();
	    	case humidity_max:
	    		return get_humidity_max();
	    	case humidity_min:
	    		return get_humidity_min();
	    	case precipitation:
	    		return get_precipitation();
	    	case pressure:
	    		return get_pressure();
	    	case temperature_max:
	    		return get_temperature_max();
	    	case temperature_mean:
	    		return get_temperature_mean();
	    	case temperature_min:
	    		return get_temperature_min();
	    	case visibility:
	    		return get_visibility();
	    	case wind_chill_factor:
	    		return get_wind_chill_factor();
	    	case wind_direction:
	    		return get_wind_direction();
	    	case wind_speed_max:
	    		return get_wind_speed_max();
	    	case wind_speed_mean:
	    		return get_wind_speed_mean();
	    	default:
	    		return null;
     	}
    }
    

    // DATE

    public void set_date(Date data) {

        date = data;

    }
    
    public Date get_date() {

        Date value = new Date();
        try {
            value = date;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: date not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED WRONG DATE");
        }
        return value;
    }
    
    // EVENTS
    
    
    public void set_events(ArrayList<EVENT> data) {

        events = data;

    }

    public void set_events(EVENT data) {

        events = new ArrayList<EVENT>();
        events.add(data);

    }

    public ArrayList<EVENT> get_events() {

        ArrayList<EVENT> value = new ArrayList<EVENT>();
        try {
            value = events;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: events not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED NO EVENTS");
        }
        return value;
    }

    
    // TEMPERATURE MAX
    
    public void set_temperature_max(double data) {

        temperature_max = data;

    }
    
    public double get_temperature_max() {

        double value = 0.0;
        try {
            value = temperature_max;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: Temperature_max not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }

    // TEMPERATURE MIN

    public void set_temperature_min(double data) {

        temperature_min = data;

    }
    
    public double get_temperature_min() {

        double value = 0.0;
        try {
            value = temperature_min;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: Temperature_min not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    // WIND SPEED MAX
    
    public void set_wind_speed_max(double data) {

        wind_speed_max = data;

    }
    
    public double get_wind_speed_max() {

        double value = 0.0;
        try {
            value = wind_speed_max;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: wind_speed_max not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }

    
    // WIND SPEED MEAN
    
    public void set_wind_speed_mean(double data) {

        wind_speed_mean = data;

    }
    
    public double get_wind_speed_mean() {

        double value = 0.0;
        try {
            value = wind_speed_mean;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: wind_speed_mean not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    // HUMIDITY MAX
    
    public void set_humidity_max(double data) {

        humidity_max = data;

    }
    
    public double get_humidity_max() {

        double value = 0.0;
        try {
            value = humidity_max;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: humidity_max not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    // HUMIDITY MIN
    
    public void set_humidity_min(double data) {

        humidity_min = data;

    }
    
    public double get_humidity_min() {

        double value = 0.0;
        try {
            value = humidity_min;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: humidity_min not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    // TEMPERATURE MEAN
    public void set_temperature_mean(double data) {

        temperature_mean = data;

    }
    
    public double get_temperature_mean() {

        double value = 0.0;
        try {
            value = temperature_mean;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: temperature_mean not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    // WIND CHILL FACTOR
    public void set_wind_chill_factor(double data) {

        wind_chill_factor = data;

    }

    public double get_wind_chill_factor() {

        double value = 0.0;
        try {
            value = wind_chill_factor;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: wind_chill_factor not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    // HEAT INDEX
    
    public void set_heat_index(double data) {

        heat_index = data;
    }

    public double get_heat_index() {

        double value = 0.0;
        try {
            value = heat_index;
        } catch (Exception e) {
            //e.printStackTrace();
            //System.err.println("Message: heat_index not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED MEAN TEMPERATURE");
            
            value = get_temperature_mean();
            
        }
        return value;
    }
    
    // DEW POINT    
    public double get_dew_point() {

        double value = 0.0;
        try {
            value = dew_point;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: dew_point not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    public void set_dew_point(double data) {

        dew_point = data;

    }
    
    // PRESSURE  
    public double get_pressure() {

        double value = 0.0;
        try {
            value = pressure;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: pressure not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    public void set_pressure(double data) {

        pressure = data;

    }

    // VISIBILITY
    public double get_visibility() {

        double value = 0.0;
        try {
            value = visibility;
        } catch (Exception e) {
            //e.printStackTrace();
            //System.err.println("Message: visibility not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    public void set_visibility(double data) {

        visibility = data;

    }
    
    // GUST SPEED
    public double get_gust_speed() {

        double value = 0.0;
        try {
            value = gust_speed;
        } catch (Exception e) {
            //e.printStackTrace();
            //System.err.println("Message: gust speed not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    public void set_gust_speed(double data) {

        gust_speed = data;

    }
    
    // PRECIPITATION
    public double get_precipitation() {

        double value = 0.0;
        try {
            value = precipitation;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: precipitation not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    public void set_precipitation(double data) {

    	precipitation = data;

    }
    
    // TODO: validate 0-360 degrees
    // WIND DIRECTION
    public double get_wind_direction() {

        double value = 0.0;
        try {
            value = wind_direction;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: wind direction not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    public void set_wind_direction(double data) {

    	wind_direction = data;

    }
    
    // CLOUD COVER
    public double get_cloud_cover() {

        double value = 0.0;
        try {
            value = cloud_cover;
        } catch (Exception e) {
            //e.printStackTrace();
            //System.err.println("Message: cloud cover not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
        }
        return value;
    }
    
    public void set_cloud_cover(double data) {

    	cloud_cover = data;

    }
    
}
