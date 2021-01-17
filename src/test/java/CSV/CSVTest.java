package CSV;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import csci310.SQLiteDB;

// for coverage : test invalid CSVLocation, eg: CSV csvInvalid = new CSV("nonexistentcsvfile.csv");


public class CSVTest {
	
	@Test
	public void testReadCSV() throws Exception {
		CSV csv = new CSV();
		SQLiteDB db = new SQLiteDB();
		db.addUser("userCSV", "passCSV");
		db.addUser("userCSVEmpty", "passCSVEmpty"); 
		db.addUser("userCSVInvalid", "passCSVInvalid"); 
		db.addUser("userCSVInvalid1", "passCSVInvalid1");
		db.addUser("userCSVInvalid2", "passCSVInvalid2");
		int userIdCSV = db.getUserId("userCSV");
		int userIdCSVEmpty = db.getUserId("userCSVEmpty");
		int userIdCSVInvalid = db.getUserId("userCSVInvalid");
		int userIdCSVInvalid1 = db.getUserId("userCSVInvalid1");
		int userIdCSVInvalid2 = db.getUserId("userCSVInvalid2");
		assertTrue("Can't read in valid file",csv.readCSV(userIdCSV, "files/input.csv")==1);
		assertTrue("Reads in nonexistent file",csv.readCSV(userIdCSVEmpty,"nonexistentcsvfile.csv")==-1);
		assertTrue("Reads in empty file",csv.readCSV(userIdCSVInvalid,"files/empty.csv")==-2);
		assertTrue("Reads in file with invalid data types",csv.readCSV(userIdCSVInvalid1,"files/invaliddatatype.csv")==-2);
		assertTrue("Reads in file with invalid data format",csv.readCSV(userIdCSVInvalid2,"files/invaliddataformat.csv")==-2);
		assertTrue("Stocks are not successfully read in",db.getStock("Twitter", userIdCSV)!=null);
		assertTrue("Stocks should not have been read in",db.getStock("Twitter", userIdCSVEmpty)==null);
		assertTrue(db.createStock("Twitter", "TWTR", userIdCSVEmpty)!=0);
		assertTrue("Stocks created not showing up",db.getStock("Twitter", userIdCSVEmpty)!=null);
		db.removeUser("userCSV");
		db.removeUser("userCSVEmpty");
		db.removeUser("userCSVInvalid");
		db.removeUser("userCSVInvalid1");
		db.removeUser("userCSVInvalid2");
	}
}
