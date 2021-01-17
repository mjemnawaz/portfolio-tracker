package csci310;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;

import com.mashape.unirest.http.exceptions.UnirestException;

public class CurrentStockInfoTest extends Mockito {
	@Test
	public void testFetchInfo() throws UnirestException, UnsupportedEncodingException, JSONException {
		CurrentStockInfo csi1 = new CurrentStockInfo();
		csi1.fetchInfo("SCARSI");
		assertEquals(csi1.getCurrentPrice(),-420.0,.0000001);
		CurrentStockInfo csi2 = new CurrentStockInfo();
		csi2.fetchInfo("AAPL");
		assertNotEquals(csi2.getCurrentPrice(), -420.0, .0000001);
		System.out.println(csi2.getCurrentPrice());
	}
	@Test
	public void testYesterdayPrice() throws UnirestException, UnsupportedEncodingException {
		CurrentStockInfo csi1 = new CurrentStockInfo();
		assertNotEquals(csi1.yesterdayPrice("AAPL"),0);
	}
	@Test
	public void testFindStockName() throws UnirestException, UnsupportedEncodingException, JSONException {
		CurrentStockInfo csi1 = new CurrentStockInfo();
		csi1.findStockName("AAPL");
		assertEquals(csi1.getStockame(),"Apple Inc.");
		CurrentStockInfo csi2 = new CurrentStockInfo();
		csi2.findStockName("APPLESAUCE");
		assertEquals(csi2.getStockame(),"NONAME");
		
	}
	
	@Test
	public void testPercentChange() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.removeUser("scarsitest");
		CurrentStockInfo csi1 = new CurrentStockInfo();
		db.addUser("scarsitest", "scarsitest");
		int id = db.getUserId("scarsitest");
		db.insertTransaction("AAPL", "AAPL", 10.0, 10.0, id);
		csi1.percentChange(id);
		assertNotEquals(csi1.getPercentChange(), -420.0, .0000001);
		db.removeUser("scarsitest");
		
	}
	

}
