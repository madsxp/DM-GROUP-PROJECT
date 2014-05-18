package app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import models.Day;

public class KNN extends Data {

	private ArrayList<Day> trainingSet;
	private double[][] distances;
	private DataManager dataManager;
	
	private ArrayList<WEATHERDAY_ATTRIBUTE> restrictedWeatherAttributes;
	private ArrayList<SECONDARY_ATTRIBUTE> restrictedSecondaryAttributes;
	
	
	public KNN(ArrayList<Day> trainingSet, DataManager dataManager) {
		
		this.trainingSet = trainingSet;
		this.dataManager = dataManager;
		
	}
	
	public KNN(DataManager dataManager) {
	
		this.dataManager = dataManager;
		
	}
	
	public void setTrainingSet(ArrayList<Day> trainingSet) {
		
		this.trainingSet = trainingSet;
		
	}
	
	public void setRestrictedAttributes(ArrayList<WEATHERDAY_ATTRIBUTE> weatherAttributes, ArrayList<SECONDARY_ATTRIBUTE> secondaryAttributes) {
		
		this.restrictedSecondaryAttributes = secondaryAttributes;
		this.restrictedWeatherAttributes = weatherAttributes;
		
	}
	
	// K-nearest neighbours
	public Object run(Day target, Object classLabel, int K, boolean showFeedback) {
		
		if (showFeedback) {
			System.out.println("----------------------------------------" );
			System.out.println("Running KNN" );
			System.out.println("----------------------------------------" );	
			System.out.println("  * Classifying: " + target.date);
			System.out.println("  * Class Label: " + classLabel);
			System.out.println("  * K: " + K);
		}
		
		// ;
		
		setAllDistances(target, classLabel);
		
//		for (double[] pair : distances) {
//			
//			System.out.println(pair[0]);
//			
//		}
		
		if (showFeedback) {
			System.out.println("----------------------------------------" );
		}
		return countNeighbourClassLabel(K, classLabel);
		
	}
	
//	// Count nearest neighbors classlabel value, and return the one with most occurrences or mean if numeric
	private Object countNeighbourClassLabel(int K, Object classLabel) {
		
		if (classLabel.getClass().getSimpleName().equals("WEATHERDAY_ATTRIBUTE")) {
			// if numeric
			if (get_weatherday_type((WEATHERDAY_ATTRIBUTE) classLabel) == DATA_TYPE.numeric) {
				
				Double sum = 0.;
				
				for (int i = 0; i < K; i++) {
				
					Day neighbour = trainingSet.get((int) distances[i][1]);
					sum += (Double) neighbour.get_weatherDay().get((WEATHERDAY_ATTRIBUTE) classLabel);
					
				}
				
				return sum/K;
				
			}
			// if nominal
			else {
					
				HashMap<Object, Integer> counter = new HashMap<Object, Integer>();
				
				for (int i = 0; i < K; i++) {
					
					Day neighbour = trainingSet.get((int) distances[i][1]);
					
					Object attrVal = neighbour.get_weatherDay().get((WEATHERDAY_ATTRIBUTE) classLabel);
					
					if (!counter.containsKey(attrVal)) {
						
						counter.put(attrVal, 0);
						
					}
					
					counter.put(attrVal, counter.get(attrVal) + 1);
					
				}
				
				Iterator it = counter.entrySet().iterator();
				
				Object attrWithMostCounts = null;
				int highestCount = 0;
				
				while (it.hasNext()) {
					 
					Map.Entry pairs = (Map.Entry)it.next();
					
					if ((int) pairs.getValue() > highestCount) {
						
						highestCount = (int) pairs.getValue();
						attrWithMostCounts = pairs.getKey();
						
					}
				}
				
				return attrWithMostCounts;
				
			}
		}
		else if (classLabel.getClass().getSimpleName().equals("SECONDARY_ATTRIBUTE")) {
			
			// if numeric
			if (get_secondary_type((SECONDARY_ATTRIBUTE) classLabel) == DATA_TYPE.numeric) {
				
				Double sum = 0.;
				
				for (int i = 0; i < K; i++) {
				
					Day neighbour = trainingSet.get((int) distances[i][1]);
					sum += (Double) neighbour.get_euroinvesterDay().get((SECONDARY_ATTRIBUTE) classLabel);
					
				}
				
				return sum/K;
				
			}
			// if nominal
			else {
					
				HashMap<Object, Integer> counter = new HashMap<Object, Integer>();
				
				for (int i = 0; i < K; i++) {
					
					Day neighbour = trainingSet.get((int) distances[i][1]);
					
					Object attrVal = neighbour.get_euroinvesterDay().get((SECONDARY_ATTRIBUTE) classLabel);
					
					if (!counter.containsKey(attrVal)) {
						
						counter.put(attrVal, 0);
						
					}
					
					counter.put(attrVal, counter.get(attrVal) + 1);
					
				}
				
				Iterator it = counter.entrySet().iterator();
				
				Object attrWithMostCounts = null;
				int highestCount = 0;
				
				while (it.hasNext()) {
					 
					Map.Entry pairs = (Map.Entry)it.next();
					
					if ((int) pairs.getValue() > highestCount) {
						
						highestCount = (int) pairs.getValue();
						attrWithMostCounts = pairs.getKey();
						
					}
				}
				
				return attrWithMostCounts;
				
			}
			
		}
		else {
			
			System.out.println("Wrong class label type");
			return 0;
			
		}
	}
	
