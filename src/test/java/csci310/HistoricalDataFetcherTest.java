package csci310;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

public class HistoricalDataFetcherTest {
	
	@Test
	public void testValidTicker() throws UnirestException, UnsupportedEncodingException {
		HistoricalDataFetcher hdf = new HistoricalDataFetcher();
		assertEquals(hdf.validTicker("AAPL"),true);
		assertEquals(hdf.validTicker("Applesauce"),false);
	}
	@Test
	public void testGetHistPrice() throws UnirestException, UnsupportedEncodingException{
		HistoricalDataFetcher hdf = new HistoricalDataFetcher();
		assertEquals(hdf.getHistPrice("2020-10-15", "AAPLESAUCE"),0.0,.0000001);
		assertEquals(hdf.getHistPrice("2020-10-15", "AAPL"),120.70999908447266,.0000001);
		
	}
	@Test
	public void testGetData() throws UnirestException, UnsupportedEncodingException {
		HistoricalDataFetcher hdf = new HistoricalDataFetcher();
		assertEquals(hdf.getData("2020-08-10","2020-10-20", "Applesauce",0),null);
		HistoricalDataSet one = hdf.getData("2020-08-10","2020-10-20", "AAPL",2);
		System.out.println(one.getMapKeys().size());
		assertEquals(one.getMapKeys().size(),3);
		assertEquals(one.getDateCount(),3);
	}
	@Test
	public void testGetIncrement() {
		HistoricalDataFetcher hdf = new HistoricalDataFetcher();
		assertEquals(hdf.getRange(200),"1y");
		assertEquals(hdf.getRange(94),"6mo");
		assertEquals(hdf.getRange(6),"3mo");
		assertEquals(hdf.getRange(1),"5d");
		
	}
	@Test
	public void testGetRange() {
		HistoricalDataFetcher hdf = new HistoricalDataFetcher();
		assertEquals(hdf.getIncrement(0),1);
		assertEquals(hdf.getIncrement(1),7);
		assertEquals(hdf.getIncrement(2),30);
		
	}
}
