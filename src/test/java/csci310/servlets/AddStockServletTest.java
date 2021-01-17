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
import csci310.CurrentStockInfo;
import csci310.HistoricalDataFetcher;
import csci310.SQLiteDB;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AddStockServlet.class, SQLiteDB.class, CurrentStockInfo.class, HistoricalDataFetcher.class })
@PowerMockIgnore({"javax.management.", "com.sun.org.apache.xerces.", 
	  "javax.xml.", "org.xml.", "org.w3c.dom.",
	  "com.sun.org.apache.xalan.", "javax.activation.*", "jdk.internal.reflect.*"})
public class AddStockServletTest extends Mockito {
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	public HttpServletRequest request;
	public HttpServletResponse response;
	SQLiteDB db;
	String username = "user", password = "pass";
	int userId = 0;
	
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
		when(request.getParameter("tickersymbol")).thenReturn("PYPL");
		when(request.getParameter("holdings")).thenReturn("2.0");
		when(request.getParameter("buyDate")).thenReturn("2020-09-09");
		when(request.getParameter("sellDate")).thenReturn("2020-10-10");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.getNameFromAbrev("PYPL")).andReturn("Paypal");
		expect(dbMock.getLocalHistPrice("2020-09-09", "PYPL")).andReturn(350.5);
		expect(dbMock.getLocalHistPrice("2020-10-10", "PYPL")).andReturn(400.6);
		expect(dbMock.insertTransaction("Paypal", "PYPL", 2.0, 350.5, userId, "2020-09-09")).andReturn(1);
		expect(dbMock.insertTransaction("Paypal", "PYPL", -2.0, 400.6, userId, "2020-10-10")).andReturn(1);
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new AddStockServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Added stock."));
	}
	
	@Test
	public void testDoPostOnlyBuy() throws Exception {
		when(request.getParameter("tickersymbol")).thenReturn("PYPL");
		when(request.getParameter("holdings")).thenReturn("2.0");
		when(request.getParameter("buyDate")).thenReturn("2020-09-09");
		when(request.getParameter("sellDate")).thenReturn("");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.getNameFromAbrev("PYPL")).andReturn("Paypal");
		expect(dbMock.getLocalHistPrice("2020-09-09", "PYPL")).andReturn(350.5);
		expect(dbMock.insertTransaction("Paypal", "PYPL", 2.0, 350.5, userId, "2020-09-09")).andReturn(1);
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new AddStockServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString(), sw.toString().contains("Added stock."));
	}
	
	@Test
	public void testDoPostWrongDateTiming() throws Exception {
		when(request.getParameter("tickersymbol")).thenReturn("PYPL");
		when(request.getParameter("holdings")).thenReturn("2.0");
		when(request.getParameter("buyDate")).thenReturn("2020-10-20");
		when(request.getParameter("sellDate")).thenReturn("2020-10-10");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.getNameFromAbrev("PYPL")).andReturn("Paypal");
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new AddStockServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Buy date cannot be after sell date."));
	}
	
	@Test
	public void testDoPostInvalidHoldings() throws Exception {
		when(request.getParameter("tickersymbol")).thenReturn("PYPL");
		when(request.getParameter("holdings")).thenReturn("INVALID");
		when(request.getParameter("buyDate")).thenReturn("2020-09-09");
		when(request.getParameter("sellDate")).thenReturn("2020-10-10");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.getNameFromAbrev("PYPL")).andReturn("Paypal");
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new AddStockServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Invalid holdings."));
	}
	
	@Test
	public void testDoPostNegativeHoldings() throws Exception {
		when(request.getParameter("tickersymbol")).thenReturn("PYPL");
		when(request.getParameter("holdings")).thenReturn("-3.5");
		when(request.getParameter("buyDate")).thenReturn("2020-09-09");
		when(request.getParameter("sellDate")).thenReturn("2020-10-10");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.getNameFromAbrev("PYPL")).andReturn("Paypal");
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new AddStockServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Zero or negative holdings."));
	}
	
	@Test
	public void testDoPostInvalidTickerSymbol() throws Exception {
		when(request.getParameter("tickersymbol")).thenReturn("INVALID");
		when(request.getParameter("holdings")).thenReturn("2.0");
		when(request.getParameter("buyDate")).thenReturn("2020-09-09");
		when(request.getParameter("sellDate")).thenReturn("2020-10-10");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.getNameFromAbrev("INVALID")).andReturn("");
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new AddStockServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Invalid ticker symbol."));
	}
	
	@Test
	public void testDoPostInvalidDate() throws Exception {
		when(request.getParameter("tickersymbol")).thenReturn("PYPL");
		when(request.getParameter("holdings")).thenReturn("2.0");
		when(request.getParameter("buyDate")).thenReturn("INVALID");
		when(request.getParameter("sellDate")).thenReturn("2020-10-10");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.getNameFromAbrev("PYPL")).andReturn("Paypal");
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new AddStockServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Invalid date."));
	}
	
	@Test
	public void testDoPostEmptyDate() throws Exception {
		when(request.getParameter("tickersymbol")).thenReturn("PYPL");
		when(request.getParameter("holdings")).thenReturn("2.0");
		when(request.getParameter("buyDate")).thenReturn("");
		when(request.getParameter("sellDate")).thenReturn("2020-10-10");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.getNameFromAbrev("PYPL")).andReturn("Paypal");
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new AddStockServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Buy date cannot be empty."));
	}
	
	@Test
	public void testDoPostFutureDate() throws Exception {
		when(request.getParameter("tickersymbol")).thenReturn("PYPL");
		when(request.getParameter("holdings")).thenReturn("2.0");
		when(request.getParameter("buyDate")).thenReturn("2020-10-01");
		when(request.getParameter("sellDate")).thenReturn("2020-12-30");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.getNameFromAbrev("PYPL")).andReturn("Paypal");
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new AddStockServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Cannot use future dates."));
	}
}
