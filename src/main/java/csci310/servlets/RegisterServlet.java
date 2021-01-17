package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import csci310.SQLiteDB;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String confirm = request.getParameter("confirm");
    	int register = 0;
    	String message = "";
    	
    	if (confirm == null || !confirm.equals(password)) {
    		message = "Password and Confirm Password must match";
    	}
    	else {
    	
	    	SQLiteDB db;
			try {
				db = new SQLiteDB();
				register = db.addUser(username, password);
				if (register == -1) {
					message = "Inputs cannot be empty";
				}
				else if (register == 0) {
					message = "User already exists";
				}
				else {
					message = "Success";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				message = "database error";
			} 
    	}
    	
		response.setStatus(HttpServletResponse.SC_OK);
   		response.setContentType("text/plain");
   		PrintWriter out = response.getWriter();
		out.println(message);
		out.flush();
		out.close();
	}

}