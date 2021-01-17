package csci310;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.mashape.unirest.http.exceptions.UnirestException;

import CSV.Stock;
import CSV.Transaction;

public class SQLiteDB {
	private Connection c = null;
	private boolean notnull = false;
	private List<String> Names = new ArrayList<String>();
	private List<String> Tickers = new ArrayList<String>();
	HashMap<String, List<Double>> HistPrices = new HashMap<String, List<Double>>();
	public SQLiteDB() throws Exception {
		setUpDatabase();
	}
	  
	public Connection getConn() throws Exception {
		if(c == null){
			
			Class.forName("org.sqlite.JDBC");
			notnull = true;
			c = DriverManager.getConnection("jdbc:sqlite:testYork.db");
	    }
	    return c;    
	}
	
	public int closeConn() throws Exception {
		if (getNotNull()) {
			c.close();
			c = null;
			notnull = false;
			return 1;
		}
		return 0;
	}
	
	public boolean getNotNull() {
		return notnull;	
	}
	
	public int setUpDatabase(){
		try {
			Collections.addAll(Tickers,"TSLA","GOOGL","TWTR","FB","PYPL","BABA","UBER","AAPL","ZM","MSFT","BAC","NFLX","KO","SBUX","NKE","NTDOY");
			Collections.addAll(Names,"Tesla","Google","Twitter","Facebook","Paypal","Ali Baba","Uber","Apple","Zoom","Microsoft","Bank of America","Netflix","Coca-Cola","Starbucks","Nike","Nintendo");
			List<Double> NOVSECND2020 = new ArrayList<Double>();
			List<Double> NOVFIRST2020 = new ArrayList<Double>();
			List<Double> APRFIRST2020 = new ArrayList<Double>();
			List<Double> DECFIRST2019 = new ArrayList<Double>();
			Collections.addAll(NOVSECND2020,400.51,1624.32,39.47,261.36,187.76,310.84,34.81,108.77,453.00,202.33,24.08,484.12,48.62,85.97,122.39,68.73);
			Collections.addAll(NOVFIRST2020,388.04,1616.11,41.36,263.11,186.13,304.69,33.41,108.86,460.91,202.47,23.70,475.74,48.06,86.96,120.08,67.73);
			Collections.addAll(APRFIRST2020,096.31,1102.10,23.32,159.60,091.38,187.56,25.42,060.23,137.00,152.11,19.77,364.08,42.12,62.62,079.23,48.45);
			Collections.addAll(DECFIRST2019,065.99,1304.01,30.91,201.64,108.01,200.00,29.60,066.81,074.50,151.38,33.32,314.66,53.40,85.43,093.49,48.39);
			HistPrices.put("2020-11-02", NOVSECND2020);
			HistPrices.put("2020-11-01", NOVFIRST2020);
			HistPrices.put("2020-04-01", APRFIRST2020);
			HistPrices.put("2019-12-01", DECFIRST2019);
			String createTable0 = "CREATE TABLE IF NOT EXISTS user (\nid INTEGER PRIMARY KEY AUTOINCREMENT,\nusername TEXT NOT NULL,\npk TEXT NOT NULL,\ndate DATETIME,\nlogin1 DATETIME,\nlogin2 DATETIME,\nUNIQUE(username)\n);";
			String createTable1 = "CREATE TABLE IF NOT EXISTS stocks (\nuserid INTEGER FOREIGNKEY REFERENCES user(id),\nid INTEGER PRIMARY KEY AUTOINCREMENT,\nname TEXT NOT NULL,\nabrev TVARCHAR(5),\nholdings DOUBLE(12,2) NOT NULL,\navgcost DOUBLE(12,2) NOT NULL\n);";
			String createTable2 = "CREATE TABLE IF NOT EXISTS transactions (\nuserid INTEGER FOREIGNKEY REFERENCES user(id),\nstockid INTEGER FOREIGNKEY REFERENCES stocks(id),\nid INTEGER PRIMARY KEY AUTOINCREMENT,\ndate DATETIME,\namount DOUBLE(10,2) NOT NULL,\nprice DOUBLE(10,2) NOT NULL\n);";
			Statement createTableStatement0 = getConn().createStatement();
			Statement createTableStatement1 = getConn().createStatement();
			Statement createTableStatement2 = getConn().createStatement();
			createTableStatement0.execute(createTable0);
			createTableStatement1.execute(createTable1);
			createTableStatement2.execute(createTable2);
			closeConn();
			throw new Exception("Exception message");
		} catch (Exception e) {
			return 1;
		}
	}
	
