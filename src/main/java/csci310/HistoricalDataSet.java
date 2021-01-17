package csci310;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class HistoricalDataSet {
	public ArrayList<Double> closingPrices;
	public ArrayList<LocalDate> dates;
	public int dateRange;
	
	public HistoricalDataSet(ArrayList<Double> ca, ArrayList<LocalDate> da, int range) {
		dateRange = range;
		closingPrices= ca;
		dates = da;
	}
	
	public Map<LocalDate, Double> getDataMap(){
		Map<LocalDate, Double> hm = new HashMap<>();
		for (int i = 0; i < dateRange; i++) {
			hm.put(dates.get(i),closingPrices.get(i));
		}
		return hm;
	}
	public int getDateCount() {
		return dateRange;
	}
	public ArrayList<LocalDate> getMapKeys() {
		return dates;
	}
 
}
