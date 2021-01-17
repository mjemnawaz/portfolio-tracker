package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import CSV.CSV;

@WebServlet("/csv")
public class CSVServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public CSVServlet() {
        super();
    }
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getParameter("filename"), status = "";
		int id = 0, returnVal = 0;
		CSV csv = new CSV();
		try {
			HttpSession session = request.getSession();
			id = (int) session.getAttribute("id");
			returnVal = csv.readCSV(id, filename);
			throw new Exception();
		} catch (Exception e) {
			if (returnVal == -2) status = "Incorrect data or empty file";
			if (returnVal == -1) status = "File does not exist";
			if (returnVal ==  1) status = "Successfully read in file";
		} 
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(status);
		out.flush();
		out.close();
	}
	
}