	public int deleteUserInfo(String username) {
		int returnVal = 0;
		try {
			int userId = getUserId(username); 
			if (userId==-1) return returnVal;
			String sql1 = "DELETE FROM stocks WHERE userid = ?;";
			String sql2 = "DELETE FROM transactions WHERE userid = ?;";
			PreparedStatement ps1 = getConn().prepareStatement(sql1);
			PreparedStatement ps2 = getConn().prepareStatement(sql2);
			ps1.setInt(1,userId);
			ps2.setInt(1,userId);
			ps1.executeUpdate();
			ps2.executeUpdate();
			closeConn();
			returnVal++;
			throw new Exception();
		} catch (Exception e) {
			return returnVal;
		}
	}
	
	public String getNameFromAbrev(String abrev) throws UnirestException, UnsupportedEncodingException, JSONException {
		String returnVal = "";
		HistoricalDataFetcher hdf = new HistoricalDataFetcher();
		if (Tickers.contains(abrev)) {
			returnVal = Names.get(Tickers.indexOf(abrev));
		} else if (hdf.validTicker(abrev)){
			CurrentStockInfo csi = new CurrentStockInfo();
			csi.fetchInfo(abrev);
			returnVal = csi.getStockame();
		}
		return returnVal;
	}
	
	public double getLocalHistPrice(String date, String abrev) throws UnirestException, UnsupportedEncodingException {
		double returnVal = 0;
		HistoricalDataFetcher hdf = new HistoricalDataFetcher();
		if (Tickers.contains(abrev) && HistPrices.containsKey(date)) {
			returnVal = HistPrices.get(date).get(Tickers.indexOf(abrev));
		} else {
			returnVal = hdf.getHistPrice(date, abrev);
		}
		return returnVal;
	}
	
	public int insertTransaction(String name, String abrev, double amount, double price, int userId) {
		return insertTransaction(name,abrev,amount,price,userId,null);
	}
	public int insertTransaction(String name, String abrev, double amount, double price, int userId, String date) {
		int stockId = 0, returnVal =0;
		double holdings = 0, avgcost = 0;
		java.sql.Date inputDate = ((date==null) ? java.sql.Date.valueOf(java.time.LocalDate.now()) : java.sql.Date.valueOf(date));
		Stock stockInfo = getStock(name,userId);
		//selling more than you own
		if ((stockInfo==null && amount<0) || (stockInfo!=null && amount<0 && amount*-1>stockInfo.getHoldings())) return returnVal;
		if (stockInfo==null) {
			stockId = createStock(name,abrev, userId);
		} else {
			stockId = stockInfo.getId();
			holdings = stockInfo.getHoldings();
			avgcost = stockInfo.getAvgcost();
		}
		String insertTransactionInfo = "INSERT INTO transactions(userid,stockid,date,amount,price)" +
				"VALUES(?,?,?,?,?)";
		PreparedStatement insertTransactionInfoStatement;
		String updateStockInfo = "UPDATE stocks SET holdings = ?, avgcost = ? WHERE id=? and userid=?";
		PreparedStatement updateStockInfoStatement;
		try {
			insertTransactionInfoStatement = getConn().prepareStatement(insertTransactionInfo);
			insertTransactionInfoStatement.setInt(1, userId);
			insertTransactionInfoStatement.setInt(2, stockId);
			insertTransactionInfoStatement.setDate(3, inputDate);
			insertTransactionInfoStatement.setDouble(4, amount);
			insertTransactionInfoStatement.setDouble(5, price);
			insertTransactionInfoStatement.executeUpdate();
			if (holdings+amount != 0)  {
				avgcost = (avgcost*holdings + price*amount)/(holdings+amount);
				holdings+=amount;
			} else {
				avgcost = 0;
				holdings = 0;
			}
			updateStockInfoStatement = getConn().prepareStatement(updateStockInfo);
			updateStockInfoStatement.setDouble(1, holdings);
			updateStockInfoStatement.setDouble(2, avgcost);
			updateStockInfoStatement.setInt(3, stockId);
			updateStockInfoStatement.setInt(4, userId);
			updateStockInfoStatement.executeUpdate();
			closeConn();
			returnVal = 1;
			throw new Exception();
		} catch (Exception e) {
			return returnVal;
		}
	}
	
