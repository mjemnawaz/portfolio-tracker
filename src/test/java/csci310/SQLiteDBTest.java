package csci310;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import CSV.Stock;

 
public class SQLiteDBTest {
	
	@Test
	public void testGetConnStatus() throws Exception {
		SQLiteDB db = new SQLiteDB();
		boolean status = false;
		status = db.getNotNull();
		assertEquals(status,false);
		db.getConn();
		status = db.getNotNull();
		assertEquals(status,true);
		db.closeConn();
		status = db.getNotNull();
		assertEquals(status,false);
	}
	
	@Test
	public void testConstructor() throws Exception {
		SQLiteDB sql = new SQLiteDB();
		assertEquals(1,1);
		sql.closeConn();
	}

	
	@Test
	public void testGetConn() throws Exception {
		SQLiteDB db = new SQLiteDB();
		Connection conn1 = db.getConn();
		assertEquals(db.getNotNull(),true);
		
		if (conn1 != null) {
			assertEquals(1,1);
		}
		
		Connection conn2 = db.getConn();
		assertEquals(db.getNotNull(),true);
		assertEquals(conn1,conn2);
		db.closeConn();
	}
	
	@Test
	public void testCloseConn() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.closeConn();
		assertEquals(db.getNotNull(),false);
		db.getConn();
		assertEquals(db.getNotNull(),true);
		db.closeConn();
		assertEquals(db.getNotNull(),false);
	}
	
	@Test
	public void testSetUpDatabase() throws Exception {
		SQLiteDB db = new SQLiteDB();
		assertEquals(db.setUpDatabase(),1);
		db.getConn();
		assertEquals(db.setUpDatabase(),1);
		db.closeConn();
		assertEquals(db.setUpDatabase(),1);
	}
	
	@Test
	public void testDeleteUserInfo() throws Exception {
		SQLiteDB db = new SQLiteDB();
		assertEquals(db.deleteUserInfo("nobody"),0); 
		db.addUser("nobody", "asdfg");
		int id = db.getUserId("nobody");
		assertTrue("new user should not have stock",db.getStock("F", id)==null);
		db.insertTransaction("F", "F", 2, 20, id);
		assertTrue("did not properly insert stock",db.getStock("F", id)!=null);
		assertEquals(db.deleteUserInfo("nobody"),1);
		assertTrue("did not properly delete user info",db.getStock("F", id)==null);
		db.removeUser("nobody");
	}
	
	@Test
	public void testGetNameFromAbrev() throws Exception {
		SQLiteDB db = new SQLiteDB();
		assertTrue(db.getNameFromAbrev("TSLA").equals("Tesla"));
		assertTrue(db.getNameFromAbrev("F").equals("Ford Motor Company"));
		assertTrue(db.getNameFromAbrev("INVALID").equals(""));
	}
	
	@Test
	public void testGetLocalHistPrice() throws Exception{
		SQLiteDB db = new SQLiteDB();
		assertTrue(db.getLocalHistPrice("2020-04-01", "INVALID")==0);
		assertTrue(db.getLocalHistPrice("2020-04-01", "TSLA")!=0);
		assertTrue(db.getLocalHistPrice("2020-04-05","TSLA")!=0);
		assertTrue(db.getLocalHistPrice("2020-04-01", "F")!=0);
	}
	
	@Test
	public void testInsertTransaction() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.addUser("mjn", "mjn");
		String name = "Google", abrev = "GOOGL";
		int userId = db.getUserId("mjn");
		assertEquals(0,db.insertTransaction(name,abrev, -1, 0.0, userId, "2020-04-01")); //selling more than owned
		assertEquals(1,db.insertTransaction(name, abrev, 1, 5, userId, "2020-04-01")); //buying one share, avgcost = 5
		assertEquals(0,db.insertTransaction(name,abrev, -2, 5, userId, "2020-04-01")); //selling more than owned
		assertEquals(1,db.insertTransaction(name, abrev, 1, 5, userId, "2020-04-01")); //buying one more share
		assertTrue("Cannot sell 2 when holdings of NAME = " + db.getStock("Google", userId).getHoldings(),
				1==db.insertTransaction(name,abrev, -2, 5, userId, "2020-04-01")); //selling it all
		db.removeUser("mjn");
		assertEquals(db.getStock(name, userId),null);
	}
	
	@Test
	public void testGetStock() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.addUser("mjn", "mjn");
		int userId = db.getUserId("mjn");
		assertEquals(db.getStock("", userId),null);
		assertTrue(db.createStock("","", userId)!=0);
		assertTrue("Cannot find inserted stock",db.getStock("", userId)!=null);
		db.removeUser("mjn");
		assertEquals(db.getStock("", userId),null);
	}
	
	@Test
	public void testGetStockList() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.addUser("mjn", "mjn");
		int userId = db.getUserId("mjn");
		db.insertTransaction("Twitter", "TWTR", 1, 9000, userId, "2020-11-01");
		db.insertTransaction("Starbucks", "SBUX", 1, 360, userId, "2020-11-01");
		db.insertTransaction("Nintendo", "NTDOY", 1, 0.01, userId, "2020-11-01");
		List<String> subset = new ArrayList<String>();
		subset.add("Twitter");
		List<Stock> list1 = db.getStockList(userId); // return all stocks
		assertTrue(list1.size()==3);
		list1 = db.getStockList(userId, subset, true); // return stocks in subset
		assertTrue(list1.size()==1);
		subset.add("Starbucks");
		list1 = db.getStockList(userId, subset, true); // return stocks in subset
		assertTrue(list1.size()==2);
		list1 = db.getStockList(userId, subset, false); // return stocks not in subset
		assertTrue(list1.size()==1);
		subset.add("NONEXISTENT");
		list1 = db.getStockList(userId, subset, false); // return stocks not in subset
		assertTrue(list1==null);
		db.removeUser("mjn");
	}
	
	@Test
	public void testGetTransactionList() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.addUser("mjn", "mjn");
		int userId = db.getUserId("mjn");
		assertTrue(db.getTransactionList(userId, "Coca-Cola").size()== 0);
		db.insertTransaction("Coca-Cola", "KO", 1, 9000, userId,"2020-11-01");
		assertTrue(db.getTransactionList(userId, "Coca-Cola").size()== 1);
		db.insertTransaction("Coca-Cola", "KO", 1, 9000, userId,"2020-11-02");
		assertTrue(db.getTransactionList(userId, "Coca-Cola").size()== 2);
		db.removeStock(db.getStock("Coca-Cola", userId).getId());
		assertTrue(db.getTransactionList(userId, "Coca-Cola").size()== 0);
		db.removeUser("mjn");
	}
	
	@Test
	public void testGetEarliestTransaction() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.addUser("mjn", "mjn");
		int userId = db.getUserId("mjn");
		assertTrue(db.getEarliestTransaction(userId).equals(""));
		assertTrue(db.getEarliestTransaction(userId, 0).equals(""));
		
		db.insertTransaction("Twitter", "TWTR", 1, 250, userId, "2020-11-01");
		int stockId = db.getStock("Twitter", userId).getId();
		assertTrue(db.getEarliestTransaction(userId).equals("2020-11-01"));
		assertTrue(db.getEarliestTransaction(userId, stockId).equals("2020-11-01"));
		
		db.insertTransaction("Twitter", "TWTR", 1, 250, userId, "2020-04-01");
		assertTrue(db.getEarliestTransaction(userId).equals("2020-04-01"));
		assertTrue(db.getEarliestTransaction(userId, stockId).equals("2020-04-01"));
		
		db.insertTransaction("Twitter", "TWTR", 1, 250, userId, "2020-11-02");
		assertTrue(db.getEarliestTransaction(userId).equals("2020-04-01"));
		assertTrue(db.getEarliestTransaction(userId, stockId).equals("2020-04-01"));
		
		db.insertTransaction("Twitter", "TWTR", 1, 250, userId, "2019-12-01");
		assertTrue(db.getEarliestTransaction(userId).equals("2019-12-01"));
		assertTrue(db.getEarliestTransaction(userId, stockId).equals("2019-12-01"));
		db.removeUser("mjn");
	}
	
	@Test
	public void testGetHoldings() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.addUser("mjn", "mjn");
		int userId = db.getUserId("mjn");
		db.insertTransaction("Zoom", "ZM", 1, 100, userId, "2020-11-01");
		db.insertTransaction("Google", "GOOGL", 1, 100, userId, "2020-11-01");
		db.insertTransaction("Facebook", "FB", 1, 100, userId, "2020-11-01");
		Stock stock = db.getStock("Facebook", userId);
		List<String> subset = new ArrayList<String>();
		subset.add("Zoom");
		subset.add("Google");
		List<Stock> list = db.getStockList(userId, subset, true);
		assertTrue(db.getHoldings(userId)!=0); 
		assertTrue(db.getHoldings(userId, stock)!=0);
		assertTrue(db.getHoldings(userId, list)!=0);
		db.removeUser("mjn");
	}
	
	@Test
	public void testGetPastHoldings() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.addUser("mjn", "mjn");
		int userId = db.getUserId("mjn");
		db.insertTransaction("Microsoft", "MSFT", 2, 100, userId, "2020-11-01");
		db.insertTransaction("Microsoft", "MSFT", 3, 100, userId, "2020-11-02");
		db.insertTransaction("Google", "GOOGL", 1, 100, userId, "2020-04-01");
		db.insertTransaction("Facebook", "FB", 1, 100, userId, "2020-11-01");
		db.insertTransaction("Facebook", "FB", 1, 100, userId, "2020-11-02");
		Stock stock = db.getStock("Facebook", userId);
		List<String> subset = new ArrayList<String>();
		subset.add("Microsoft");
		subset.add("Google");
		List<Stock> list = db.getStockList(userId, subset, true);
		assertTrue(db.getPastHoldings(userId, "INVALIDDATE")==-1);
		assertTrue(db.getPastHoldings(userId, "2020-04-01")!=0);
		assertTrue(db.getPastHoldings(userId, stock, "2020-11-01")!=0);
		assertTrue(db.getPastHoldings(userId, stock, "INVALIDDATE")==-1);
		assertTrue(db.getPastHoldings(userId, list, "2020-04-01")!=0);
		assertTrue(db.getPastHoldings(userId, list, "2019-12-01")==0);
		db.removeUser("mjn");
	}
	
	@Test
	public void testGetPastHoldingsRange() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.addUser("rando", "rando");
		int userId = db.getUserId("rando");
		List<String> stocknames = new ArrayList<String>();
		stocknames.add("Google");
		stocknames.add("Tesla");
		db.insertTransaction("Google", "GOOGL", 1, 100, userId, "2020-10-01");
		db.insertTransaction("Tesla", "TSLA", 1, 100, userId, "2020-10-02");
		db.insertTransaction("Google", "GOOGL", -1, 100, userId, "2020-10-06");
		db.insertTransaction("Tesla", "TSLA", -1, 100, userId, "2020-10-31");
		String ret = db.getPastHoldingsRange(userId, stocknames, "2020-10-02", "2020-11-01", 1);
		assertTrue(ret != null);
		db.removeUser("rando");
	}
	
	@Test
	public void testGetValueChange() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.addUser("rando", "rando");
		int userId = db.getUserId("rando");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		assertTrue(db.getValueChange(userId)==0);
		db.insertTransaction("Google", "GOOGL", 1, 100, userId, "2020-11-01");
		db.insertTransaction("Google", "GOOGL", 1, 100, userId, "2020-04-01");
		assertTrue(db.getValueChange(userId) != -100); // equals % change in google stock yesterday to today
		System.out.println("TODAY: " + java.time.LocalDate.now(ZoneId.of("America/Los_Angeles")).toString());
		db.insertTransaction("Google", "GOOGL", -2, 100, userId, java.time.LocalDate.now().toString());
		assertTrue(db.getValueChange(userId) == -100); // sold all holdings today
		db.insertTransaction("Tesla", "TSLA", 2, 100, userId, "2020-04-01");
		List<Stock> subset = new ArrayList<Stock>();
		subset.add(db.getStock("Tesla", userId));
		assertTrue(db.getValueChange(userId, "2019-12-01", subset)==100); // had 0 holdings initially
		db.insertTransaction("Tesla", "TSLA", -2, 100, userId, "2020-04-01");
		assertTrue(db.getValueChange(userId, "2019-12-01", subset)==0); // had 0, have 0
		db.removeUser("rando");
	}
	
	@Test
	public void testCreateStock() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.addUser("mjn", "mjn");
		int userId = db.getUserId("mjn");
		assertTrue(db.createStock("","", userId)!=0);
		assertTrue(db.createStock("","", userId)!=0);
		db.removeUser("mjn");
		assertEquals(db.getStock("", userId),null);
	}
	
	@Test
	public void testRemoveStock() throws Exception {
		SQLiteDB db = new SQLiteDB();
		db.addUser("mjn", "mjn");
		int userId = db.getUserId("mjn");
		assertTrue(db.getStock("Google", userId)==null);
		assertTrue(db.createStock("Google","GOOGL", userId)!=0);
		assertTrue(db.getStock("Google", userId)!=null);
		assertTrue(db.removeStock(db.getStock("Google", userId).getId())==1);
		assertTrue(db.getStock("Google", userId)==null);
		db.insertTransaction("Google", "GOOGL", 1, 200, userId);
		assertTrue(db.getStock("Google", userId)!=null);
		assertTrue(db.removeStock(db.getStock("Google", userId).getId())==1);
		assertTrue(db.getStock("Google", userId)==null);
		db.removeUser("mjn");
		assertEquals(db.getStock("", userId),null);
	}
	
	@Test
	public void testUserExists() throws Exception {
		String username = "yeww";
		String password = "yeww";
		String check = "";
		SQLiteDB db = new SQLiteDB();
		check = db.userExists("", password);
		assertEquals(check,"Inputs cannot be empty");
		//d41d8cd98f00b204e9800998ecf8427e is blank string after hash
		check = db.userExists(username,"d41d8cd98f00b204e9800998ecf8427e");
		assertEquals(check,"Inputs cannot be empty");
		check = db.userExists(null, password);
		assertEquals(check,"Inputs cannot be empty");
		check = db.userExists(username,null);
		assertEquals(check,"Inputs cannot be empty");
		
		int add = db.addUser(username, password);
		if (add == 1) {
			check = db.userExists(username, password);
			assertEquals("Success",check);
		}
		int exists = db.addUser(username, password);
		assertEquals(0, exists);
		int remove = db.removeUser(username);
		if (remove == 1) {
			check = db.userExists(username, password);
			assertEquals("Invalid Credentials",check);
		}
	}
	
	@Test
	public void testIsLocked() throws Exception {
		String username = "lock";
		String password = "lock";
		String check = "";
		SQLiteDB db = new SQLiteDB();
		db.addUser(username, password);
		check = db.userExists(username, password);
		assertEquals(check, "Success");
		check = db.userExists(username, "loc");
		check = db.userExists(username, "loc");
		check = db.userExists(username, "loc");
		check = db.userExists(username, password);
		assertEquals(check, "User locked out, try again in 1 minute");
		db.removeUser(username);
	}
	
	@Test
	public void testLogin() throws Exception {
		String username = "lock";
		String password = "lock";
		String check = "";
		SQLiteDB db = new SQLiteDB();
		db.addUser(username, password);
		check = db.login("dne");
		assertEquals(check, "Invalid Credentials");
		check = db.userExists(username, "loc");
		check = db.userExists(username, "loc");
		check = db.userExists(username, "loc");
		assertEquals(check, "User locked out, try again in 1 minute");
		db.removeUser(username);
	}
	
	@Test
	public void testClearLoginHistory() throws Exception {
		String username = "lock";
		String password = "lock";
		String check = "";
		SQLiteDB db = new SQLiteDB();
		db.addUser(username, password);
		db.userExists(username, "loc");
		db.userExists(username, "loc");
		db.userExists(username, "loc");
		db.clearLoginHistory(username);
		check = db.userExists(username, "loc");
		assertTrue(!check.equals("User locked out, try again in 1 minute"));
		db.removeUser(username);
	}
	
	@Test
	public void testAddUser() throws Exception {
		String username = "yeww";
		String password = "yeww";
		int check = 0;
		SQLiteDB db = new SQLiteDB();
		check = db.addUser("", password);
		assertEquals(check,-1);
		//d41d8cd98f00b204e9800998ecf8427e is blank string after hash
		check = db.addUser(username,"d41d8cd98f00b204e9800998ecf8427e");
		assertEquals(check,-1);
		check = db.addUser(null, password);
		assertEquals(check,-1);
		check = db.addUser(username,null);
		assertEquals(check,-1);
		check = db.addUser("yeww", password);
		assertEquals(check,1);
		assertEquals(db.userExists(username, password),"Success");
		db.removeUser(username);
	}
	
	@Test
	public void testRemoveUser() throws Exception {
		String username = "yeww";
		String password = "yeww";
		SQLiteDB db = new SQLiteDB();
		int check = 0;
		int add = db.addUser(username, password);
		if (add == 1){
			check = db.removeUser("");
			assertEquals(check,0);
			check = db.removeUser(null);
			assertEquals(check,0);
			check = db.removeUser(username);
			assertEquals(check,1);
			check = db.removeUser(username);
			assertEquals(check,0);	
		}
	}
	
	@Test
	public void testGetUserId() throws Exception {
		String username = "yeww";
		String password = "yeww";
		SQLiteDB db = new SQLiteDB();
		int add = db.addUser(username, password);
		int check = db.getUserId(username);
		assertTrue(check > 0);
		db.removeUser(username);
	}
	
}
