/**
 * 
 */
package csci310.servlets;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.replay;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import csci310.SQLiteDB;

/**
 * @author ykc03
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ RegisterServlet.class, SQLiteDB.class })
@PowerMockIgnore({"javax.management.", "com.sun.org.apache.xerces.", 
	  "javax.xml.", "org.xml.", "org.w3c.dom.",
	  "com.sun.org.apache.xalan.", "javax.activation.*", "jdk.internal.reflect.*"})
public class RegisterServletTest extends Mockito {
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	public HttpServletRequest request;
	public HttpServletResponse response;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
	}
	
	@Test
	public void testDoPost() throws Exception {
		when(request.getParameter("username")).thenReturn("group4");
		when(request.getParameter("password")).thenReturn("4444");
		when(request.getParameter("confirm")).thenReturn("4444");
		
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.addUser("group4", "4444")).andReturn(1);
		replay(dbMock, SQLiteDB.class);
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		when(response.getWriter()).thenReturn(pw);
		
		new RegisterServlet().doPost(request, response);

		pw.flush();
		assertTrue(sw.toString().contains("Success"));
	}
	
	@Test
	public void testDoPostNoMatch() throws Exception {
		when(request.getParameter("username")).thenReturn("group4");
		when(request.getParameter("password")).thenReturn("4444");
		when(request.getParameter("confirm")).thenReturn("444");
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		when(response.getWriter()).thenReturn(pw);
		
		new RegisterServlet().doPost(request, response);

		pw.flush();
		assertTrue(sw.toString().contains("Password and Confirm Password must match"));
	}
	
	@Test
	public void testDoPostNull() throws Exception {
		when(request.getParameter("username")).thenReturn("group4");
		when(request.getParameter("password")).thenReturn("4444");
		when(request.getParameter("confirm")).thenReturn(null);
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		when(response.getWriter()).thenReturn(pw);
		
		new RegisterServlet().doPost(request, response);

		pw.flush();
		assertTrue(sw.toString().contains("Password and Confirm Password must match"));
	}
	
	@Test
	public void testDoPostFormat() throws Exception {
		when(request.getParameter("username")).thenReturn(null);
		when(request.getParameter("password")).thenReturn("4444");
		when(request.getParameter("confirm")).thenReturn("4444");
		
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.addUser(null, "4444")).andReturn(-1);
		replay(dbMock, SQLiteDB.class);
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		when(response.getWriter()).thenReturn(pw);
		
		new RegisterServlet().doPost(request, response);

		pw.flush();
		assertTrue(sw.toString().contains("Inputs cannot be empty"));
	}
	
	@Test
	public void testDoPostExist() throws Exception {
		when(request.getParameter("username")).thenReturn("group4");
		when(request.getParameter("password")).thenReturn("4444");
		when(request.getParameter("confirm")).thenReturn("4444");
		
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.addUser("group4", "4444")).andReturn(0);
		replay(dbMock, SQLiteDB.class);
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		when(response.getWriter()).thenReturn(pw);
		
		new RegisterServlet().doPost(request, response);

		pw.flush();
		assertTrue(sw.toString().contains("User already exists"));
	}
	
	@Test
	public void testDoPostException() throws Exception {
		when(request.getParameter("username")).thenReturn("group4");
		when(request.getParameter("password")).thenReturn("4444");
		when(request.getParameter("confirm")).thenReturn("4444");
		
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andThrow(new Exception());
		replay(dbMock, SQLiteDB.class);
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		when(response.getWriter()).thenReturn(pw);
		
		new RegisterServlet().doPost(request, response);

		pw.flush();
		assertTrue(sw.toString().contains("database error"));
	}

}
