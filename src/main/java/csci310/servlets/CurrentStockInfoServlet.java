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
import csci310.CurrentStockInfo;

@WebServlet("/CurrentStockInfo")
public class CurrentStockInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CurrentStockInfoServlet() {
        super();
    }

    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	String message = "";
    	try {
	    	String ticker = request.getParameter("ticker");
	    	String idStr = request.getParameter("id");
	    	int idInt = Integer.parseInt(idStr);
	    	
	    	HttpSession session = request.getSession();
	    	CurrentStockInfo csi = new CurrentStockInfo();
	    	csi.fetchInfo(ticker);
	    	csi.percentChange(idInt);
	    	request.setAttribute("percentChange", csi.getPercentChange());
			request.setAttribute("currPrice",csi.getCurrentPrice());
			if (csi.getCurrentPrice() != -420.0) {
				message = "Success";
			}
			else {
				message = "Not a valid stock ticker. Please try again";
			}
			
    	} catch (Exception e) {
    		message = "Exception";
    	}
    	request.setAttribute("message", message);
    	response.setStatus(HttpServletResponse.SC_OK);
   		response.setContentType("text/plain");
   		PrintWriter out = response.getWriter();
		out.println(message);
		out.flush();
		out.close();
    }
}