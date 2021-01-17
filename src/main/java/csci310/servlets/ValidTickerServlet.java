package csci310.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mashape.unirest.http.exceptions.UnirestException;

import csci310.HistoricalDataFetcher;
import csci310.HistoricalDataSet;
import csci310.SQLiteDB;

@WebServlet("/validTicker")
public class ValidTickerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ValidTickerServlet() {
        super();
    }

    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	//String lowerBound = request.getParameter("lowerBound");
    	String ticker = request.getParameter("ticker");
    	//String intervalStr = request.getParameter("interval");
    	//String upperBound = request.getParameter("upperBound");
    	String message = "";
    	String nextFile = "";
    	//int interval = Integer.parseInt(intervalStr);
    	RequestDispatcher rd;
    	request.setAttribute("errorMessage", "");
    	try {
    		HistoricalDataFetcher hdf = new HistoricalDataFetcher();
			if (hdf.validTicker(ticker)) {
				HttpSession session = request.getSession();
				//HistoricalDataSet hds = hdf.getData(lowerBound,upperBound, ticker,interval);
				//request.setAttribute("keys", hds.getMapKeys());
				//request.setAttribute("histData",hds.getDataMap());
				message = "Success";
				request.setAttribute("message", message);
				nextFile = "onsuccess.jsp"; //change to whatever the historical
				//data jsp is 
			}
			else {
				message = "Not a valid stock ticker. Please try again";
				request.setAttribute("errorMessage", message);
				nextFile = "onfailure.jsp"; //change to whatever page led to
				//this page
			}
			
		} catch (Exception e) {
			message = "Unirest Exception. See stack trace for more information";
			request.setAttribute("errorMessage",message);
			nextFile = "onfailure.jsp"; //change to whatever page led to
			//this page
		}
    	response.setStatus(HttpServletResponse.SC_OK);
   		response.setContentType("text/plain");
   		PrintWriter out = response.getWriter();
		out.println(message);
		out.flush();
		out.close();
//		rd = request.getRequestDispatcher("/" + nextFile);
//		rd.forward(request, response);
    }
}
