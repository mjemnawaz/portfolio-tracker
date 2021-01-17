package csci310.servlets;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.replay;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import CSV.CSV;
import csci310.SQLiteDB;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CSVServlet.class, CSV.class })
@PowerMockIgnore({"javax.management.", "com.sun.org.apache.xerces.", 
	  "javax.xml.", "org.xml.", "org.w3c.dom.",
	  "com.sun.org.apache.xalan.", "javax.activation.*", "jdk.internal.reflect.*"})

public class CSVServletTest extends Mockito {
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	public HttpServletRequest request;
	public HttpServletResponse response;
	String username = "csv", password = "csv";
	int userId = 0;
	SQLiteDB db;
	
	@Mock private HttpSession session;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		db = new SQLiteDB();
		db.addUser(username, password);
		userId = db.getUserId(username);
	}
	
	@After
	public void cleanUp() throws Exception{
		db.removeUser(username);
	}
	
	@Test
	public void testDoPost() throws Exception {
		String filename = "files/input.csv";
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		when(request.getParameter("filename")).thenReturn(filename);
		CSV csvMock = PowerMock.createMock(CSV.class);
		PowerMock.expectNew(CSV.class).andReturn(csvMock);
		expect(csvMock.readCSV(userId, filename)).andReturn(1);
		replay(csvMock, CSV.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new CSVServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Successfully read in file"));
	}
	
	@Test
	public void testDoPostNonexistent() throws Exception {
		String filename = "nonexistent.csv";
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		when(request.getParameter("filename")).thenReturn(filename);
		CSV csvMock = PowerMock.createMock(CSV.class);
		PowerMock.expectNew(CSV.class).andReturn(csvMock);
		expect(csvMock.readCSV(userId, filename)).andReturn(-1);
		replay(csvMock, CSV.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new CSVServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("File does not exist"));
	}
	
	@Test
	public void testDoPostInvalid() throws Exception {
		String filename = "files/empty.csv";
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		when(request.getParameter("filename")).thenReturn(filename);
		CSV csvMock = PowerMock.createMock(CSV.class);
		PowerMock.expectNew(CSV.class).andReturn(csvMock);
		expect(csvMock.readCSV(userId, filename)).andReturn(-2);
		replay(csvMock, CSV.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new CSVServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Incorrect data or empty file"));
	}

}
