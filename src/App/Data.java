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
        
        public enum APRIORI_TEMPERATURE {
            
                MAXMinusInfinityToMinus15,
                MAXMinus15toMinus10,
                MAXMinus10toMinus5,
                MAXMinus5toZero,
                MAXZeroTo5,
                MAX5To10,
                MAX10To15,
                MAX15To20,
                MAX20To25,
                MAX25To30,
                MAX30To35,
                MAX35ToInfinity
              
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
		    heat_index,
		    temperature_mean, 
		    wind_chill_factor,
	
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
				
			default:
				// TODO: handle this
				return "";
		
		}
		
	}
	
	
}
