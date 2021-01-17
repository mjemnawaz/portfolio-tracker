package csci310;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;    
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TimeZone;
import java.time.temporal.ChronoUnit;
import org.json.JSONArray;
import org.json.JSONObject;

public class HistoricalDataFetcher {
	public HistoricalDataFetcher() {
		
	}
	public boolean validTicker(String ticker) throws UnirestException, UnsupportedEncodingException {
		String encoded = URLEncoder.encode(ticker, "UTF-8");
		String url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-charts?region=US&symbol=" + encoded;
		//String encoded =  URLEncoder.encode(url,"UTF-8");
		HttpResponse<String> response = Unirest.get(url)
				.header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
				.header("x-rapidapi-key", "101d534745mshca05ed8277458cfp133f78jsnaf2725c91d22")
				.asString();
		JSONObject obj = new JSONObject(response.getBody());
		JSONObject chart = obj.getJSONObject("chart");
		if (chart.optJSONObject("error") != null) {
			System.out.println("error");
			return false;
		}
		return true;
	}
	public HistoricalDataSet getData(String startDate, String endDate, String ticker,int increment) throws UnirestException, UnsupportedEncodingException {
		if (!validTicker(ticker)) {
			return null;
		}
		System.out.println("S&P Branch");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		//convert String to LocalDate
		LocalDate startD = LocalDate.parse(startDate,formatter);
		LocalDate endD = LocalDate.parse(endDate, formatter);
		LocalDate localDate = LocalDate.now();
		int range = -1*(int) ChronoUnit.DAYS.between(localDate,startD)+1;
		int upperBound = -1*(int) ChronoUnit.DAYS.between(localDate,endD);
		
		int newIncrement = getIncrement(increment);
		//String body = "{\"chart\":{\"result\":[{\"meta\":{\"currency\":\"USD\",\"symbol\":\"AAPL\",\"exchangeName\":\"NMS\",\"instrumentType\":\"EQUITY\",\"firstTradeDate\":345479400,\"regularMarketTime\":1601921420,\"gmtoffset\":-14400,\"timezone\":\"EDT\",\"exchangeTimezoneName\":\"America/New_York\",\"regularMarketPrice\":115.39,\"chartPreviousClose\":91.027,\"priceHint\":2,\"currentTradingPeriod\":{\"pre\":{\"timezone\":\"EDT\",\"end\":1601904600,\"start\":1601884800,\"gmtoffset\":-14400},\"regular\":{\"timezone\":\"EDT\",\"end\":1601928000,\"start\":1601904600,\"gmtoffset\":-14400},\"post\":{\"timezone\":\"EDT\",\"end\":1601942400,\"start\":1601928000,\"gmtoffset\":-14400}},\"dataGranularity\":\"1d\",\"range\":\"3mo\",\"validRanges\":[\"5d\",\"max\",\"10y\",\"ytd\",\"6mo\",\"5y\",\"1mo\",\"1y\",\"3mo\",\"2y\",\"1d\"]},\"timestamp\":[1594042200,1594128600,1594215000,1594301400,1594387800,1594647000,1594733400,1594819800,1594906200,1594992600,1595251800,1595338200,1595424600,1595511000,1595597400,1595856600,1595943000,1596029400,1596115800,1596202200,1596461400,1596547800,1596634200,1596720600,1596807000,1597066200,1597152600,1597239000,1597325400,1597411800,1597671000,1597757400,1597843800,1597930200,1598016600,1598275800,1598362200,1598448600,1598535000,1598621400,1598880600,1598967000,1599053400,1599139800,1599226200,1599485400,1599571800,1599658200,1599744600,1599831000,1600090200,1600176600,1600263000,1600349400,1600435800,1600695000,1600781400,1600867800,1600954200,1601040600,1601299800,1601386200,1601472600,1601559000,1601645400,1601921420],\"comparisons\":[{\"symbol\":\"^FCHI\",\"high\":[5121.72,5056.52,5040.52,5014.85,4974.37,5060.43,5007.46,5145.33,5105.12,5091.13,5100.79,5172.98,5099.29,5083.97,4982.12,4957.77,4950.01,4978.5,4952.38,4902.9,4899.52,4920.8,4954.6,4951.86,4896.21,4937.63,5052.12,5096.53,5079.02,5018.95,4995.55,5001.29,4977.23,4937.95,4939.25,5013.7,5073.61,5050.1,5052.26,5031.53,5067.55,4993.56,5062.01,5131.39,5068.88,5064.64,5061.6,5058.35,5062.89,5053.63,5087.52,5087.99,5091.52,5053.32,5041.33,4949.76,4827.0,4871.45,4806.83,4758.05,4849.97,4846.63,4803.44,4871.83,4824.88,4876.86],\"low\":[5059.07,5009.03,4969.83,4911.56,4882.5,4980.12,4941.73,5039.19,5048.32,5045.07,5015.11,5097.42,5028.48,5019.21,4925.41,4923.19,4893.63,4945.58,4801.02,4783.69,4763.6,4856.65,4907.41,4860.98,4843.9,4878.58,4949.05,5010.8,5037.55,4921.58,4937.72,4924.15,4917.53,4888.15,4839.08,4948.71,5008.27,4977.94,5005.46,4970.64,4942.18,4892.83,4959.64,4982.34,4928.79,4979.87,4935.38,4973.36,5005.84,4997.86,5034.91,5035.1,5029.56,4995.65,4978.18,4779.21,4772.84,4802.26,4744.04,4666.26,4788.18,4806.45,4803.44,4804.77,4824.88,4842.26],\"chartPreviousClose\":5007.14,\"close\":[5081.51,5043.73,4981.13,4921.01,4970.48,5056.23,5007.46,5108.98,5085.28,5069.42,5093.18,5104.28,5037.12,5033.76,4956.43,4939.62,4928.94,4958.74,4852.94,4783.69,4875.93,4889.52,4933.34,4885.13,4889.52,4909.51,5027.99,5073.31,5042.38,4962.93,4971.94,4938.06,4977.23,4911.24,4896.33,5007.89,5008.27,5048.43,5015.97,5002.94,4947.22,4938.1,5031.74,5009.52,4965.07,5053.72,4973.52,5042.98,5023.93,5034.14,5051.88,5067.93,5074.42,5039.5,4978.18,4792.04,4772.84,4802.26,4762.62,4729.66,4843.27,4832.07,4803.44,4824.04,4824.88,4871.87],\"open\":[5112.5,5054.04,5004.82,5006.52,4891.61,5026.2,4990.48,5045.92,5076.52,5088.0,5058.48,5129.78,5098.48,5064.72,4973.73,4944.3,4930.1,4948.48,4952.08,4866.24,4797.06,4905.66,4916.96,4919.41,4873.1,4905.27,4952.41,5017.73,5055.42,5018.95,4972.59,4951.98,4934.79,4914.69,4927.61,4948.71,5023.06,4991.63,5052.26,5031.31,5041.34,4974.42,4969.62,5074.64,4974.69,4979.87,5052.88,4980.46,5039.48,5014.97,5071.87,5056.9,5065.75,5005.89,5035.94,4949.76,4809.57,4824.17,4746.1,4754.81,4806.14,4832.87,4803.44,4850.2,4824.88,4863.63]},{\"symbol\":\"^GDAXI\",\"high\":[12842.59,12661.12,12670.19,12709.68,12649.99,12836.41,12697.91,12999.84,12919.23,12954.25,13063.69,13313.9,13197.92,13217.73,12935.5,12908.66,12936.01,12864.39,12768.71,12524.75,12698.56,12768.58,12757.03,12799.21,12691.66,12753.12,13046.13,13101.12,13075.25,12971.08,12960.84,13052.84,12980.7,12891.14,12911.27,13104.31,13221.82,13192.32,13218.05,13147.24,13148.19,13127.28,13303.17,13460.46,13127.08,13117.61,13147.52,13257.54,13297.71,13255.47,13339.14,13262.81,13277.23,13245.87,13263.44,12998.9,12698.12,12830.7,12684.8,12622.77,12873.4,12859.14,12869.96,12836.65,12690.25,12842.48],\"low\":[12655.21,12525.18,12463.22,12459.16,12416.69,12688.94,12535.9,12773.44,12805.47,12870.27,12811.72,13147.3,13067.06,13072.82,12812.02,12811.44,12746.46,12789.97,12253.92,12313.36,12365.61,12528.07,12632.99,12519.85,12517.44,12605.82,12802.06,12879.11,12956.3,12797.52,12847.85,12807.0,12833.8,12755.52,12633.71,12924.7,13060.87,13010.53,13087.37,12951.26,12923.76,12850.3,13062.17,13004.83,12753.69,12920.92,12856.55,12973.46,13161.93,13117.46,13159.72,13138.2,13181.56,13035.94,13116.25,12505.16,12592.97,12642.97,12512.5,12341.58,12660.15,12738.3,12698.42,12671.23,12539.86,12727.75],\"chartPreviousClose\":12528.18,\"close\":[12733.45,12616.8,12494.81,12489.46,12633.71,12799.97,12697.36,12930.98,12874.97,12919.61,13046.92,13171.83,13104.25,13103.39,12838.06,12838.66,12835.28,12822.26,12379.65,12313.36,12646.98,12600.87,12660.25,12591.68,12674.88,12687.53,12946.89,13058.63,12993.71,12901.34,12920.66,12881.76,12977.33,12830.0,12764.8,13066.54,13061.62,13190.15,13096.36,13033.2,12945.38,12974.25,13243.43,13057.77,12842.66,13100.28,12968.33,13237.21,13208.89,13202.84,13193.66,13217.67,13255.37,13208.12,13116.25,12542.44,12594.39,12642.97,12606.57,12469.2,12870.87,12825.82,12760.73,12730.77,12689.04,12828.31],\"open\":[12774.77,12660.09,12542.58,12639.17,12442.22,12817.19,12642.23,12812.11,12857.3,12915.27,12898.11,13194.93,13165.29,13183.5,12919.31,12865.75,12886.23,12819.63,12762.12,12403.1,12374.46,12744.13,12667.12,12664.55,12634.79,12729.1,12806.97,12917.34,13044.47,12965.22,12924.88,12836.87,12838.63,12829.39,12879.45,12945.97,13136.77,13041.83,13206.58,13140.6,13103.93,13037.2,13071.66,13354.72,12916.12,12931.4,13126.23,12990.52,13265.51,13198.74,13329.61,13220.81,13224.16,13060.18,13210.86,12998.9,12629.71,12737.32,12520.47,12615.91,12673.73,12817.79,12754.77,12812.08,12558.78,12824.05]}],\"indicators\":{\"quote\":[{\"open\":[92.5,93.85250091552734,94.18000030517578,96.26249694824219,95.33499908447266,97.26499938964844,94.83999633789062,98.98999786376953,96.5625,96.98750305175781,96.4175033569336,99.17250061035156,96.69249725341797,96.99749755859375,90.98750305175781,93.70999908447266,94.36750030517578,93.75,94.1875,102.88500213623047,108.19999694824219,109.13249969482422,109.37750244140625,110.40499877929688,113.20500183105469,112.5999984741211,111.97000122070312,110.49749755859375,114.43000030517578,114.83000183105469,116.0625,114.35250091552734,115.98249816894531,115.75,119.26249694824219,128.69749450683594,124.69750213623047,126.18000030517578,127.14250183105469,126.01249694824219,127.58000183105469,132.75999450683594,137.58999633789062,126.91000366210938,120.06999969482422,null,113.94999694824219,117.26000213623047,120.36000061035156,114.56999969482422,114.72000122070312,118.33000183105469,115.2300033569336,109.72000122070312,110.4000015258789,104.54000091552734,112.68000030517578,111.62000274658203,105.16999816894531,108.43000030517578,115.01000213623047,114.55000305175781,113.79000091552734,117.63999938964844,112.88999938964844,113.91000366210938],\"low\":[92.46749877929688,93.05750274658203,94.08999633789062,94.67250061035156,94.70500183105469,95.25749969482422,93.87750244140625,96.48999786376953,95.90499877929688,95.83999633789062,96.0625,96.74250030517578,96.60250091552734,92.01000213623047,89.1449966430664,93.4800033569336,93.24749755859375,93.7125015258789,93.76750183105469,100.82499694824219,107.89250183105469,108.38749694824219,108.89749908447266,109.79750061035156,110.2925033569336,110.0,109.10749816894531,110.29750061035156,113.92749786376953,113.04499816894531,113.9625015258789,114.00749969482422,115.61000061035156,115.73249816894531,119.25,123.9375,123.05249786376953,125.0824966430664,123.8324966430664,124.57749938964844,126.0,130.52999877929688,127.0,120.5,110.88999938964844,null,112.68000030517578,115.26000213623047,112.5,110.0,112.80000305175781,113.61000061035156,112.04000091552734,108.70999908447266,106.08999633789062,103.0999984741211,109.16000366210938,106.7699966430664,105.0,107.66999816894531,112.77999877929688,113.56999969482422,113.62000274658203,115.83000183105469,112.22000122070312,113.55000305175781],\"volume\":[118655600,112424400,117092000,125642800,90257200,191649200,170989200,153198000,110577600,92186800,90318000,103646000,89001600,197004400,185438800,121214000,103625600,90329200,158130000,374336800,308151200,173071600,121992000,202428800,198045600,212403600,187902400,165944800,210082000,165565200,119561600,105633600,145538000,126907200,338054800,345937600,211495600,163022400,155552400,187630000,225702700,152470100,200119000,257599600,332607200,null,231366600,176940500,182274400,180860300,140150100,184642000,154679000,178011000,287104900,195713800,183055400,150718700,167743300,149981400,137672400,99382200,142675200,116120400,144310900,66814608],\"high\":[93.94499969482422,94.65499877929688,95.375,96.31749725341797,95.9800033569336,99.95500183105469,97.25499725341797,99.24749755859375,97.40499877929688,97.14749908447266,98.5,99.25,97.9749984741211,97.07749938964844,92.97000122070312,94.90499877929688,94.55000305175781,95.2300033569336,96.29750061035156,106.41500091552734,111.63749694824219,110.79000091552734,110.39250183105469,114.4124984741211,113.67500305175781,113.7750015258789,112.48249816894531,113.2750015258789,116.0425033569336,115.0,116.0875015258789,116.0,117.1624984741211,118.39250183105469,124.86750030517578,128.78500366210938,125.18000030517578,126.99250030517578,127.48500061035156,126.44249725341797,131.0,134.8000030517578,137.97999572753906,128.83999633789062,123.69999694824219,null,118.98999786376953,119.13999938964844,120.5,115.2300033569336,115.93000030517578,118.83000183105469,116.0,112.19999694824219,110.87999725341797,110.19000244140625,112.86000061035156,112.11000061035156,110.25,112.44000244140625,115.31999969482422,115.30999755859375,117.26000213623047,117.72000122070312,115.37000274658203,116.11000061035156],\"close\":[93.4625015258789,93.17250061035156,95.34249877929688,95.75250244140625,95.91999816894531,95.47750091552734,97.05750274658203,97.7249984741211,96.52249908447266,96.32749938964844,98.35749816894531,97.0,97.27249908447266,92.84500122070312,92.61499786376953,94.80999755859375,93.25250244140625,95.04000091552734,96.19000244140625,106.26000213623047,108.9375,109.66500091552734,110.0625,113.90249633789062,111.11250305175781,112.72750091552734,109.375,113.01000213623047,115.01000213623047,114.90750122070312,114.60749816894531,115.5625,115.7074966430664,118.2750015258789,124.37000274658203,125.85749816894531,124.82499694824219,126.52249908447266,125.01000213623047,124.80750274658203,129.0399932861328,134.17999267578125,131.39999389648438,120.87999725341797,120.95999908447266,null,112.81999969482422,117.31999969482422,113.48999786376953,112.0,115.36000061035156,115.54000091552734,112.12999725341797,110.33999633789062,106.83999633789062,110.08000183105469,111.80999755859375,107.12000274658203,108.22000122070312,112.27999877929688,114.95999908447266,114.08999633789062,115.80999755859375,116.79000091552734,113.0199966430664,115.38999938964844]}],\"adjclose\":[{\"adjclose\":[93.2942886352539,93.00481414794922,95.17090606689453,95.58016967773438,95.74736785888672,95.3056640625,96.88282012939453,97.54911804199219,96.3487777709961,96.15412902832031,98.18048095703125,96.82542419433594,97.0974349975586,92.67790222167969,92.44831085205078,94.63935852050781,93.08467102050781,94.86894989013672,96.01688385009766,106.06875610351562,108.74143981933594,109.4676284790039,109.86441040039062,113.69750213623047,111.11250305175781,112.72750091552734,109.375,113.01000213623047,115.01000213623047,114.90750122070312,114.60749816894531,115.5625,115.7074966430664,118.2750015258789,124.37000274658203,125.85749816894531,124.82499694824219,126.52249908447266,125.01000213623047,124.80750274658203,129.0399932861328,134.17999267578125,131.39999389648438,120.87999725341797,120.95999908447266,null,112.81999969482422,117.31999969482422,113.48999786376953,112.0,115.36000061035156,115.54000091552734,112.12999725341797,110.33999633789062,106.83999633789062,110.08000183105469,111.80999755859375,107.12000274658203,108.22000122070312,112.27999877929688,114.95999908447266,114.08999633789062,115.80999755859375,116.79000091552734,113.0199966430664,115.38999938964844]}]}}],\"error\":null}}\n"; 
		Double[] ca = new Double[range];
		ArrayList<Double> closing = new ArrayList<Double>();
		ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
		LocalDate[] da = new LocalDate[range]; 
		HashSet<LocalDate> ds = new HashSet<LocalDate>();  
        String strrange = getRange(range);
		LocalDate upper = LocalDate.now();
		upper = upper.minusDays(upperBound);
		String encoded = URLEncoder.encode(ticker, "UTF-8");
		String url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-charts?region=US&comparisons=%255EGDAXI%252C%255EFCHI&symbol=" + encoded + "&interval=1d&range=" + strrange;
		HttpResponse<String> response = Unirest.get(url)
			.header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
			.header("x-rapidapi-key", "101d534745mshca05ed8277458cfp133f78jsnaf2725c91d22")
			.asString();
		JSONObject jsonObj = new JSONObject(response.getBody());
		JSONObject chart = jsonObj.getJSONObject("chart");
		JSONArray result = chart.getJSONArray("result");
		JSONObject resultZero = result.getJSONObject(0);
		JSONArray timestampsJSON = resultZero.getJSONArray("timestamp");
		JSONObject indicators = resultZero.getJSONObject("indicators");
		JSONArray quote = indicators.getJSONArray("quote");
		JSONObject quoteZero = quote.getJSONObject(0);
		JSONArray closeJSON = quoteZero.getJSONArray("close");
		
		//adding to set
		for (int i = timestampsJSON.length() - 1; i >= 0; i--) {
			long l = timestampsJSON.getLong(i)* 1000;
			Timestamp timestamp = new Timestamp(l);
			LocalDate dummy = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			ds.add(dummy);
			
		}
		
		//adding the range of dates we want
		for (int i = range-1; i >= 0; i--) {
			da[i] = localDate;
			localDate = localDate.minusDays(1);
		}
		//close functionality
		int counter = 0;
		int closeIndex = closeJSON.length()-1;
		System.out.println(closeJSON.length()-1);
		System.out.println(range);
		System.out.println("!!");
		Double closeHold = (Double) closeJSON.get(closeIndex);
		for (int i = range - 1; i >= 0; i--) {
			System.out.println(closeIndex);
			if (ds.contains(da[i])) {
				//if (counter % newIncrement == 0) {
					closing.add(closeHold);
					dates.add(da[i]);
				//}
				closeIndex--;
				if (closeIndex >= 0) {
					if (!JSONObject.NULL.equals(closeJSON.get(closeIndex))) {
						closeHold = (Double) closeJSON.get(closeIndex);
					}
				}
				
				
			}
			else {
				System.out.println("here");
				//if (counter % newIncrement == 0) {
					closing.add(closeHold);
					dates.add(da[i]);
				//}
			}
			counter++;
		}
		ArrayList<Double> modClosing = new ArrayList<Double>();
		ArrayList<LocalDate> modDates = new ArrayList<LocalDate>();
		
		for (int i = 0; i < dates.size(); i++) {
			if (dates.get(i).compareTo(upper) <= 0) {
				modClosing.add(closing.get(i));
				modDates.add(dates.get(i));
			}
		}
		
		ArrayList<Double> secondModClosing = new ArrayList<Double>();
		ArrayList<LocalDate> secondModDates = new ArrayList<LocalDate>();
		int count = 0;
		for (int i = 0; i < modDates.size(); i++) {
			if (count % newIncrement == 0) {
				secondModClosing.add(modClosing.get(i));
				secondModDates.add(modDates.get(i));
				
			}
			count ++;
		}
		HistoricalDataSet hds = new HistoricalDataSet(secondModClosing,secondModDates,secondModDates.size());
		return hds;
	}
	public double getHistPrice(String date, String ticker) throws UnirestException, UnsupportedEncodingException {
		if (!validTicker(ticker)) {
			return 0.0;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		//convert String to LocalDate
		LocalDate startD = LocalDate.parse(date,formatter);
		LocalDate localDate = LocalDate.now();
		int range = (-1*(int) ChronoUnit.DAYS.between(localDate,startD)) + 1;
		Double[] ca = new Double[range];
		ArrayList<Double> closing = new ArrayList<Double>();
		ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
		LocalDate[] da = new LocalDate[range]; 
		HashSet<LocalDate> ds = new HashSet<LocalDate>();  
        String strrange = getRange(range);
        String encoded = URLEncoder.encode(ticker, "UTF-8");
		String url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-charts?region=US&comparisons=%255EGDAXI%252C%255EFCHI&symbol=" + encoded + "&interval=1d&range=" + strrange;
		HttpResponse<String> response = Unirest.get(url)
			.header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
			.header("x-rapidapi-key", "101d534745mshca05ed8277458cfp133f78jsnaf2725c91d22")
			.asString();
		JSONObject jsonObj = new JSONObject(response.getBody());
		JSONObject chart = jsonObj.getJSONObject("chart");
		JSONArray result = chart.getJSONArray("result");
		JSONObject resultZero = result.getJSONObject(0);
		JSONArray timestampsJSON = resultZero.getJSONArray("timestamp");
		JSONObject indicators = resultZero.getJSONObject("indicators");
		JSONArray quote = indicators.getJSONArray("quote");
		JSONObject quoteZero = quote.getJSONObject(0);
		JSONArray closeJSON = quoteZero.getJSONArray("close");
		
		//adding to set
		for (int i = timestampsJSON.length() - 1; i >= 0; i--) {
			long l = timestampsJSON.getLong(i)* 1000;
			Timestamp timestamp = new Timestamp(l);
			LocalDate dummy = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			ds.add(dummy);
			
		}
		//adding the range of dates we want
		for (int i = range-1; i >= 0; i--) {
			da[i] = localDate;
			localDate = localDate.minusDays(1);
		}
		//close functionality
		int closeIndex = closeJSON.length()-1;
		Double closeHold = (Double) closeJSON.get(closeIndex);
		for (int i = range - 1; i >= 0; i--) {
			if (ds.contains(da[i])) {
				closing.add(closeHold);
				dates.add(da[i]);
				closeIndex--;
				if (!JSONObject.NULL.equals(closeJSON.get(closeIndex))) {
					closeHold = (Double) closeJSON.get(closeIndex);
				}
			}
			else {
				closing.add(closeHold);
				dates.add(da[i]);
			}
		}
		double price = 0.0;
		for (int i = 0; i < range; i++) {
			System.out.println(startD);
			if (dates.get(i).equals(startD)) {
				price = closing.get(i);
			}
		}
		return price;
	}
	public String getRange(int range) {
		if (range >= 186) {
			return "1y";
		}
		else if (range > 93) {
			return "6mo";		
		}
		else if (range > 5) {
			return "3mo";
		}
		else  {
			return "5d";
		}
	}
	public int getIncrement(int increment) {
		int newIncrement = 1;
		if (increment == 1) {
			newIncrement = 7;
		}
		if (increment == 2) {
			newIncrement = 30;
		}
		return newIncrement;
	}
}
//	public static void main(String[] args) throws UnirestException, UnsupportedEncodingException {
//		HistoricalDataFetcher hdf = new HistoricalDataFetcher();
//		System.out.println("last 61 days, interval of 1 month");
//		HistoricalDataSet hds3 = hdf.getData("2020-08-02","2020-10-20","AAPL",2);
//		ArrayList<LocalDate> mk3 = hds3.getMapKeys();
//		Map<LocalDate,Double> mp3 = hds3.getDataMap();
//		for (int i = 0; i < hds3.dateCount(); i++) {
//			System.out.println(mk3.get(i) + " " + mp3.get(mk3.get(i)));
//		}
//		System.out.println("----------------------------------");
//		System.out.println("last 10 days, interval of 1 day");
//		HistoricalDataSet hds1 = hdf.getData("2020-08-02","2020-10-24","^GSPC",0);
//		ArrayList<LocalDate> mk1 = hds1.getMapKeys();
//		Map<LocalDate,Double> mp1 = hds1.getDataMap();
//		for (int i = 0; i < hds1.dateCount(); i++) {
//			System.out.println(mk1.get(i) + " " + mp1.get(mk1.get(i)));
//		}
//		System.out.println("----------------------------------");
//		System.out.println("last 10 days, interval of 1 week");
//		Map<LocalDate, Double> reg = hds1.getDataMap();
//		Map<String, String> str = new HashMap<>();
//		ArrayList<LocalDate> keysLD = hds1.getMapKeys();
//		String message = "";
//		for (int i = 0; i < keysLD.size();i++) {
//			String dateString = keysLD.get(i).toString();
//			System.out.println(dateString);
//			String doubleString = reg.get(keysLD.get(i)).toString();
//			message += (dateString + ":" + doubleString);
//			if (i != keysLD.size()-1) {
//				message += ",";
//			}
//			str.put(dateString, doubleString);
//		}
//		JSONObject json = new JSONObject(str);
//		json.toString();
		//String message = "";
		//message = json.toString();
//		System.out.println(message);
//		HistoricalDataSet hds2 = hdf.getData("2020-10-11","2020-10-26","AAPL",1);
//		ArrayList<LocalDate> mk2 = hds2.getMapKeys();
//		Map<LocalDate,Double> mp2 = hds2.getDataMap();
//		for (int i = 0; i < hds2.dateCount(); i++) {
//			System.out.println(mk2.get(i) + " " + mp2.get(mk2.get(i)));
//		}
//		System.out.println("----------------------------------");

//	}
//}


