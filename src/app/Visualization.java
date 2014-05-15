package app;

import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import app.Data.WEATHERDAY_ATTRIBUTE;
import models.Day;

public class Visualization {
	
	private SimpleDateFormat dateParser;
	private SimpleDateFormat dateParserSimple;
	
	public Visualization() {
		
		dateParser = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		dateParserSimple = new SimpleDateFormat("yyyy-MM-dd EEE");
		
	}
	
	public void showTable(HashMap<Date, Day> days) {
		
		String[][] daysS = convertDaysToStringArray(days);
		String[] headers = new String[WEATHERDAY_ATTRIBUTE.values().length];
		
		int i = 0;
		for (WEATHERDAY_ATTRIBUTE attr : WEATHERDAY_ATTRIBUTE.values()) {
    		
			headers[i] = attr.toString();
    		i++;
			
    	}
		
		Arrays.sort(daysS, new Comparator<String[]>() {
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
				Date time1 = null;
				try {
					time1 = dateParserSimple.parse(entry1[0]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date time2 = null;
				try {
					time2 = dateParserSimple.parse(entry2[0]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (time1 != null && time2 != null) {
					
					return time1.compareTo(time2);
					
				}
				else {
					
				 return 0;	
				}
				
			}
        });
		
		JTable table;
		
		table = new JTable(daysS, headers);
		
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
	
		JFrame frame = new JFrame("Days");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(scrollPane);
		
		frame.pack();
	    frame.setVisible(true);
		
	}
	
	public String[][] convertDaysToStringArray(HashMap<Date, Day> days) {		
		
		String[][] daysS = new String[days.size()][];
		
		Iterator it = days.entrySet().iterator();
		int numOfcolumns = WEATHERDAY_ATTRIBUTE.values().length;
		int j = 0;
		
        while (it.hasNext()) {
            
        	Map.Entry pairs = (Map.Entry)it.next(); 
        	Day day = (Day) pairs.getValue();
        	String[] dayS = new String[numOfcolumns];
        	
        	int i = 0;
        	for (WEATHERDAY_ATTRIBUTE attr : WEATHERDAY_ATTRIBUTE.values()) {
        		
        		String data;
        		
        		if (i == 0) {
        			
        			data = dateParserSimple.format((Date) day.get_weatherDay().get(attr));
        			
        		}
        		else {
        			
        			data = day.get_weatherDay().get(attr).toString();
        			
        		}
        		
        		dayS[i] = data;
        		i++;
        		
        	}
        	
        	daysS[j] = dayS;
        			
        	j++;
        }
        
		return daysS;
		
	}
	
	
}
