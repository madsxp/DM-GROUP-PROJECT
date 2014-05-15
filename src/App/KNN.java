package app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import models.Day;

public class KNN extends Data {

	private HashMap<Date, Day> trainingSet;
	private double[][] distances;
	private DataManager dataManager;
	
	private String classLabelType;
	
	public KNN(HashMap<Date, Day> trainingSet, DataManager dataManager) {
		
		this.trainingSet = trainingSet;
		this.dataManager = dataManager;
		
	}	
	
	// K-nearest neighbours
	public HashMap<String, Object> run(Day target, Object classLabel, int K) {

		System.out.println("----------------------------------------" );
		System.out.println("Running KNN" );
		System.out.println("----------------------------------------" );	
		
		System.out.println("  * Classifying: " + target.date);
		System.out.println("  * Class Label: " + classLabel);
		System.out.println("  * K: " + K);

		classLabelType = classLabel.getClass().getSimpleName();
		
		// ;
		
		setAllDistances(target, classLabel);
		
//		for (double[] pair : distances) {
//			
//			System.out.println(pair[0]);
//			
//		}
		
//		
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		result.put("newValue", countNeighbourClassLabel(K, classLabel));
//		// TODO: put additional info in the result
		
		System.out.println("----------------------------------------" );
		
		return null;
		
	}
	
//	// Count nearest neighbors classlabel value, and return the one with most occurrences
//	private Object countNeighbourClassLabel(int K, Object classLabel) {
//		
//		if (getType(classLabel) == DataType.NUMERIC) {
//			
//			double sum = 0;
//			
//			for (int i = 0; i < K; i++) {
//				
//				if (trainingSet.get((int) distances[i][1]).get(classLabel) != null) {
//				
//					sum += (double) trainingSet.get((int) distances[i][1]).get(classLabel);
//				
//				}
//			}
//			
//			return sum / K;
//			
//		}
//		else {
//				
//			HashMap<Object, Integer> counter = new HashMap<Object, Integer>();
//			
//			for (int i = 0; i < K; i++) {
//				
//				Answer neighbour = trainingSet.get((int) distances[i][1]);
//				
//				Object attrVal = neighbour.get(classLabel);
//				
//				if (!counter.containsKey(attrVal)) {
//					
//					counter.put(attrVal, 0);
//					
//				}
//				
//				counter.put(attrVal, counter.get(attrVal) + 1);
//				
//			}
//			
//			Iterator it = counter.entrySet().iterator();
//			
//			Object attrWithMostCounts = null;
//			int highestCount = 0;
//			
//			while (it.hasNext()) {
//				 
//				Map.Entry pairs = (Map.Entry)it.next();
//				
//				if ((int) pairs.getValue() > highestCount) {
//					
//					highestCount = (int) pairs.getValue();
//					attrWithMostCounts = pairs.getKey();
//					
//				}
//			}
//			
//			return attrWithMostCounts;
//			
//		}
//	}
//	
	// Set all distances from the target to all answers in the set
	private void setAllDistances(Day target, Object classLabel) {
		
		distances = new double[trainingSet.size()][2];
		
		Iterator it = trainingSet.entrySet().iterator();
		int n = 0;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next(); 
            
            distances[n] = new double[] { dayDistance((Day) pairs.getValue(), target, classLabel), (double) n };
            
            n++;
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
		
		for (WEATHERDAY_ATTRIBUTE attr : WEATHERDAY_ATTRIBUTE.values()) {
			
			if (!attr.equals(classLabel)) {
				
				Object attr1 = d1.get_weatherDay().get(attr);
				Object attr2 = d2.get_weatherDay().get(attr);
				
				if (attr1 != null && attr2 != null ) {
					
					if (attr1.getClass().isArray()) {
	
//						Object[] objects1 = (Object[]) attr1;
//						Object[] objects2 = (Object[]) attr2;
//						
//						double objsSum = 0;
//						double combinations = 0;
//						
//						for (Object obj1 : objects1) {
//							
//							for (Object obj2 : objects2) {
//								
//								objsSum +=  Math.pow(attrDistance(obj1, obj2, attr), 2);
//								combinations ++;
//								
//							}
//						}
//						
//						sum += Math.pow(objsSum / combinations, 2);
						
					}
					else {
					
						sum += Math.pow(attrDistance(attr1, attr2, attr, DATA_MODEL.WeatherDay), 2);
					
					}
				}
			}
		}
		
		return Math.sqrt(sum);
		
	}

	// Distance between two attributes
	private Double attrDistance(Object attr1, Object attr2, Object attr, DATA_MODEL data_model) {
		
		// If value is numeric, return Euclidean distance between the normalized (0.-1.1) values
		// Else, return 1 if the values are different and 0 if values are the same
		
		switch (data_model) {
		
			case EuroinvestorDay:
				break;
			case WeatherDay:
				if (get_weatherday_type((WEATHERDAY_ATTRIBUTE) attr) == DATA_TYPE.numeric) {
					// TODO: normalized
					return Math.abs(((double) attr1 - (double) attr2));
					
				}
				else {
					
					return attr1.equals(attr2) ? 0. : 1.;
					
				}	
			default:
		}	
		return null;
	}
	
}