	// Set all distances from the target to all answers in the set
	private void setAllDistances(Day target, Object classLabel) {
		
		distances = new double[trainingSet.size()][2];
		
		for (int n = 0; n < trainingSet.size(); n++) {
        
            distances[n] = new double[] { dayDistance(trainingSet.get(n), target, classLabel), (double) n };

		}
		// Sort distances in ascending order
 		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances.length - i - 1; j++) {
				if(distances[j][0] > distances[j+1][0]) {
					double[] temp = distances[j];
					distances[j] = distances[j+1];
					distances[j+1] = temp;
				}
			}
		}
	}

	// Distance between two days
	private double dayDistance(Day d1, Day d2, Object classLabel) {
		
		double sum = 0;
		
		ArrayList<WEATHERDAY_ATTRIBUTE> WeatherDayAttributes;
		
		if (restrictedWeatherAttributes != null) {
			
			WeatherDayAttributes  = restrictedWeatherAttributes;
			
		}
		else {
		
			WeatherDayAttributes = getAllWeatherDayAttributes();
			
		}
		
		for (WEATHERDAY_ATTRIBUTE attr : WeatherDayAttributes) {
			
			if (!attr.equals(classLabel)) {
				// Don't use any temperature attribute if classLabel is one
				if (classLabel.getClass().getSimpleName().equals("WEATHERDAY_ATTRIBUTE") && !(((WEATHERDAY_ATTRIBUTE) classLabel == WEATHERDAY_ATTRIBUTE.temperature_max || (WEATHERDAY_ATTRIBUTE) classLabel == WEATHERDAY_ATTRIBUTE.temperature_mean || (WEATHERDAY_ATTRIBUTE) classLabel == WEATHERDAY_ATTRIBUTE.temperature_min) && (attr == WEATHERDAY_ATTRIBUTE.temperature_max || attr == WEATHERDAY_ATTRIBUTE.temperature_mean || attr == WEATHERDAY_ATTRIBUTE.temperature_min))) {
					
					Object attr1 = d1.get_weatherDay().get(attr);
					Object attr2 = d2.get_weatherDay().get(attr);
					
					if (attr1 != null && attr2 != null ) {
						
						if (attr1.getClass().getSimpleName().equals("ArrayList")) {
							
							ArrayList objects1 = (ArrayList) attr1;
							ArrayList objects2 = (ArrayList) attr2;
							
							double objsSum = 0;
							double combinations = 0;
							
							for (Object obj1 : objects1) {
								
								for (Object obj2 : objects2) {
									
									objsSum +=  Math.pow(attrDistance(obj1, obj2, attr, DATA_MODEL.WeatherDay, d1.date), 2);
									combinations ++;
									
								}
							}
							if (combinations != 0) {
								
								sum += Math.pow(objsSum / combinations, 2);
								
							}							
						}
						else {
							
							sum += Math.pow(attrDistance(attr1, attr2, attr, DATA_MODEL.WeatherDay, d1.date), 2);
						
						}
					}
				}
			}
		}
		
//		for (EUROINVESTOR_ATTRIBUTE attr : EUROINVESTOR_ATTRIBUTE.values()) {
//			
//			if (!attr.equals(classLabel)) {
//				
//				Object attr1 = d1.get_euroinvesterDay().get(attr);
//				Object attr2 = d2.get_euroinvesterDay().get(attr);
//				
//				if (attr1 != null && attr2 != null ) {
//					
//					if (attr1.getClass().getSimpleName().equals("ArrayList")) {
//						
//						ArrayList objects1 = (ArrayList) attr1;
//						ArrayList objects2 = (ArrayList) attr2;
//						
//						double objsSum = 0;
//						double combinations = 0;
//						
//						for (Object obj1 : objects1) {
//							
//							for (Object obj2 : objects2) {
//								
//								objsSum +=  Math.pow(attrDistance(obj1, obj2, attr, DATA_MODEL.EuroinvestorDay), 2);
//								combinations ++;
//								
//							}
//						}
//						if (combinations != 0) {
//							
//							sum += Math.pow(objsSum / combinations, 2);
//							
//						}							
//					}
//					else {
//						
//						sum += Math.pow(attrDistance(attr1, attr2, attr, DATA_MODEL.EuroinvestorDay), 2);
//					
//					}
//				}
//			}
//		}
		return Math.sqrt(sum);
		
	}

	// Distance between two attributes
	private Double attrDistance(Object attr1, Object attr2, Object attr, DATA_MODEL data_model, Date date) {
		
		// If value is numeric, return Euclidean distance between the normalized (0.-1.1) values
		// Else, return 1 if the values are different and 0 if values are the same
		
		switch (data_model) {
		
			case SecondaryDay:
				if (get_secondary_type((SECONDARY_ATTRIBUTE) attr) == DATA_TYPE.numeric) {
					
					//return Math.abs(dataManager.calculateNormalizedValue(attr, (double) attr1) - dataManager.calculateNormalizedValue(attr, (double) attr2));
					return Math.abs(dataManager.getNormalizedValueOnDate(date, attr1) - dataManager.getNormalizedValueOnDate(date, attr1));
					
				}
				else {
					
					return attr1.equals(attr2) ? 0. : 1.;
					
				}	
			case WeatherDay: 
				if (get_weatherday_type((WEATHERDAY_ATTRIBUTE) attr) == DATA_TYPE.numeric) {
					
					return Math.abs(dataManager.calculateNormalizedValue(attr, (double) attr1) - dataManager.calculateNormalizedValue(attr, (double) attr2));
					
				}
				else {
					
					return attr1.equals(attr2) ? 0. : 1.;
					
				}	
			default:
		}	
		return null;
	}
	
}
