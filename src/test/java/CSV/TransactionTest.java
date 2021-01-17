package CSV;

import static org.junit.Assert.*;

import org.junit.Test;

public class TransactionTest {

	private Transaction t = new Transaction(0,"GM",20,null);
	
	@Test
	public void testGetId() {
		assertTrue("",t.getId()==0);
	}
	
	@Test
	public void testGetName() {
		assertTrue("",t.getName().contentEquals("GM"));
	}
	
	@Test
	public void testGetAmount() {
		assertTrue("",t.getAmount()==20);
	}
	
	@Test
	public void testGetDate() {
		assertTrue("",t.getDate()==null);
	}

}
