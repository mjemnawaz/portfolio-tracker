package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CSV.Stock;
import csci310.SQLiteDB;

@WebServlet("/deletestock")
public class DeleteStockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public DeleteStockServlet() {
        super();
    }
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("stockname"), status = "";
		int returnVal = 0, stockId = 0;
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("id");
		try {
			SQLiteDB db = new SQLiteDB();
			Stock s = db.getStock(name, userId);
			stockId = s.getId();
			returnVal = db.removeStock(stockId);
			throw new Exception();
		} catch (Exception e) {
			if (returnVal == 1) status = "Deleted stock.";
			else status = "This stock does not exist.";
		}
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(status);
		out.flush();
		out.close();
	}
}
