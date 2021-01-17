package CSV;


import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import csci310.CurrentStockInfo;
import csci310.HistoricalDataFetcher;
import csci310.SQLiteDB;

public class CSV {
	
	//read in portfolio holdings from before the account is created
	// successfully read in data: returns 1
	// couldn't find valid file: returns -1
	// incorrect data format or empty: return -2
	public int readCSV(int userId, String CSVLocation) throws UnirestException, UnsupportedEncodingException, JSONException {
		int returnVal = -1, index = 0;
		List<Transaction> toInsert = new ArrayList<Transaction>();
		SQLiteDB db = null;
		try {
			db = new SQLiteDB();
			FileReader filereader = new FileReader(CSVLocation);
			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build(); //skip the first line of csv (column labels)
			returnVal--;
			String[] nextRecord;
	        String abrev="";
	        double holdings=0;
	        while ((nextRecord = csvReader.readNext()) != null) {
	            for (index = 0; index < nextRecord.length; index++) { 
	                if (index%4==0) abrev = nextRecord[index];
	                if (index%4==1) holdings = Double.parseDouble(nextRecord[index].replaceAll("[^\\d.]", ""));
	                if (index%4==2) {
	                	System.out.println("Date: " + nextRecord[index]);
	                	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	                	toInsert.add(new Transaction(0,abrev,holdings,
	                			new java.sql.Date(sdf.parse(nextRecord[index]).getTime())));
	                }
	            }  
	        } 
	        returnVal = 1;
	        throw new Exception("Success"); // 1
		} catch (Exception e) {
			if (returnVal==1) {
				for (int i = 0; i < toInsert.size(); i++) {
					String stockName = db.getNameFromAbrev(toInsert.get(i).getName());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					System.out.println("User " + userId+ ": Inserting " + toInsert.get(i).getAmount() + " of " + 
							stockName + " at " + sdf.format(toInsert.get(i).getDate()));
					db.insertTransaction(stockName,toInsert.get(i).getName(), toInsert.get(i).getAmount(),
							1, userId, sdf.format(toInsert.get(i).getDate()));
				}
			}
			return returnVal;
		} 
	}
}

