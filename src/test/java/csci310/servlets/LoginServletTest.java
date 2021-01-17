/**
 * 
 */
package csci310.servlets;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;

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

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.*;

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import csci310.SQLiteDB;
import csci310.servlets.LoginServlet;


/**
 * @author ykc03
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoginServlet.class, SQLiteDB.class })
@PowerMockIgnore({"javax.management.", "com.sun.org.apache.xerces.", 
	  "javax.xml.", "org.xml.", "org.w3c.dom.",
	  "com.sun.org.apache.xalan.", "javax.activation.*", "jdk.internal.reflect.*"})
public class LoginServletTest extends Mockito {
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	public HttpServletRequest request;
	public HttpServletResponse response;
	/*
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;*/


	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
	}
	
	@Test
	public void testDoPost() throws Exception {
		//testing success login
		when(request.getParameter("username")).thenReturn("group4");
		when(request.getParameter("password")).thenReturn("4444");
		
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.userExists("group4", "4444")).andReturn("Success");
		expect(dbMock.getUserId("group4")).andReturn(1);
		replay(dbMock, SQLiteDB.class);
		
		HttpSession session = mock(HttpSession.class);
		when(request.getSession()).thenReturn(session);
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		when(response.getWriter()).thenReturn(pw);
		
		new LoginServlet().doPost(request, response);

		pw.flush();
		assertTrue(sw.toString().contains("Success"));
	}
	
	@Test
	public void testInvalidCredential() throws Exception {
		//testing success login
		when(request.getParameter("username")).thenReturn("group4");
		when(request.getParameter("password")).thenReturn("4444");
		
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.userExists("group4", "4444")).andReturn("Invalid Credentials");
		replay(dbMock, SQLiteDB.class);
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		when(response.getWriter()).thenReturn(pw);
		
		new LoginServlet().doPost(request, response);

		pw.flush();
		assertTrue(sw.toString().contains("Invalid Credentials"));
	}
	
	@Test
	public void testException() throws Exception {
		when(request.getParameter("username")).thenReturn("group4");
		when(request.getParameter("password")).thenReturn("4444");
		
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andThrow(new Exception());
		replay(dbMock, SQLiteDB.class);
		//verify(dbMock, SQLiteDB.class);
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		when(response.getWriter()).thenReturn(pw);
		
		new LoginServlet().doPost(request, response);

		pw.flush();
		assertTrue(sw.toString().contains("database error"));
	}
	

}
