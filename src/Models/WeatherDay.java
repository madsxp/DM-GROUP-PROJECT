package Models;

import java.util.ArrayList;
import java.util.Date;

public class WeatherDay extends App.Data {
	
	public int id;
	private Date date;
	private ArrayList<EVENT> events;
	private Double temperature_max;
	private Double temperature_min;
	private Double wind_chill_factor;
	private Double wind_speed_max;
	private Double wind_speed_min;
	
	// calculated data
	private Double temperature_mean;
	private Double wind_speed_mean;
	
	// CONSTRUCTOR
	public WeatherDay() {
		
		id = weatherDayCount;
		weatherDayCount ++;
		
	}
	
	// SET
	public void set_events(ArrayList<EVENT> data) {
		
		events = data;
		
	}
	
	public void set_events(EVENT data) {
		
		events = new ArrayList<EVENT>(); 
		events.add(data);
		
	}
	
	public void set_date(Date data) {
		
		date = data;
		
	}
	
	public void set_temperature_max(double data) {
		
		temperature_max = data;
		
	}
	
	public void set_temperature_min(double data) {
		
		temperature_min = data;
		
	}
	
	public void set_wind_chill_factor(double data) {
		
		wind_chill_factor = data;
		
	}
	
	public void set_wind_speed_max(double data) {
		
		wind_speed_max = data;
		
	}
	
	public void set_wind_speed_min(double data) {
		
		wind_speed_min = data;
		
	}
	
	public void set_wind_speed_mean(double data) {
		
		wind_speed_mean = data;
		
	}
	
	// GET
	public ArrayList<EVENT> get_events() {
		
		ArrayList<EVENT> value = new ArrayList<EVENT>();
		try {
			value = events;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Message: events not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED NO EVENTS");
		}
		return value;		
	}
	
	public Date get_date() {
	
		Date value = new Date();
		try {
			value = date;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Message: date not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED WRONG DATE");
		}
		return value;
	}
	
	public double get_temperature_max() {
		
		double value = 0.0;
		try {
			value = temperature_max;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Message: Temperature_max not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
		}
		return value;
	}
	
	public double get_temperature_min() {
	
		double value = 0.0;
		try {
			value = temperature_min;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Message: Temperature_min not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
		}
		return value;
	}
	
	public double get_wind_speed_min() {
		
		double value = 0.0;
		try {
			value = wind_speed_min;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Message: wind_speed_min not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
		}
		return value;
	}
	
	public double get_wind_speed_max() {
		
		double value = 0.0;
		try {
			value = wind_speed_max;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Message: wind_speed_max not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
		}
		return value;
	}
	
	// calculated data
	
	public double get_wind_chill_factor() {
		
		double value = 0.0;
		try {
			value = wind_chill_factor;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Message: wind_chill_factor not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
		}
		return value;
	}
	
	public double get_wind_speed_mean() {
		
		double value = 0.0;
		try {
			value = wind_speed_mean;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Message: wind_speed_mean not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
		}
		return value;
	}
	
	public double get_temperature_mean() {
		
		double value = 0.0;
		try {
			value = temperature_mean;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Message: temperature_mean not set on WeatherDay id:" + this.id + " (" + this + ") RETURNED 0.0");
		}
		return value;
	}

	
}