	public Stock getStock(String name, int userId) {
		Stock returnVal = null;
		String checkIfStockInfoExists = "SELECT * FROM stocks WHERE name = '" + name + "' and userid = " + userId + ";";
		PreparedStatement checkIfStockInfoExistsStatement;
		try {
			checkIfStockInfoExistsStatement = getConn().prepareStatement(checkIfStockInfoExists);
			ResultSet stock = checkIfStockInfoExistsStatement.executeQuery();
			if (stock.next()) 
				returnVal = new Stock(stock.getInt("id"), stock.getInt("userid"), name, stock.getString("abrev"), stock.getDouble("holdings"),stock.getDouble("avgcost"));
			closeConn();
			throw new Exception();
		} catch (Exception e) {
			return returnVal;
		}
	}
	
	// returns all stocks for this user
	public List<Stock> getStockList(int userId) {
		return getStockList(userId, new ArrayList<String>(), false);
	}
	
	// returns all stocks for this user
	// if include = false, EXCEPT for stocks in subset
	// if include = true, ONLY stocks in subset
	public List<Stock> getStockList(int userId, List<String> subset, boolean include) {
		List<Stock> returnVal = new ArrayList<Stock>();
		String includeStatement = include ? "IN" : "NOT IN";
		String getStocks = "SELECT * FROM stocks WHERE userid = " + userId;
		for (int i = 0; i < subset.size(); i++) {
			if (getStock(subset.get(i), userId)==null) returnVal = null; // invalid stockname found in subset
			if (i==0 && i == subset.size()-1) getStocks += " and name " + includeStatement+ " ('" + subset.get(i) + "')";
			else if (i==0) getStocks += " and name " + includeStatement+ " ('" + subset.get(i) +"'";
			else if (i == subset.size()-1) getStocks += ", '" + subset.get(i) + "')";
			else getStocks+= ", '" + subset.get(i)+"'";
		}
		getStocks += ";";
		try {
			PreparedStatement getStocksStatement = getConn().prepareStatement(getStocks);
			ResultSet stocks = getStocksStatement.executeQuery();
			while (stocks.next()) 
				returnVal.add(new Stock(stocks.getInt("id"), stocks.getInt("userid"), stocks.getString("name"), 
						stocks.getString("abrev"), stocks.getDouble("holdings"),stocks.getDouble("avgcost")));
			closeConn();
			throw new Exception();
		} catch (Exception e) {
			return returnVal;
		}
	}
	
	public List<Transaction> getTransactionList(int userId, String name){
		List<Transaction> returnVal = new ArrayList<Transaction>();
		if (getStock(name, userId)==null) return returnVal;
		int stockId = getStock(name, userId).getId();
		String getTransactions = "SELECT * FROM transactions WHERE userid = " + userId + " and stockid = " + stockId + ";";
		try {
			PreparedStatement getStocksStatement = getConn().prepareStatement(getTransactions);
			ResultSet transactions = getStocksStatement.executeQuery();
			while (transactions.next()) {
				returnVal.add(new Transaction(transactions.getInt("id"), name, 
						transactions.getDouble("amount"), transactions.getDate("date")));
			}
			closeConn();
			throw new Exception();
		} catch (Exception e) {
			return returnVal;
		}
	}
	
