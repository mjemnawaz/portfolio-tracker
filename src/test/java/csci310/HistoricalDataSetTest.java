package csci310;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HistoricalDataSetTest {
	
	
	
	@Test
	public void testGetDataMap(){
		ArrayList<Double> ca = new ArrayList<Double>();
		ArrayList<LocalDate> da = new ArrayList<LocalDate>();
		Double[] oa = new Double[2];
		ca.add(10.0);
		ca.add(11.0);
		da.add(LocalDate.now());
		da.add(LocalDate.now().plusDays(1));
		int range = 2;
		HistoricalDataSet hds = new HistoricalDataSet(ca,da,range);
		Map<LocalDate, Double> hm = hds.getDataMap();
		assertEquals(hm.containsKey(da.get(0)),true);
		assertEquals(hm.containsKey(da.get(1)),true);
		assertEquals(hm.containsKey("applesauce"),false);
		assertEquals(hm.get(da.get(0)),ca.get(0));
		assertEquals(hm.get(da.get(1)),ca.get(1));
		
	}
	
	@Test
	public void testGetMapKeys() {
		ArrayList<Double> ca = new ArrayList<Double>();
		ArrayList<LocalDate> da = new ArrayList<LocalDate>();
		Double[] oa = new Double[2];
		ca.add(10.0);
		ca.add(11.0);
		da.add(LocalDate.now());
		da.add(LocalDate.now().plusDays(1));
		int range = 2;
		HistoricalDataSet hds = new HistoricalDataSet(ca,da,range);
		ArrayList<LocalDate> ld = hds.getMapKeys();
		assertEquals(ld.get(0),da.get(0));
		assertEquals(ld.get(1),da.get(1));
		assertEquals(ld.size(),2);
	}
}
