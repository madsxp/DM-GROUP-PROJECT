import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		
		CSVFileReader reader = new CSVFileReader();
		String[][] data = reader.read("data/weather.csv", false);
		String[] headers = reader.readHeaders("data/weather.csv");
		
	}
}
