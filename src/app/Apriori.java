package app;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

	ArrayList<ArrayList<String>> dataSets;
	
	ArrayList<ArrayList<String>> candidates;
	HashMap<Double, Boolean> candidatesHash;
	HashMap<Double, Integer> allSupportCounts;
	HashMap<ArrayList<String>, Integer> supportCount;
	HashMap<ArrayList<String>, Integer> previousSupportCount;

	ArrayList<AssociationRule> associationRules;
	
	ArrayList<String> trends;
	 
	public Apriori(ArrayList<Day> days, ArrayList<String> trends) {
		
		this.days = days;
		this.trends = trends;
		convertDays(this.days, null, trends);
		
	}
	
	public void setSpecificPropertySet(ArrayList<PROPERTY> propertyList) {
		
		convertDays(days, propertyList, trends);
		
	}
	
	public void run(int minSupport, int maxSetSize) {
		
		this.allSupportCounts = new HashMap<Double, Integer>();
		this.minSupport = minSupport;
		this.associationRules = new ArrayList<AssociationRule>();	
		
		System.out.println("----------------------------------------");
		System.out.println("Runnin apriori with minimum support of " + minSupport + ".");
		System.out.println("----------------------------------------\n");
		
		System.out.println("\n----------------- " + K + " --------------------\n");
		
		// Create initial candidates, of itemset size 1
		findCandidatesOfSize1();

		System.out.println("\n----------------------------------------\n");
		
		System.out.println("Item set size: " + (K) + ".");
		System.out.println("Candidates: " + candidates.size());
		System.out.println("Candidates with support: " + supportCount.size());			
		
		// Remove low support candidates
		removeLowSupportCandidates();
		outputSupportCount();
		
		System.out.println("Candidates with support after removing min. support: " + supportCount.size() + "\n");
		
		// Run until supportCount is 0
		while (supportCount.size() > 0) {
			
			if (K >= maxSetSize && maxSetSize != 0) {
				
				break;
				
			}
			
			System.out.println("\n----------------- " + K + " --------------------\n");
			
			System.out.println("Item set size: " + (K));
			
			// 1) Find new candidates from old candidates
			System.out.println("Finding candidates ...");
			findCandidates();
			
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
			
			int rulesGenerated = generateAssociationRules(supportCount);
			
			System.out.println("\nGenerated " + rulesGenerated + " new association rules");
			
			if (rulesGenerated == 0) {
				
//				System.out.println("stopped because no rules could be generated with confidence of 70% or more");
//				break;
				
			}

			System.out.println("\n----------------------------------------\n");
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
		
		supportCount = new HashMap<ArrayList<String>, Integer>();
		candidates = new ArrayList<ArrayList<String>>();
		candidatesHash = new HashMap<Double, Boolean>();
		
		for (ArrayList<String> dataSet : dataSets) {
			
			for (String item : dataSet) {
				
				ArrayList<String> itemSet = new ArrayList<String>();
				itemSet.add(item);
				Double hashSum = hashSum(itemSet);
				
				if (!supportCount.containsKey(itemSet)) {
					
					supportCount.put(itemSet, 0);
					candidates.add(itemSet);
					candidatesHash.put(hashSum, true);
					allSupportCounts.put(hashSum, 0);
					
				}
				
				supportCount.put(itemSet, supportCount.get(itemSet) + 1);
				allSupportCounts.put(hashSum, allSupportCounts.get(hashSum) + 1);
				
			}
		}
	}
	
	// Count/find new candidates from existing ones, by combining
	private void findCandidates() {
		
		K ++;
		
		ArrayList<ArrayList<String>> newCandidates = new ArrayList<ArrayList<String>>();
		HashMap<Double, Boolean> newCandidatesHash = new HashMap<Double, Boolean>();
		
		for (int i = 0; i < candidates.size(); i++) {
			
			for (int j = i+1; j < candidates.size(); j++) {
			
				ArrayList<String> itemSet1 = candidates.get(i);
				ArrayList<String> itemSet2 = candidates.get(j);
				
				ArrayList<ArrayList<String>> combined = combine(itemSet1, itemSet2);
				
				for (ArrayList<String> combination : combined) {
					
					Double hashSum = hashSum(combination);
					
					if (newCandidatesHash.get(hashSum) == null) {
						
						newCandidates.add(combination);
						newCandidatesHash.put(hashSum, true);
						allSupportCounts.put(hashSum, 0);
					
					}
				}
			}
		}
		
		candidatesHash = newCandidatesHash;
		candidates = newCandidates;
		
	}
	
	// For combining two candidates
	// Used when finding new candidates (of size +1)	
	private ArrayList<ArrayList<String>> combine(ArrayList<String> candidate1, ArrayList<String> candidate2) {
		
		// Make a new ArrayList, for all the candidates produces by combining candidate1 and candidate2
		ArrayList<ArrayList<String>> combined = new ArrayList<ArrayList<String>>();
		
		// Go through candidate2's properties, and combine each one with candidate1
		for (int i = 0; i < candidate2.size(); i++) {
			
			String p1 = candidate2.get(i);
			
			outer : {
				// Check that the property is not in candidate1 already
				if (!candidate1.contains(p1)) {
					
					// Pruning
					// Create candidate1 variations (subsets) with property p1, and check that each one is already in the candidates
					for (int j = 0; j < candidate1.size(); j++) {
						
						// Make a variation (checker)
						ArrayList<String> checker = new ArrayList<String>(candidate1);
						checker.set(j, p1);
						
						// Check to see if this variation is in our candidates
						if (candidatesHash.get(hashSum(checker)) == null) {
							// If not, there is no point in adding this combination to our new candidates
							break outer;
						}
					}
					
					// Make this combination and add it to the list of candidates
					ArrayList<String> candidateC = new ArrayList<String>(candidate1);
					
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
		
		ArrayList<ArrayList<String>> lowSupportCountCandidates = new ArrayList<ArrayList<String>>();
		
		for (ArrayList<String> candidate : supportCount.keySet()) {
			
			if (supportCount.get(candidate) < minSupport) {
				
				lowSupportCountCandidates.add(candidate);
				
			}	
		}
		
		for (ArrayList<String> lowSupportCountCandidate : lowSupportCountCandidates) {
			
			supportCount.remove(lowSupportCountCandidate);
			candidates.remove(lowSupportCountCandidate);
			candidatesHash.remove(hashSum(lowSupportCountCandidate));
			
		}
	}
	
	// Count support for each candidate
	private void countCandidates() {
		
		previousSupportCount = supportCount;
		
		supportCount = new HashMap<ArrayList<String>, Integer>();
		
		for (ArrayList<String> dataSet : dataSets) {
		
			for (ArrayList<String> itemSet : candidates) {
			
				if (dataSet.containsAll(itemSet)) {

					if (!supportCount.containsKey(itemSet)) {
					
						supportCount.put(itemSet, 0);
						
					}
					
					supportCount.put(itemSet, supportCount.get(itemSet) + 1);
					Double hashSum = hashSum(itemSet);
					allSupportCounts.put(hashSum, allSupportCounts.get(hashSum)+1);
					
				}				
			}
		}		
	}
	
	private void outputSupportCount() {
		
		for (ArrayList<String> itemSet : supportCount.keySet()) {
			
			System.out.println(itemSet + " : " + supportCount.get(itemSet));
			
		}	
	}
	
	private void outputPreviousSupportCount() {
	
		for (ArrayList<String> itemSet : previousSupportCount.keySet()) {
			
			System.out.println(itemSet + " : " + previousSupportCount.get(itemSet));
			
		}	
	}
	
	// Return the sum of hash values in a candidate (hash value of each property)
	// Used for faster checking if a candidate exist already
	private double hashSum(ArrayList<String> set) {
		
		hsum = 0;
		
		for (String p : set) {
			
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
	
	private void convertDays(ArrayList<Day> days, ArrayList<PROPERTY> properties, ArrayList<String> trends) {
		
		System.out.println("----------------------------------------" );
		System.out.println("Apriori");
		System.out.println("----------------------------------------" );
		System.out.println("  * Converting days to property arrays");
		System.out.println("----------------------------------------\n" );
		
		dataSets = new ArrayList<ArrayList<String>>();
		
		int low = 0;
		int med = 0;
		int high = 0;
		
		int i = 0;
        for (Day day : days) {
            
        	ArrayList<String> dataSet = new ArrayList<String>();

        	SecondaryDay secondaryDay = day.get_secondaryDay();
        	WeatherDay weatherDay = day.get_weatherDay();
            
        	if (secondaryDay != null) {
	            for (PROPERTY property : secondaryDay.discreteValues) {
	            	
	            	if (properties == null || properties.indexOf(property) != -1) {
	            		dataSet.add(property.toString());
	            	}
	            }
        	}
        	if (weatherDay != null) {
	            for (PROPERTY property : weatherDay.discreteValues) {
	            	
	            	if (properties == null || properties.indexOf(property) != -1) {
	            		dataSet.add(property.toString());
	            	}
	            }
        	}
        	
        	if (trends != null) {
        		
        		// Discretize trends
        		for (String[] trend : day.google_trends) {
        			
        			if (trends.contains(trend[0])) {
        				
	        			String trend_name = trend[0];
	        			Double trend_value = Double.parseDouble(trend[1]);
	        			
	        			int skew = 0;
	        			int numOfDays = 350;
	        			
	        			if (i <= numOfDays) {
	        				
	        				skew = (numOfDays - i) +1;
	        				
	        			}
	        			else if (i >= days.size() - numOfDays) {

	        				skew = -numOfDays + (days.size()-i) -1;
	        				
	        			}
	       
	        			Double sum = 0.;
	        			Double min = Double.POSITIVE_INFINITY;
	        			Double counter = 0.;
	        			
	        			for (int j = -numOfDays; j <= numOfDays; j++) {
	        				
	        				int index = i+j+skew;
	        				
	        				if (index != i) {
	        					
	        					int trendIndex = -1;
	        					
	        					for (int n = 0; n < days.get(index).google_trends.size(); n++) {
	        						
	        						if (days.get(index).google_trends.get(n)[0].equals(trend_name)) {
	        							
	        							trendIndex = n;
	        							
	        						}
	        					}
	        					
	        					if (trendIndex > -1) {
	        						
	        						Double trendValue = Double.parseDouble(days.get(index).google_trends.get(trendIndex)[1]);
	        						sum += trendValue;
	        						
	        						if (trendValue < min) {
	        							
	        							min = trendValue;
	        							
	        						}
	        						
	        						counter ++;
	        						
	        					}
	        				}
	        			}
	        			
	        			Double mean = sum/counter;
	        			
	        			if (trend_value > mean || trend_value > 90) {
	        				
	        				dataSet.add("trend_" + trend_name + "_high");
	        				high++;
	        				
	        			}
	        			else if (trend_value > min+10) {
	        				
	        				dataSet.add("trend_" + trend_name + "_med");
	        				med++;
	        			}
	        			else {
	        				
	        				dataSet.add("trend_" + trend_name + "_low");
	        				low++;
	        			}
        			}
        		}
        	}
        	
            dataSets.add(dataSet);
            i++;
        }  
        
        System.out.println("high: " + high);
        System.out.println("med: " + med);
        System.out.println("low: " + low);
	}
	
	public int generateAssociationRules(HashMap<ArrayList<String>, Integer> supportCount) {
		int rulesGenerated = 0;
		for (ArrayList<String> itemSet : supportCount.keySet()) {
			
			for (String prop : itemSet) {
				
				ArrayList<String> association = new ArrayList<String>(itemSet);
				ArrayList<String> prop_a = new ArrayList<String>();
				prop_a.add(prop);
				association.remove(prop);
				
				int supportCount_a = allSupportCounts.get(hashSum(prop_a));
				int supportCount_b = allSupportCounts.get(hashSum(itemSet));
				
				Double confidence = (double) supportCount_b/(double)supportCount_a*100;
				
				if (confidence > 50) {
					
					associationRules.add(new AssociationRule(prop, association, confidence, supportCount_a, supportCount_b));
					rulesGenerated++;
					
				}	
			}
		}
		
		return rulesGenerated;
		
	}
	
	public void outputAssociationRules() {
		
		System.out.println("\n\nAssociation rules:\n");
		
		DecimalFormat df = new DecimalFormat("#.#");
		
		Collections.sort(associationRules, new Comparator<AssociationRule>() {
			@Override
			public int compare(AssociationRule asso1, AssociationRule asso2) {
		        return asso1.confidence.compareTo(asso2.confidence);
		    }
        });
		
		for (AssociationRule a : associationRules) {
			
			System.out.println(a.subject + " (" + a.subjectCount + ") -> " + a.association + " (" + a.associationCount + ") = " + df.format(a.confidence) + "% confidence");
			
		}
		
	}
	
	public void outputAssociationRules(PROPERTY onlyWith) {
		
		System.out.println("\n\nAssociation rules (only with "+ onlyWith + "):\n");
		
		DecimalFormat df = new DecimalFormat("#.#");
		
		Collections.sort(associationRules, new Comparator<AssociationRule>() {
			@Override
			public int compare(AssociationRule asso1, AssociationRule asso2) {
		        return asso1.confidence.compareTo(asso2.confidence);
		    }
        });
		
		for (AssociationRule a : associationRules) {
			
			if (a.subject.equals("" + onlyWith) || a.association.contains("" + onlyWith)) {
				System.out.println(a.subject + " (" + a.subjectCount + ") -> " + a.association + " (" + a.associationCount + ") = " + df.format(a.confidence) + "% confidence");
			}
		}
		
	}
	
	public void outputAssociationRulesOnlyWith(ArrayList<PROPERTY> onlyWith) {
		
		System.out.println("\n\nAssociation rules (only with "+ onlyWith + "):\n");
		
		DecimalFormat df = new DecimalFormat("#.#");
		
		Collections.sort(associationRules, new Comparator<AssociationRule>() {
			@Override
			public int compare(AssociationRule asso1, AssociationRule asso2) {
		        return asso1.confidence.compareTo(asso2.confidence);
		    }
        });
		
		for (AssociationRule a : associationRules) {
			
			boolean outPut = false;
			
			for (PROPERTY property : onlyWith) {
				
				if (a.subject.equals("" + property) || a.association.contains("" + property)) {
					
					outPut = true;
					break;
				}
			}
			
			if (outPut) {
				System.out.println(a.subject + " (" + a.subjectCount + ") -> " + a.association + " (" + a.associationCount + ") = " + df.format(a.confidence) + "% confidence");
			}
		}
		
	}
	
	public void outputAssociationRules(ArrayList<String> trends) {
		
		System.out.println("\n\nAssociation rules (only with "+ trends + "):\n");
		
		DecimalFormat df = new DecimalFormat("#.#");
		
		Collections.sort(associationRules, new Comparator<AssociationRule>() {
			@Override
			public int compare(AssociationRule asso1, AssociationRule asso2) {
		        return asso1.confidence.compareTo(asso2.confidence);
		    }
        });
		
		for (AssociationRule a : associationRules) {
			
			boolean showThis = false;
			
			for (String trend : trends) {
				
				if (a.subject.indexOf("trend_" + trend) > -1 || a.association.contains("trend_" + trend + "_high") || a.association.contains("trend_" + trend + "_med") || a.association.contains("trend_" + trend + "_low")) {
					showThis = true;
				}
			}
			
			if (showThis) { 
				System.out.println(a.subject + " (" + a.subjectCount + ") -> " + a.association + " (" + a.associationCount + ") = " + df.format(a.confidence) + "% confidence");
			}
		}
		
	}
	
	private class AssociationRule {
		
		public String subject;
		public ArrayList<String> association;
		public Double confidence;
		public int subjectCount;
		public int associationCount;
		
		public AssociationRule(String subject, ArrayList<String> association, Double confidence, int subjectCount, int associationCount) {
			
			this.subject = subject;
			this.association = association;
			this.confidence = confidence;
			this.subjectCount = subjectCount;
			this.associationCount = associationCount;
		
		}
	}
}
