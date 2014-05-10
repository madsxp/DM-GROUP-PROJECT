package App;

import java.util.ArrayList;
import java.util.Date;

public class Data {
	
	public static int weatherDayCount = 0;
	
	public enum EVENT {
		
		FOG,
		RAIN,
		SNOW,
		THUNDERSTORM,
		HAIL,
		TORNADO
		
	}
	
	public enum WEATHERDAY_ATTRIBUTES {
	
			date,
			events,
		    temperature_max,
		    temperature_min,
		    wind_speed_max,
		    wind_speed_mean,
		    humidity_min,
		    humidity_max,
		    dew_point,
		    heat_index,
		    temperature_mean, 
		    wind_chill_factor,
		    pressure,
		    visibility,
		    gust_speed,
		    precipitation,
		    wind_direction,
		    cloud_cover
	
	}
	
	public String get_weatherday_label(WEATHERDAY_ATTRIBUTES attribute) {
		
		switch (attribute) {
		
			case date:
				return "CET";
	
			case events:
				return "Events";
				
			case temperature_max:
				return "MaxTemperatureC";
			
			case temperature_min:
				return "MinTemperatureC";
			
			case wind_speed_max:
				return "MaxWindSpeedKm/h";
				
			case wind_speed_mean:
				return "MeanWindSpeedKm/h";
				
			case humidity_min:
				return "MinHumidity";
			
			case humidity_max:
				return "MaxHumidity";
				
			case dew_point:
				return "DewPointC";
				
			case pressure:
				return "MeanSeaLevelPressurehPa";
				
			case visibility:
				return "MeanVisibilityKm";
				
			case gust_speed:
				return "MaxGustSpeedKm/h";
				
			case precipitation:
				return "Precipitationmm";
				
			case wind_direction:
				return "WindDirDegrees";
				
			case cloud_cover:
				return "CloudCover";
				
			default:
				// TODO: handle this
				return "";
		
		}
	}
}
