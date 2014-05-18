package app;

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
    
    public enum SECONDARY_ATTRIBUTE {
    	
    	date,
    	close,
    	development,
    	positive_development
    	
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
		TempMaxMinusInfinityToMinus15,
        TempMaxMinus15toMinus10,
        TempMaxMinus10toMinus5,
        TempMaxMinus5toZero,
        TempMaxZeroTo5,
        TempMax5To10,
        TempMax10To15,
        TempMax15To20,
        TempMax20To25,
        TempMax25To30,
        TempMax30To35,
        TempMax35ToInfinity,
		
        // wind speed max
        SpeedMaxCalm,
        SpeedMaxLightAir,
        SpeedMaxLightBreeze,
        SpeedMaxGentleBreese,
        SpeedMaxModerate,
        SpeedMaxFreshBreeze,
        SpeedMaxStrongBreeeze,
        SpeedMaxModerateGale,
        SpeedMaxFreshGale,
        SpeedMaxStrongGale,
        SpeedMaxStorm,
        SpeedMaxViolentStorm,
        SpeedMaxHurricane,
        
        // wind direction
        directionNORTHERN,
        directionNORTHEASTERN,
        directionEASTERN,
        directionSOUTHEASTERN,
        directionSOUTHERN,
        directionSOUTHWESTERN,
        directionWESTERN,
        directionNORTHWESTERN,
        
        // humidity
        humidityMaxHigh,
        humidityMaxMid,
        humidityMaxLow,
        
        // precipitation
        no_rain,
   	 	small_rain,
   	 	medium_rain,
   	 	high_rain
        
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

}

