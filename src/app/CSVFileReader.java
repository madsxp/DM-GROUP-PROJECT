package app;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The CSVFileReader class is used to load a csv file
 * @author andershh and jang
 *
 */
public class CSVFileReader {
	/**
	 * The read method reads in a csv file as a two dimensional string array.
	 * Please note it is assumed that ';' is used as separation character.
	 * @param csvFile Path to file
	 * @param useNullForBlank Use empty string for missing values?
	 * @return Two dimensional string array containing the data from the csv file
	 * @throws IOException
	 */
	public static String[][] readWunderground(String csvFile, boolean useNullForBlank)
			throws IOException {
		
		System.out.println("----------------------------------------" );
		System.out.println("Reading Wunderground data" );
		System.out.println("----------------------------------------" );
		List<String[]> lines = new ArrayList<String[]>();
		
		BufferedReader bufRdr = new BufferedReader(new FileReader(new File(
				csvFile)));
		// read the header
		String line = bufRdr.readLine().replaceAll("\\s+","");
		
		StringTokenizer tok = new StringTokenizer(line, ",");
		
		final int numberOfColumns = tok.countTokens();

		// read each line of text file
		while ((line = bufRdr.readLine()) != null) {
			int col = 0;
			StringTokenizer st = new StringTokenizer(line, ",");

			String[] cols = line.split(",");
	
			String[] lineTokens = new String[numberOfColumns];
			while (col < numberOfColumns) {
				// get next token and store it in the array
				lineTokens[col] = cols[col];
				if (!useNullForBlank && lineTokens[col] == null)
					lineTokens[col] = "";
				col++;
			}

			lines.add(lineTokens);
		}
		String[][] ret = new String[lines.size()][];
		bufRdr.close();
	
		System.out.println("  * Read " + ret.length + " lines");
		System.out.println("----------------------------------------\n" );
		
		
		return lines.toArray(ret);
	}
	
	public static String[][] readNoaa(String csvFile, boolean useNullForBlank)
			throws IOException {
		
		System.out.println("----------------------------------------" );
		System.out.println("Reading Noaa data" );
		System.out.println("----------------------------------------" );
		List<String[]> lines = new ArrayList<String[]>();
		
		BufferedReader bufRdr = new BufferedReader(new FileReader(new File(
				csvFile)));
		// read the header
		String line = bufRdr.readLine();
		
		StringTokenizer tok = new StringTokenizer(line, ",");
		
		final int numberOfColumns = tok.countTokens();

		// read each line of text file
		while ((line = bufRdr.readLine()) != null) {
			int col = 0;

			String[] cols = line.split(",");
	
			String[] lineTokens = new String[numberOfColumns];
			while (col < numberOfColumns) {
				// get next token and store it in the array
				lineTokens[col] = cols[col];
				if (!useNullForBlank && lineTokens[col] == null)
					lineTokens[col] = "";
				col++;
			}

			lines.add(lineTokens);
		}
		String[][] ret = new String[lines.size()][];
		bufRdr.close();
	
		System.out.println("  * Read " + ret.length + " lines");
		System.out.println("----------------------------------------\n" );
		
		
		return lines.toArray(ret);
	}
	
	public static String[][] readGoogleTrends(String csvFile, boolean useNullForBlank)
			throws IOException {

		List<String[]> lines = new ArrayList<String[]>();
		
		BufferedReader bufRdr = new BufferedReader(new FileReader(new File(
				csvFile)));
		
		// skip past information in the start of document
		int infoLines = 4;
		for (int i = 0; i < infoLines; i++) {
			
			bufRdr.readLine();
			
		}
		
		// read the header
		String line = bufRdr.readLine();
		
		StringTokenizer tok = new StringTokenizer(line, ",");
		
		final int numberOfColumns = tok.countTokens();
		
		// read each line of text file
		while ((line = bufRdr.readLine()) != null) {
			
			if (line.length() == 0) {
				
				break;
				
			}
				
			int col = 0;

			String[] cols = line.split(",");
	
			String[] lineTokens = new String[numberOfColumns];
			while (col < numberOfColumns) {
				// get next token and store it in the array
				lineTokens[col] = cols[col];
				if (!useNullForBlank && lineTokens[col] == null)
					lineTokens[col] = "";
				col++;
			}

			lines.add(lineTokens);
		}
		String[][] ret = new String[lines.size()][];
		bufRdr.close();		
		
		return lines.toArray(ret);
	}
	
	
	public static String[][] readYahooFinanceData(String csvFile, boolean useNullForBlank)
			throws IOException {
		
		System.out.println("----------------------------------------" );
		System.out.println("Reading Euroinvester data" );
		System.out.println("----------------------------------------" );
		List<String[]> lines = new ArrayList<String[]>();

		BufferedReader bufRdr = new BufferedReader(new FileReader(new File(
				csvFile)));
		// read the header
		String line = bufRdr.readLine().replaceAll("\\s+","");
		
		StringTokenizer tok = new StringTokenizer(line, ",");
		
		final int numberOfColumns = tok.countTokens();

		// read each line of text file
		while ((line = bufRdr.readLine()) != null) {
			int col = 0;
			StringTokenizer st = new StringTokenizer(line, ",");

			String[] cols = line.split(",");
	
			String[] lineTokens = new String[numberOfColumns];
			while (col < numberOfColumns) {
				// get next token and store it in the array
				lineTokens[col] = cols[col];
				if (!useNullForBlank && lineTokens[col] == null)
					lineTokens[col] = "";
				col++;
			}

			lines.add(lineTokens);
		}
		String[][] ret = new String[lines.size()][];
		bufRdr.close();
	
		System.out.println("  * Read " + ret.length + " lines");
		System.out.println("----------------------------------------\n" );
		
		
		return lines.toArray(ret);
	}

	/**
	 * The read method reads in a csv file as a two dimensional string array.
	 * This method is utilizes the string.split method for splitting each line of the data file.
	 * @param csvFile File to load
	 * @param seperationChar Character used to seperate entries
	 * @param nullValue What to insert in case of missing values
	 * @return Data file content as a 2D string array
	 * @throws IOException
	 */
	public static String[][] readDataFile(String csvFile, String seperationChar, String nullValue) throws IOException
	{
		List<String[]> lines = new ArrayList<String[]>();

		BufferedReader bufRdr = new BufferedReader(new FileReader(new File(csvFile)));
		// read the header
		String line = bufRdr.readLine();
		
		while ((line = bufRdr.readLine()) != null) {
			String[] arr = line.split(seperationChar); 
			
			for(int i = 0; i < arr.length; i++)
			{
				if(arr[i].equals(""))
				{
					arr[i] = nullValue;
				}				
			}			
			lines.add(arr);
		}
		
		String[][] ret = new String[lines.size()][];
		bufRdr.close();
		return lines.toArray(ret);
	}
	
	public static String[] readHeaders(String csvFile) throws IOException {
		
		BufferedReader bufRdr = new BufferedReader(new FileReader(new File(csvFile)));
		// read the headers
		String line = bufRdr.readLine().replaceAll("\\s+","");
		
		String[] arr = line.split(",");
		
		return arr;
		
	}
	
	public static String[] readHeadersComma(String csvFile) throws IOException {
		
		BufferedReader bufRdr = new BufferedReader(new FileReader(new File(csvFile)));
		// read the headers
		String line = bufRdr.readLine();
		
		String[] arr = line.split(",");
		
		return arr;
		
	}
	
	
	
}
