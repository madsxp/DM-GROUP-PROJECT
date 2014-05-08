package Models;

import java.util.ArrayList;
import java.util.Date;

public class WeatherDay extends App.Data {

    public int id;
    private Date date;
    private ArrayList<EVENT> events;
    private Double temperature_max;
    private Double temperature_min;
    private Double wind_speed_max;
    private Double wind_speed_mean;
    private Double humidity_min;
    private Double humidity_max;
    
    // post calculated data
    
    private Double heat_index;
    private Double temperature_mean; 
    private Double wind_chill_factor;

    
    // CONSTRUCTOR
    
    public WeatherDay() {

        id = weatherDayCount;
        weatherDayCount++;

    }
    
    // DATA TO STRING
    public String getReadableData() {
		
    	//TODO: Add the rest of the data
		return "\n"
				+ "   Temperature - Max : " + get_temperature_max() + "\n"; 
		
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
    
    
    
    public void set_temperature_mean(double data) {

        temperature_mean = data;

    }
    
    
    public void set_wind_chill_factor(double data) {

        wind_chill_factor = data;

    }

    public void set_heat_index(double data) {

        heat_index = data;
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

    public double get_heat_index() {

        double value = 0.0;
        try {
            value = heat_index;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message: heat_index not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED MEAN TEMPERATURE");
            
            value = get_temperature_mean();
            
        }
        return value;
    }

}