	// returns a string for the date of the earliest transactions made by this user in this format: "yyyy-MM-dd"
	public String getEarliestTransaction(int userId) {
		String getTransactions = "SELECT * FROM transactions WHERE userid = " + userId + ";";
		String returnVal = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			PreparedStatement getTransactionsStatement = getConn().prepareStatement(getTransactions);
			ResultSet transactions = getTransactionsStatement.executeQuery();
			while (transactions.next()) {
				if (returnVal.equals("")) returnVal = sdf.format(transactions.getDate("date"));
				else if (transactions.getDate("date").before(sdf.parse(returnVal))) {
					returnVal = sdf.format(transactions.getDate("date"));
				}
			}
			closeConn();
			throw new Exception();
		} catch (Exception e) {
			return returnVal;
		}
	}
	
	// returns a string for the date of the earliest transactions made by this user for this stock in this format: "yyyy-MM-dd"
	public String getEarliestTransaction(int userId, int stockId) {
		String getTransactions = "SELECT * FROM transactions WHERE userid = " + userId + " and stockid = " + stockId+ ";";
		String returnVal = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			PreparedStatement getTransactionsStatement = getConn().prepareStatement(getTransactions);
			ResultSet transactions = getTransactionsStatement.executeQuery();
			while (transactions.next()) {
				if (returnVal.equals("")) returnVal = sdf.format(transactions.getDate("date"));
				else if (transactions.getDate("date").before(sdf.parse(returnVal))) {
					returnVal = sdf.format(transactions.getDate("date"));
				}
			}
			closeConn();
			throw new Exception();
		} catch (Exception e) {
			return returnVal;
		}
	}
	
	// returns total portfolio holdings for this user in USD
	public double getHoldings(int userId) throws UnsupportedEncodingException, JSONException {
		List<Stock> list = getStockList(userId);
		CurrentStockInfo csi = new CurrentStockInfo();
		double returnVal = 0;
		for (int i = 0; i < list.size(); i++) {
			csi.fetchInfo(list.get(i).getAbrev());
			returnVal += getStock(list.get(i).getName(), userId).getHoldings() * csi.getCurrentPrice();
		}
		return returnVal;
	}
	
	// returns holdings for this stock in the user's portfolio
	public double getHoldings(int userId, Stock stock) throws UnsupportedEncodingException, JSONException {
		CurrentStockInfo csi = new CurrentStockInfo();
		csi.fetchInfo(stock.getAbrev());
		return getStock(stock.getName(),userId).getHoldings() * csi.getCurrentPrice();
	}
	
	// returns holdings for these stocks in the user's portfolio
	public double getHoldings(int userId, List<Stock> subset) throws UnsupportedEncodingException, JSONException {
		CurrentStockInfo csi = new CurrentStockInfo();
		double returnVal = 0;
		for (int i = 0; i < subset.size(); i++) {
			csi.fetchInfo(subset.get(i).getAbrev());
			returnVal += getStock(subset.get(i).getName(),userId).getHoldings() * csi.getCurrentPrice();
		}
		return returnVal;
	}
	
	// returns -1 for invalid date
	public double getPastHoldings(int userId, String date) throws UnirestException, UnsupportedEncodingException {
		return getPastHoldings(userId, getStockList(userId), date);
	}
	
	public double getPastHoldings(int userId, Stock stock, String date) throws UnirestException, UnsupportedEncodingException {
		double returnVal = 0, pastHoldings = getStock(stock.getName(),userId).getHoldings();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		Date pastDate = null;
		try {
			pastDate = sdf.parse(date);
		} catch (Exception e) {
			return -1;
		}
		double pastPrice = getLocalHistPrice(date, stock.getAbrev());
		List<Transaction> transactions = getTransactionList(userId, stock.getName());
		for (int t = 0; t < transactions.size(); t++) {
			if (transactions.get(t).getDate().after(pastDate))
				pastHoldings-=transactions.get(t).getAmount();
		}
		returnVal += pastHoldings * pastPrice;
		return returnVal;
	}
	
	public double getPastHoldings(int userId, List<Stock> subset, String date) throws UnirestException, UnsupportedEncodingException {
		double returnVal = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		Date pastDate = null;
		try {
			pastDate = sdf.parse(date);
		} catch (Exception e) {
			return -1;
		}
		for (int i = 0; i < subset.size(); i++) {
			double pastPrice = getLocalHistPrice(date, subset.get(i).getAbrev());
			double pastHoldings = getStock(subset.get(i).getName(), userId).getHoldings();
			List<Transaction> transactions = getTransactionList(userId, subset.get(i).getName());
			for (int t = 0; t < transactions.size(); t++) {
				if (transactions.get(t).getDate().after(pastDate)) {
					pastHoldings-=transactions.get(t).getAmount();
				}
			}
			returnVal += pastHoldings * pastPrice;
		}
		return returnVal;
	}
	
	public String getPastHoldingsRange(int userId, List<String> stocknames, String startDate, String endDate, int interval) {
		System.out.println("Getting the past holdings from " + startDate + " to " + endDate + " for " + stocknames.toString());
		List<Stock> stockList = getStockList(userId, stocknames, true);
		List<String> dates = new ArrayList<String>();
		List<Double> values = new ArrayList<Double>();
		try {
			for (int index = 0; index < stockList.size(); index++) {
				HistoricalDataFetcher hdf = new HistoricalDataFetcher();
				HistoricalDataSet hds = hdf.getData(startDate,endDate, stockList.get(index).getAbrev(), interval);
				Map<LocalDate, Double> reg = hds.getDataMap();
				ArrayList<LocalDate> keysLD = hds.getMapKeys();
				System.out.println("Received " + reg.toString());
				for (int i = keysLD.size()-1; i >= 0;i--) {
					if (index==0) {
						dates.add(keysLD.get(i).toString());
					}
					double holdings = getStock(stockList.get(index).getName(), userId).getHoldings();
					List<Transaction> transactions = getTransactionList(userId, stockList.get(index).getName());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					sdf.setLenient(false);
					for (int t = 0; t < transactions.size(); t++) {
						if (transactions.get(t).getDate().after(sdf.parse(keysLD.get(i).toString()))) {
							holdings-=transactions.get(t).getAmount();
						}
					}
					int ri = (i-(keysLD.size()-1))*-1;
					double val = ((index==0) ? 0 : values.get(ri)) + holdings * reg.get(keysLD.get(i));
					BigDecimal bd = new BigDecimal(val).setScale(2, RoundingMode.HALF_UP);
					val = bd.doubleValue();
					if (index == 0) values.add(val);
					else values.set(ri,val);
				}
			}
			throw new Exception();
		} catch (Exception e) {
			String returnVal = "";
			for (int i = 0; i < dates.size(); i++) {
				returnVal += (dates.get(i) + ":" + values.get(i));
				if (i!=dates.size()-1) returnVal += ",";
			}
			return returnVal;
		}
	}
	
	// value change of total portfolio
	public double getValueChange(int userId) throws UnirestException, UnsupportedEncodingException, JSONException {
		Date date = new Date();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    List<Stock> list = getStockList(userId);
	    System.out.println("YESTERDAY: " + java.time.LocalDate.now(ZoneId.of("America/Los_Angeles")).minusDays(1).toString());
		return getValueChange(userId, java.time.LocalDate.now(ZoneId.of("America/Los_Angeles")).minusDays(1).toString(), list);
	    //return getValueChange(userId, "2020-11-09", list);
	}
	
	public double getValueChange(int userId, String startDate, List<Stock> subset) throws UnirestException, UnsupportedEncodingException, JSONException {
		double curr = getHoldings(userId, subset), past = getPastHoldings(userId, subset, startDate);
		System.out.println("Past Holdings from " + startDate + ": " + past + ", CURRent Holdings: " + curr);
		if (past == 0 && curr == 0) return 0;
		else if (past == 0) return 100;
		else return (((curr-past)/past)*100);
	}
	
