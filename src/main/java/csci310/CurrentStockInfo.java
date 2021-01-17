package csci310;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import CSV.Stock;

public class CurrentStockInfo {
	String ticker = "";
	String url = "";
	String stockname = "NONAME";
	int override = 0;
	double percentChange = -420.0;
	double currentPrice = -420.0;
	public CurrentStockInfo() {
		
	}
	

	public void fetchInfo(String ticker) throws UnsupportedEncodingException, JSONException {
		ticker = ticker.toUpperCase();
		HistoricalDataFetcher hdf = new HistoricalDataFetcher();
		try {
			if (hdf.validTicker(ticker)) {
				String encoded = URLEncoder.encode(ticker, "UTF-8");
				url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-charts?region=US&comparisons=%255EGDAXI%252C%255EFCHI&symbol=" + encoded + "&interval=1d&range=5d";
				LocalDate[] da = new LocalDate[5]; 
				HashSet<LocalDate> ds = new HashSet<LocalDate>();
				findStockName(ticker);
				HttpResponse<String> response = Unirest.get(url)
					.header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
					.header("x-rapidapi-key", "101d534745mshca05ed8277458cfp133f78jsnaf2725c91d22")
					.asString();
				JSONObject yhObj = new JSONObject(response.getBody());
				JSONObject chart = yhObj.getJSONObject("chart");
				JSONArray result = chart.getJSONArray("result");
				JSONObject resultZero = result.getJSONObject(0);
				JSONArray timestampsJSON = resultZero.getJSONArray("timestamp");
				JSONObject indicators = resultZero.getJSONObject("indicators");
				JSONArray quote = indicators.getJSONArray("quote");
				JSONObject quoteZero = quote.getJSONObject(0);
				JSONArray closeJSON = quoteZero.getJSONArray("close");
				for (int i = timestampsJSON.length() - 1; i >= 0; i--) {
					long l = timestampsJSON.getLong(i)* 1000;
					Timestamp timestamp = new Timestamp(l);
					LocalDate dummy = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					ds.add(dummy);
				}
				LocalDate localDate = LocalDate.now();
				//adding the range of dates we want
				for (int i = 4; i >= 0; i--) {
					da[i] = localDate;
					localDate = localDate.minusDays(1);
				}
				currentPrice = (Double)closeJSON.get(4);
			}
			else {
				throw new UnirestException("UnirestException");
			}
				
		} catch (UnirestException e) {
			System.out.println("Unirest Error");
		}
	}
	public void findStockName(String ticker) throws UnirestException, UnsupportedEncodingException, JSONException {
		HistoricalDataFetcher hdf = new HistoricalDataFetcher();
		System.out.println("Ticker "+ ticker);
		ticker = ticker.toUpperCase();
		if (hdf.validTicker(ticker)){
			String encoded = URLEncoder.encode(ticker, "UTF-8");
			HttpResponse<String> response = Unirest.get("https://rapidapi.p.rapidapi.com/stock/v2/get-analysis?symbol=" + encoded + "&region=US")
					.header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
					.header("x-rapidapi-key", "101d534745mshca05ed8277458cfp133f78jsnaf2725c91d22")
					.asString();
			System.out.println(response.getBody());
			JSONObject nameObj = new JSONObject(response.getBody());
			JSONObject price = nameObj.getJSONObject("price");
			String name = price.getString("longName");
			stockname = name;
		}
	}
	public double yesterdayPrice(String ticker) throws UnirestException, UnsupportedEncodingException {
		String encoded = URLEncoder.encode(ticker, "UTF-8");
		url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-charts?region=US&comparisons=%255EGDAXI%252C%255EFCHI&symbol=" + encoded + "&interval=1d&range=5d";
		//findStockName(ticker);
		HttpResponse<String> response = Unirest.get(url)
			.header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
			.header("x-rapidapi-key", "101d534745mshca05ed8277458cfp133f78jsnaf2725c91d22")
			.asString();
		JSONObject yhObj = new JSONObject(response.getBody());
		JSONObject chart = yhObj.getJSONObject("chart");
		JSONArray result = chart.getJSONArray("result");
		JSONObject resultZero = result.getJSONObject(0);
		JSONObject indicators = resultZero.getJSONObject("indicators");
		JSONArray quote = indicators.getJSONArray("quote");
		JSONObject quoteZero = quote.getJSONObject(0);
		JSONArray closeJSON = quoteZero.getJSONArray("close");
		return (Double) closeJSON.get(3);
	}
	public double percentChange(int userId) throws Exception {
		SQLiteDB db = new SQLiteDB();
		List<Stock> stocks = db.getStockList(userId);
		double today = db.getHoldings(userId);
		double yesterday = 0.0;
		for (int i = 0; i < stocks.size(); i++) {
			yesterday += (yesterdayPrice(stocks.get(i).getAbrev()) * stocks.get(i).getHoldings());
		}
		percentChange = (today-yesterday)/yesterday;
		return (today-yesterday)/yesterday;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public double getPercentChange() {
		return percentChange;
	}
	public String getStockame() {
		return stockname;
	}

	
}
