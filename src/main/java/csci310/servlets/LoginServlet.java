package csci310.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import csci310.SQLiteDB;
/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
   		String login = "";
   		int id = 0;
   		
   		SQLiteDB db;
		try {
			db = new SQLiteDB();
			login = db.userExists(username, password);
		
		
			if (login.equals("Success")) {
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				id = db.getUserId(username);
				session.setAttribute("id", id);
				//session.setMaxInactiveInterval(120);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			login = "database error";
		} 
		
   		response.setStatus(HttpServletResponse.SC_OK);
   		response.setContentType("text/plain");
   		PrintWriter out = response.getWriter();
		out.println(login);
		out.flush();
		out.close();

    	
    }

}
