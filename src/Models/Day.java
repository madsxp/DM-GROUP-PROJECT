package Models;
import java.util.Date;


public class Day {

	public Date date;
	public WeatherDay weatherDay;
	public EuroinvesterDay euroEuroinvesterDay;
	
	public String getReadableData() {
		
		return	
				"------------------------------------------------------\n"
		+ 		"Day\n"
		+ 		"Date: " + date.toString() + "\n"
		+		"Weather data: " + weatherDay.getReadableData()
		+		"Euroinvestor data: " + euroEuroinvesterDay.getReadableData()
		+		"-----------------------------------------------------\n";
		
	}
	
}
