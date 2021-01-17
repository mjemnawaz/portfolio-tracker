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

import CSV.Stock;
import csci310.SQLiteDB;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DeleteStockServlet.class, SQLiteDB.class })
@PowerMockIgnore({"javax.management.", "com.sun.org.apache.xerces.", 
	  "javax.xml.", "org.xml.", "org.w3c.dom.",
	  "com.sun.org.apache.xalan.", "javax.activation.*", "jdk.internal.reflect.*"})
public class DeleteStockServletTest extends Mockito {
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	public HttpServletRequest request;
	public HttpServletResponse response;
	SQLiteDB db;
	String username = "user", password = "pass";
	int userId = 0, stockId = 0;
	Stock s;
	
	@Mock private HttpSession session;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		db = new SQLiteDB();
		db.addUser(username, password);
		userId = db.getUserId(username);
		db.insertTransaction("Paypal", "PYPL", 5, 100, userId);
		stockId = db.getStock("Paypal", userId).getId();
		s = new Stock(stockId, userId, "Paypal", "PYPL", 5, 100);
	}
	
	@After
	public void cleanUp() throws Exception{
		db.removeUser(username);
	}
	
	@Test
	public void testDoPost() throws Exception {
		when(request.getParameter("stockname")).thenReturn("Paypal");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.getStock("Paypal", userId)).andReturn(s);
		expect(dbMock.removeStock(stockId)).andReturn(1);
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new DeleteStockServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Deleted stock."));
	}
	
	@Test
	public void testDoPostNonexistent() throws Exception {
		when(request.getParameter("stockname")).thenReturn("Paypal");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.getStock("Paypal", userId)).andReturn(null);
		expect(dbMock.removeStock(stockId)).andReturn(0);
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new DeleteStockServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("This stock does not exist."));
	}

}