//	public double getValueChange(int userId, String startDate, String endDate, List<Stock> subset) throws UnirestException {
//		double end = getPastHoldings(userId, subset, endDate);
//		double start = getPastHoldings(userId, subset, startDate);
//		if (end == -1 || start == -1) return -1;
//		if (start == 0 && end >0) return 100;
//		if (start == 0) return 0;
//		return (((end-start)/start)*100);
//	}
	
	public int createStock(String name, String abrev, int userId) {
		int returnVal = 0;
		// already created stock
		if (getStock(name,userId)!=null) return 1;
		String insertStockInfo = "INSERT INTO stocks(userid, name, abrev, holdings, avgcost)" + 
				"VALUES(?,?,?,?,?);";
		PreparedStatement insertStockInfoStatement;
		try {
			insertStockInfoStatement = getConn().prepareStatement(insertStockInfo);
			insertStockInfoStatement.setInt(1, userId);
			insertStockInfoStatement.setString(2, name);
			insertStockInfoStatement.setString(3, abrev);
			insertStockInfoStatement.setDouble(4, 0);
			insertStockInfoStatement.setDouble(5, 0);
			insertStockInfoStatement.executeUpdate();
			closeConn();
			returnVal = getStock(name,userId).getId();
			throw new Exception();
		} catch (Exception e) {
			return returnVal;
		}
	}
	
	// remove all records of this stock
	public int removeStock(int stockId) {
		int returnVal = 0;
		String sql1 = "DELETE FROM stocks WHERE id = ?;";
		String sql2 = "DELETE FROM transactions WHERE stockid = ?;";
		try {
			PreparedStatement ps1 = getConn().prepareStatement(sql1);
			PreparedStatement ps2 = getConn().prepareStatement(sql2);
			ps1.setInt(1,stockId);
			ps2.setInt(1,stockId);
			ps1.executeUpdate();
			ps2.executeUpdate();
			closeConn();
			returnVal++;
			throw new Exception();
		} catch (Exception e) {
			return returnVal;
		}
	}
	
	public int removeUser(String username) {
		try {
			if (username != null && !username.equals("")) {
				deleteUserInfo(username);
				String sql = "DELETE FROM user WHERE username = ?;";
				PreparedStatement ps = getConn().prepareStatement(sql);
				ps.setString(1,username);
				int rowsaffected = ps.executeUpdate();
				closeConn();
				return rowsaffected;
			}
			throw new Exception("Exception message");
		} catch (Exception e) {
			return 0;
		}
	}
	public String userExists(String username,String password) {
		try {
			if (username != null && password != null && !username.equals("") && !password.equals("d41d8cd98f00b204e9800998ecf8427e")) {
				int userThere = 0;
				String sql = "SELECT username FROM user WHERE username = ? AND pk = ?;";
				PreparedStatement ps = getConn().prepareStatement(sql);
				ps.setString(1,username);
				ps.setString(2, password);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					userThere += 1;
				}
				closeConn();
				if (userThere > 0) {
					return isLocked(username);
				}
				else {
					//do lock out here
					return login(username);
				}
			}
			else {
				throw new Exception("Exception message");
				
			}
		} catch (Exception e) {
			//e.printStackTrace();
			return "Inputs cannot be empty";
		}
	}
	public String isLocked(String username) throws Exception {
		String sqlLogin2 = "SELECT login2 FROM user WHERE username = ? AND login2 > DATETIME('now', '-1 minutes');";
		PreparedStatement ps2 = getConn().prepareStatement(sqlLogin2);
		ps2.setString(1, username);
		ResultSet rs2 = ps2.executeQuery();
		String time2 = "";
		if (rs2.next()) {
			time2 = "User locked out, try again in 1 minute";
		}
		else {
			time2 = "Success";
			clearLoginHistory(username);
		}
		closeConn();
		return time2;
	}
	
	public String login(String username) throws Exception {
		//username problem
		if (getUserId(username) == -1) {
			return "Invalid Credentials";
		}
		//password problem
		String sqlLogin1 = "SELECT * FROM user WHERE username = ?;";
		PreparedStatement ps1 = getConn().prepareStatement(sqlLogin1);
		ps1.setString(1, username);
		ResultSet rs1 = ps1.executeQuery();
		String time1 = "";
		while (rs1.next()) {
			time1 = rs1.getString("login1");
		}
		
		String sqlLogin2 = "SELECT login2 FROM user WHERE username = ? AND login2 > DATETIME('now', '-1 minutes');";
		PreparedStatement ps2 = getConn().prepareStatement(sqlLogin2);
		ps2.setString(1, username);
		ResultSet rs2 = ps2.executeQuery();
		String time2 = "";
		if (rs2.next()) {
			time2 = "User locked out, try again in 1 minute";
		}
		else {
			time2 = "Invalid Credentials";
		}
		
		String sqlCurrentLogin = "UPDATE user SET login1 = DATETIME('now') WHERE username = ?";
		PreparedStatement psLogin = getConn().prepareStatement(sqlCurrentLogin);
		psLogin.setString(1, username);
		psLogin.executeUpdate();
		
		if (time1 != null) {
			String sqlChange2 = "UPDATE user SET login2 = ? WHERE username = ?";
			PreparedStatement psTime2 = getConn().prepareStatement(sqlChange2);
			psTime2.setString(1, time1);
			psTime2.setString(2, username);
			psTime2.executeUpdate();
		}
		closeConn();
		return time2;
	}
	
	public void clearLoginHistory(String username) throws SQLException, Exception {
		String sql = "UPDATE user SET login1 = NULL, login2 = NULL WHERE username = ?";
		PreparedStatement ps = getConn().prepareStatement(sql);
		ps.setString(1, username);
		ps.executeUpdate();
		closeConn();
	}
	
	public int addUser(String username, String password) {
		try {
			//d41d8cd98f00b204e9800998ecf8427e is blank string after hashing
			if (username != null && password != null && !username.equals("") && !password.equals("d41d8cd98f00b204e9800998ecf8427e")) {
				String sql = "INSERT INTO user(username,pk,date) VALUES(?,?,?);";
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				PreparedStatement ps = getConn().prepareStatement(sql);
				ps.setString(1,username);
				ps.setString(2, password);
				ps.setString(3, formatter.format(date));
				int rowsaffected = ps.executeUpdate();
				closeConn();
				return rowsaffected;
			}
			else {
				return -1;
			}
		} catch (Exception e) {
			//user already exists
			return 0;
		}
	}

	public int getUserId(String username) throws Exception {
		String sql = "SELECT id FROM user WHERE username = ?";
		PreparedStatement ps = getConn().prepareStatement(sql);
		ps.setString(1,username);
		ResultSet rs = ps.executeQuery();
		int id = -1;
		while (rs.next()) {
			id = rs.getInt("id");
		}
		closeConn();
		return id;
	}
}