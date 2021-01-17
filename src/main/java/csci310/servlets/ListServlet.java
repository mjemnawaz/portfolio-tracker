package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CSV.Stock;
import csci310.SQLiteDB;

@WebServlet("/list")
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ListServlet() {
        super();
    }
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String status = "";
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("id");
		List<Stock> list = null;
		try {
			SQLiteDB db = new SQLiteDB();
			list = db.getStockList(userId);
			status += db.getHoldings(userId) + "|" + db.getValueChange(userId);
			throw new Exception();
		} catch (Exception e) {
			for (int i = 0; i < list.size(); i++) {
				status+="|"+(list.get(i).getName()+"|"+list.get(i).getAbrev());
			}
		}
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(status);
		out.flush();
		out.close();
	}
}
