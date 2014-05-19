package app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import models.Day;
import models.SecondaryDay;
import models.WeatherDay;
import app.Data.PROPERTY;

public class Apriori {
	
	// Minimum support
	private int minSupport;
	
	// Current size of pattern set
	int K = 1;
	
	// Days
	ArrayList<Day> days;

	ArrayList<ArrayList<PROPERTY>> dataSets;
	
	ArrayList<ArrayList<PROPERTY>> candidates;
	HashMap<Double, Boolean> candidatesHash;
	HashMap<ArrayList<PROPERTY>, Integer> supportCount;
	HashMap<ArrayList<PROPERTY>, Integer> previousSupportCount;
	
	public Apriori(ArrayList<Day> days) {
		
		this.days = days;
		convertDays(this.days, null);
		
	}
	
	public void setSpecificPropertySet(ArrayList<PROPERTY> propertyList) {
		
		convertDays(days, propertyList);
		
	}
	
	public void run(int minSupport) {
		
		this.minSupport = minSupport;
			
		System.out.println("---------------------------");
		System.out.println("Runnin apriori with minimum support of " + minSupport + ".");
		System.out.println("---------------------------\n");
	
		// Create initial candidates, of itemset size 1
		findCandidatesOfSize1();

		System.out.println("Item set size: " + (K) + ".");
		System.out.println("Candidates: " + candidates.size());
		System.out.println("Candidates with support: " + supportCount.size());			
		
		// Remove low support candidates
		removeLowSupportCandidates();
		
		System.out.println("Candidates with support after removing min. support: " + supportCount.size() + "\n");
		
		// Run until supportCount is 0
		while (supportCount.size() > 0) {
			
			// 1) Find new candidates from old candidates
			findCandidates();
			
			System.out.println("Item set size: " + (K) + ".");
			System.out.println("Candidates: " + candidates.size());
			
			// 2) Count candidates in itemset
			countCandidates();
			
			System.out.println("Candidates with support: " + supportCount.size());
						
			// 3) Remove low support candidates
			removeLowSupportCandidates();
			
			System.out.println("Candidates with support removing min. support: " + supportCount.size());
			
			if (true) {
				
				outputSupportCount();
			
			}

			System.out.println("");

		}

		// output the previous support count

		System.out.println("..DONE!\n");
		System.out.println("Item set size: " + (K-1) + ".");
		System.out.println("Candidates with support: " + previousSupportCount.size());
		System.out.println("\n# of days: " + days.size());
		System.out.println("\nCandidates: \n");
		outputPreviousSupportCount();
			
	}
	
	// Find the first set of candidates
	private void findCandidatesOfSize1() {
		
		supportCount = new HashMap<ArrayList<PROPERTY>, Integer>();
		candidates = new ArrayList<ArrayList<PROPERTY>>();
		candidatesHash = new HashMap<Double, Boolean>();
		
		for (ArrayList<PROPERTY> dataSet : dataSets) {
			
			for (PROPERTY item : dataSet) {
				
				ArrayList<PROPERTY> itemSet = new ArrayList<PROPERTY>();
				itemSet.add(item);
				
				if (!supportCount.containsKey(itemSet)) {
					
					supportCount.put(itemSet, 0);
					candidates.add(itemSet);
					candidatesHash.put(hashSum(itemSet), true);
					
				}
				
				supportCount.put(itemSet, supportCount.get(itemSet) + 1);
				
			}
		}
	}
	
	// Count/find new candidates from existing ones, by combining
	private void findCandidates() {
		
		K ++;
		
		ArrayList<ArrayList<PROPERTY>> newCandidates = new ArrayList<ArrayList<PROPERTY>>();
		HashMap<Double, Boolean> newCandidatesHash = new HashMap<Double, Boolean>();
		
		for (int i = 0; i < candidates.size(); i++) {
			
			for (int j = i+1; j < candidates.size(); j++) {
			
				ArrayList<PROPERTY> itemSet1 = candidates.get(i);
				ArrayList<PROPERTY> itemSet2 = candidates.get(j);
				
				ArrayList<ArrayList<PROPERTY>> combined = combine(itemSet1, itemSet2);

				for (ArrayList<PROPERTY> combination : combined) {
					
					if (newCandidatesHash.get(hashSum(combination)) == null) {
						
						newCandidates.add(combination);
						newCandidatesHash.put(hashSum(combination), true);
					
					}
				}
			}
		}
		
		candidatesHash = newCandidatesHash;
		candidates = newCandidates;
		
	}
	
