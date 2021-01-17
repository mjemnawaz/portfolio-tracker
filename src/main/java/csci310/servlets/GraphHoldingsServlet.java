package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CSV.Stock;
import csci310.SQLiteDB;

@WebServlet("/GraphHoldingsServlet")
public class GraphHoldingsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public GraphHoldingsServlet() {
        super();
    }

    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String status = "";
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("id"), interval = Integer.parseInt(request.getParameter("interval"));
    	String startDate = request.getParameter("lowerBound"), endDate = request.getParameter("upperBound"),
    			stockList = request.getParameter("stockList");
    	String[] stockArr = stockList.split(",");
    	List<String> stocks = new ArrayList<String>();
    	for (int i = 0; i < stockArr.length; i++) 
    		stocks.add(stockArr[i]);
		try {
			SQLiteDB db = new SQLiteDB();
			status = db.getPastHoldingsRange(userId, stocks, startDate, endDate, interval);
			throw new Exception();
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(status);
			out.flush();
			out.close();
		}
    }
	
}
