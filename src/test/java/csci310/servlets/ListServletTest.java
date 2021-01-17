package csci310.servlets;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.replay;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

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
@PrepareForTest({ ListServlet.class, SQLiteDB.class })
@PowerMockIgnore({"javax.management.", "com.sun.org.apache.xerces.", 
	  "javax.xml.", "org.xml.", "org.w3c.dom.",
	  "com.sun.org.apache.xalan.", "javax.activation.*", "jdk.internal.reflect.*"})
public class ListServletTest extends Mockito {

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
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		List<Stock> list = new ArrayList<Stock>();
		db.insertTransaction("Paypal", "PYPL", 5, 100, userId);
		db.insertTransaction("Tesla", "TSLA", 5, 100, userId);
		db.insertTransaction("Ali Baba", "BABA", 2, 300, userId);
		list.add(new Stock(0,userId,"Paypal","PYPL",5,100));
		list.add(new Stock(1,userId,"Tesla","TSLA",5,100));
		list.add(new Stock(2,userId,"Ali Baba","BABA",2,300));
		expect(dbMock.getStockList(userId)).andReturn(list);
		expect(dbMock.getHoldings(userId)).andReturn(1010.0);
		expect(dbMock.getValueChange(userId)).andReturn(7.2);
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new ListServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString(),sw.toString().contains("1010.0|7.2|Paypal|PYPL|Tesla|TSLA|Ali Baba|BABA"));
	}
	
	@Test
	public void testDoPostEmpty() throws Exception {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB dbMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(dbMock);
		expect(dbMock.getStockList(userId)).andReturn(new ArrayList<Stock>());
		expect(dbMock.getHoldings(userId)).andReturn(0.0);
		expect(dbMock.getValueChange(userId)).andReturn(0.0);
		replay(dbMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new ListServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString(),sw.toString().contains("0.0|0"));
	}
}
