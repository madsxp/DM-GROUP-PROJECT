package app;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import models.Day;

public class Visualization extends Data {
	
	private SimpleDateFormat dateParserSimple;
	private DecimalFormat df;
	
	public Visualization() {

		dateParserSimple = new SimpleDateFormat("yyyy-MM-dd EEE");
		df = new DecimalFormat("#.#");
		
	}

	public void showDaysTable(ArrayList<Day> days) {
		
		String[][] daysS = convertDaysToStringArray(days);
		// -1 because both has date
		int numOfcolumns = WEATHERDAY_ATTRIBUTE.values().length+SECONDARY_ATTRIBUTE.values().length-1;
		String[] headers = new String[numOfcolumns];
		
		int i = 0;
		for (SECONDARY_ATTRIBUTE attr : SECONDARY_ATTRIBUTE.values()) {
    		
    		headers[i] = attr.toString();
       		i++;  		
    		
    	}
		for (WEATHERDAY_ATTRIBUTE attr : WEATHERDAY_ATTRIBUTE.values()) {
			if (attr != WEATHERDAY_ATTRIBUTE.date) {
				headers[i] = attr.toString();
	    		i++;
			}
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
		
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	
		for (int k = 2; k < headers.length; k++) {
			
			table.getColumnModel().getColumn(k).setPreferredWidth(headers[k].length()*8);
			
		}
		
		// set min width of columns
		table.getColumnModel().getColumn(0).setPreferredWidth(110);
		table.getColumnModel().getColumn(3).setPreferredWidth(220);
				
		table.setRowHeight(30);
		table.setIntercellSpacing(new Dimension(0,0));
		
		JFrame frame = new JFrame("Days");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(scrollPane);
		
		Container c = frame.getContentPane();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        c.setPreferredSize(d);
        
		frame.pack();
	    frame.setVisible(true);
		
	}
	
	public void showStringArrayData(String[][] data, String[] headers) {
		
		JTable table;
		
		table = new JTable(data, headers);
		
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	
		for (int k = 2; k < headers.length; k++) {
			
			table.getColumnModel().getColumn(k).setPreferredWidth(headers[k].length()*8);
			
		}
		
		// set min width of columns
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
				
		table.setRowHeight(30);
		table.setIntercellSpacing(new Dimension(0,0));
		
		JFrame frame = new JFrame("Days");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(scrollPane);
		
		Container c = frame.getContentPane();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        c.setPreferredSize(d);
        
		frame.pack();
	    frame.setVisible(true);
		
	}
	
	public String[][] convertDaysToStringArray(ArrayList<Day> days) {		
		
		String[][] daysS = new String[days.size()][];
		
		int numOfcolumns = WEATHERDAY_ATTRIBUTE.values().length+SECONDARY_ATTRIBUTE.values().length-1;
		int j = 0;
		
       	for (Day day : days) {
            
        	String[] dayS = new String[numOfcolumns];
        	
        	int i = 0;
        	for (SECONDARY_ATTRIBUTE attr : SECONDARY_ATTRIBUTE.values()) {
        		
	        		String data;
	        		
	        		data = null;
	        		
	        		switch (get_secondary_type(attr)) {
		        		case date:
		        			data = dateParserSimple.format((Date) day.get_secondaryDay().get(attr));
		        			break;
		        		case nominal:
		        			data = day.get_secondaryDay().get(attr).toString();
		        			break;
		        		case numeric:
		        			data = df.format((Double) day.get_secondaryDay().get(attr)).toString();
		        			// exception for development. Add + symbol for clarity
		        			if (attr == SECONDARY_ATTRIBUTE.development && (Double) day.get_secondaryDay().get(attr) > 0) {
		        				
		        				data = "+" + data;
		        				
		        			}
		        			break;
	        			default:
	        				// TODO:
	        				break;
	        				
	        		}
	        		
	        		dayS[i] = data;
	        		i++;
        		
        	}
        	for (WEATHERDAY_ATTRIBUTE attr : WEATHERDAY_ATTRIBUTE.values()) {
        		if (attr != WEATHERDAY_ATTRIBUTE.date) {
	        		String data;
	        		
	        		data = null;
	        		
	        		switch (get_weatherday_type(attr)) {
	        		
		        		case date:
		        			data = dateParserSimple.format((Date) day.get_weatherDay().get(attr));
		        			break;
		        		case nominal:
		        			data = day.get_weatherDay().get(attr).toString();
		        			break;
		        		case numeric:
		        			data = df.format((Double) day.get_weatherDay().get(attr)).toString();
		        			break;
	        			default:
	        				// TODO:
	        				break;
	        				
	        		}
	        		
	        		dayS[i] = data;
	        		i++;
        		}
        	}
        	
        	
        	
        	daysS[j] = dayS;
        			
        	j++;
        }
        
		return daysS;
		
	}
	
	
}