	// For combining two candidates
	// Used when finding new candidates (of size +1)	
	private ArrayList<ArrayList<PROPERTY>> combine(ArrayList<PROPERTY> candidate1, ArrayList<PROPERTY> candidate2) {
		
		// Make a new ArrayList, for all the candidates produces by combining candidate1 and candidate2
		ArrayList<ArrayList<PROPERTY>> combined = new ArrayList<ArrayList<PROPERTY>>();
		
		// Go through candidate2's properties, and combine each one with candidate1
		for (int i = 0; i < candidate2.size(); i++) {
			
			PROPERTY p1 = candidate2.get(i);
			
			outer : {
				// Check that the property is not in candidate1 already
				if (!candidate1.contains(p1)) {
					
					// Pruning
					// Create candidate1 variations (subsets) with property p1, and check that each one is already in the candidates
					for (int j = 0; j < candidate1.size(); j++) {
						
						// Make a variation (checker)
						ArrayList<PROPERTY> checker = new ArrayList<PROPERTY>(candidate1);
						checker.set(j, p1);
						
						// Check to see if this variation is in our candidates
						if (candidatesHash.get(hashSum(checker)) == null) {
							// If not, there is no point in adding this combination to our new candidates
							break outer;
						}
					}
					
					// Make this combination and add it to the list of candidates
					ArrayList<PROPERTY> candidateC = new ArrayList<PROPERTY>(candidate1);
					
					candidateC.add(p1);
					
					combined.add(candidateC);
					
				}
			}
		}
		// Return list of candidates
		return combined;
	}	
	
	
	// Remove candidates with support below min support
	// The candidates are removed from supportCount, candidates and candidatesHash
	private void removeLowSupportCandidates() {
		
		ArrayList<ArrayList<PROPERTY>> lowSupportCountCandidates = new ArrayList<ArrayList<PROPERTY>>();
		
		for (ArrayList<PROPERTY> candidate : supportCount.keySet()) {
			
			if (supportCount.get(candidate) < minSupport) {
				
				lowSupportCountCandidates.add(candidate);
				
			}	
		}
		
		for (ArrayList<PROPERTY> lowSupportCountCandidate : lowSupportCountCandidates) {
			
			supportCount.remove(lowSupportCountCandidate);
			candidates.remove(lowSupportCountCandidate);
			candidatesHash.remove(hashSum(lowSupportCountCandidate));
			
		}
	}
	
	// Count support for each candidate
	private void countCandidates() {
		
		previousSupportCount = supportCount;
		
		supportCount = new HashMap<ArrayList<PROPERTY>, Integer>();
		
		for (ArrayList<PROPERTY> dataSet : dataSets) {
		
			for (ArrayList<PROPERTY> itemSet : candidates) {
			
				if (dataSet.containsAll(itemSet)) {

					if (!supportCount.containsKey(itemSet)) {
					
						supportCount.put(itemSet, 0);
						
					}
					
					supportCount.put(itemSet, supportCount.get(itemSet) + 1);
					
				}				
			}
		}		
	}
	
	private void outputSupportCount() {
		
		for (ArrayList<PROPERTY> itemSet : supportCount.keySet()) {
			
			System.out.println(itemSet + " : " + supportCount.get(itemSet));
			
		}	
	}
	
	private void outputPreviousSupportCount() {
	
		for (ArrayList<PROPERTY> itemSet : previousSupportCount.keySet()) {
			
			System.out.println(itemSet + " : " + previousSupportCount.get(itemSet));
			
		}	
	}
	
	// Return the sum of hash values in a candidate (hash value of each property)
	// Used for faster checking if a candidate exist already
	private double hashSum(ArrayList<PROPERTY> set) {
		
		hsum = 0;
		
		for (PROPERTY p : set) {
			
			hsum += p.hashCode();
			
		}
		return hsum;
	}
	
	double hsum = 0;
	
	public void printDataSetsLines(int from, int to) {
		
		for (int i = from; i < to; i++) {
			
			System.out.println(dataSets.get(i));
	
		}
		
	}
	
	private void convertDays(ArrayList<Day> days, ArrayList<PROPERTY> properties) {
		
		System.out.println("----------------------------------------" );
		System.out.println("Apriori");
		System.out.println("----------------------------------------" );
		System.out.println("  * Converting days to property arrays");
		System.out.println("----------------------------------------\n" );
		
		dataSets = new ArrayList<ArrayList<PROPERTY>>();
		
		int i = 0;
        for (Day day : days) {
            
        	ArrayList<PROPERTY> dataSet = new ArrayList<PROPERTY>();

        	SecondaryDay euroinvestorDay = day.get_secondaryDay();
        	WeatherDay weatherDay = day.get_weatherDay();
            
        	if (euroinvestorDay != null) {
	            for (PROPERTY property : euroinvestorDay.discreteValues) {
	            	
	            	if (properties == null || properties.indexOf(property) != -1) {
	            		dataSet.add(property);
	            	}
	            }
        	}
        	if (weatherDay != null) {
	            for (PROPERTY property : weatherDay.discreteValues) {
	            	
	            	if (properties == null || properties.indexOf(property) != -1) {
	            		dataSet.add(property);
	            	}
	            }
        	}
            dataSets.add(dataSet);
            
        }	
	}
	
	
	
}
