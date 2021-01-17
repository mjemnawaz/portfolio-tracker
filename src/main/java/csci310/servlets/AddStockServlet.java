package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mashape.unirest.http.exceptions.UnirestException;

import csci310.CurrentStockInfo;
import csci310.SQLiteDB;

@WebServlet("/addstock")
public class AddStockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public AddStockServlet() {
        super();
    }
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String tickerSymbol = request.getParameter("tickersymbol"), holdingsString = request.getParameter("holdings"), 
				buyDateStr = request.getParameter("buyDate"), sellDateStr = request.getParameter("sellDate"), 
				status = "", stockName = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		Date buyDate = null, sellDate = null;
		boolean valid = true, date = false, dateTiming = false, empty = false, past = false;
		double holdings = -1;
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("id");
		try {
			if (buyDateStr.equals("")) throw new Exception();
			empty = true;
			buyDate = sdf.parse(buyDateStr);
			if (!sellDateStr.equals("")) sellDate = sdf.parse(sellDateStr);
			date = true;
			if (!sellDateStr.equals("") && buyDate.after(sellDate)) throw new Exception();
			dateTiming = true;
			if (!sellDateStr.equals("") && sellDate.after(new Date())) throw new Exception();
		    past = true;
			SQLiteDB db = new SQLiteDB();
			stockName = db.getNameFromAbrev(tickerSymbol);
			if (stockName.equals("")) {
				valid = false;
				throw new Exception();
			}
			holdings = Double.parseDouble(holdingsString);
			if (holdings<=0) {
				holdings = -2;
				throw new Exception();
			}
			double buyPrice = db.getLocalHistPrice(buyDateStr, tickerSymbol);
			db.insertTransaction(stockName, tickerSymbol, holdings, buyPrice, userId, buyDateStr);
			if (!sellDateStr.equals("")) {
				double sellPrice = db.getLocalHistPrice(sellDateStr, tickerSymbol);
				db.insertTransaction(stockName, tickerSymbol, holdings*-1, sellPrice, userId, sellDateStr);
			}
			throw new Exception();
		} catch (Exception e) {
			if (!empty) status = "Buy date cannot be empty.";
			else if (!date) status = "Invalid date.";
			else if (!dateTiming) status = "Buy date cannot be after sell date.";
			else if (!past) status = "Cannot use future dates.";
			else if (!valid) status = "Invalid ticker symbol.";
			else if (holdings == -1) status = "Invalid holdings.";
			else if (holdings == -2) status = "Zero or negative holdings.";
			else status = "Added stock.";
		}
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(status);
		out.flush();
		out.close();
	}
}
