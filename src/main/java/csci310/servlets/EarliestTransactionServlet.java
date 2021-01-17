package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import csci310.SQLiteDB;

@WebServlet("/EarliestTransactionServlet")
public class EarliestTransactionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public EarliestTransactionServlet() {
        super();
    }

    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String status = "";
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("id");
		SQLiteDB db;
		try {
			db = new SQLiteDB();
			status = db.getEarliestTransaction(userId);
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
