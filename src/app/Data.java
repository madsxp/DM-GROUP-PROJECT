package app;

import java.util.ArrayList;
import java.util.Date;

public class Data {

	String[] google_trends = new String[] { 
		"solbriller",
		"ol",
		"afbudsrejser",
		"vejrudsigt"
	};
	
    public static int weatherDayCount = 0;

    public enum EVENT {

        FOG,
        RAIN,
        SNOW,
        THUNDERSTORM,
        HAIL,
        TORNADO
    }
    
    public PROPERTY getEventAsDiscrete(EVENT event) {
    	
    	switch (event) {
    	
    		case FOG:
    			return PROPERTY.event_fog;
    		case HAIL:
    			return PROPERTY.event_hail;
    		case RAIN:
    			return PROPERTY.event_rain;
    		case SNOW:
    			return PROPERTY.event_snow;
    		case THUNDERSTORM:
    			return PROPERTY.event_thunderstorm;
    		case TORNADO:
    			return PROPERTY.event_tornado;
    		default:
    			return null;
    	}
    }
    
    public enum SECONDARY_ATTRIBUTE {
    	
    	date,
    	close,
    	development,
    	positive_development,
    	// Google trends
    	trend_afbudsrejser,
    	trend_solbriller
    	
    }
     
    public enum WEATHERDAY_ATTRIBUTE {

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
    
    enum DATA_TYPE {
    	
    	nominal,
    	numeric,
    	bool,
    	date,
    	no_type
    
    }
    
    public DATA_TYPE get_secondary_type(SECONDARY_ATTRIBUTE attr) {
    	
    	switch (attr) {
    	case date:
    		return DATA_TYPE.date;
    	case development:
    	case trend_afbudsrejser:
    	case trend_solbriller:
    	case close:
    		return DATA_TYPE.numeric;
    	default:
    		return DATA_TYPE.no_type;
    	}
    }
    
    public DATA_TYPE get_weatherday_type(WEATHERDAY_ATTRIBUTE attr) {
    	
    	switch (attr) {
    		
    	case date:
    		return DATA_TYPE.date;
    	case events:
    		return DATA_TYPE.nominal;
    	case cloud_cover:
    	case dew_point:
    	case gust_speed:
    	case heat_index:
    	case humidity_max:
    	case humidity_min:
    	case precipitation:
    	case pressure:
    	case temperature_max:
    	case temperature_mean:
    	case temperature_min:
    	case visibility:
    	case wind_chill_factor:
    	case wind_direction:
    	case wind_speed_max:
    	case wind_speed_mean:
    		return DATA_TYPE.numeric;   
    	default:
    		return DATA_TYPE.no_type;
    	}
    }
    
    public enum PROPERTY {

		// EUROINVESTOR
		// Price
		price_no_change,
		price_increase,
		price_decrease,
		
		// WEATHER
		// temperature
		temperature_freezing,
		temperature_cold,
		temperature_snug,
		temperature_warm,
		temperature_hot,
		temperature_very_hot,
		
//		TempMaxMinusInfinityToMinus15,
//		TempMaxMinus15toMinus10,
//		TempMaxMinus10toMinus5,
//		TempMaxMinus5toZero,
//		TempMaxZeroTo5,
//		TempMax5To10,
//		TempMax10To15,
//		TempMax15To20,
//		TempMax20To25,
//		TempMax25To30,
//		TempMax30To35,
//		TempMax35ToInfinity,
		
		// wind speed max
		wind_calm,
		wind_light_air,
		wind_light_breeze,
		wind_gentle_breeze,
		wind_moderate,
		wind_fresh_breeze,
		wind_strong_breeze,
		wind_moderate_gale,
		wind_fresh_gale,
		wind_strong_gale,
		wind_storm,
		wind_violent_storm,
		wind_hurricane,
		
		// wind direction
		wind_direction_N,
		wind_direction_NE,
		wind_direction_E,
		wind_direction_SE,
		wind_direction_S,
		wind_direction_SW,
		wind_direction_W,
		wind_direction_NW,
		
		// humidity
		high_humidity,
		med_humidity,
		low_humidity,
		
		// precipitation
		no_rain,
		small_rain,
		medium_rain,
		high_rain,
		
		// Gust speed
		low_gust_speed,
		med_gust_speed,
		high_gust_speed,
		
		// Cloud cover
		no_clouds,
		med_cloud_cover,
		high_cloud_cover,
		
		// Visibility
		low_visibility,
		med_visibility,
		high_visibility,
		
		// Pressure
		low_pressure,
		med_pressure,
		high_pressure,
		
		// Events
		event_fog,
		event_rain,
		event_snow,
		event_thunderstorm,
		event_hail,
		event_tornado,
		
		// Heat index
		feels_hotter,
		
		// Wind chill
		feels_colder,
		
		// sunny/cloudy
		sunny,
   	 	cloudy
   	 	
	}

    public String get_weatherday_label(WEATHERDAY_ATTRIBUTE attribute) {

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

    public enum DATA_MODEL {
    	
    	WeatherDay,
    	SecondaryDay
    	
    }
    
    public ArrayList<WEATHERDAY_ATTRIBUTE> getAllWeatherDayAttributes() {
    	
    	ArrayList<WEATHERDAY_ATTRIBUTE> attributes = new ArrayList<WEATHERDAY_ATTRIBUTE>();
    	
    	for (WEATHERDAY_ATTRIBUTE attr : WEATHERDAY_ATTRIBUTE.values()) {
    		
    		attributes.add(attr);
    		
    	}
    	
    	return attributes;
    	
    }

}

