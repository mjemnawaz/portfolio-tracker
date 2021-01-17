package CSV;

import static org.junit.Assert.*;

import org.junit.Test;

public class StockTest {

	private Stock s  = new Stock(0,0,"Paypal","PYPL",10,400);
	
	@Test
	public void testGetId() {
		assertTrue("",s.getId()==0);
	}
	
	@Test
	public void testGetUserId() {
		assertTrue("",s.getUserId()==0);
	}
	
	
	@Test
	public void testGetName() {
		assertTrue("",s.getName().contentEquals("Paypal"));
	}
	
	@Test
	public void testGetAbrev() {
		assertTrue("",s.getAbrev().contentEquals("PYPL"));
	}
	
	@Test
	public void testGetHoldings() {
		assertTrue("",s.getHoldings()==10);
	}
	
	@Test
	public void testGetAvgCost() {
		assertTrue("",s.getAvgcost()==400);
	}

}
