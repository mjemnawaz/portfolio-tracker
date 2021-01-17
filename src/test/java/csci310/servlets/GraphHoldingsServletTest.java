package csci310.servlets;

import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.replay;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.easymock.EasyMock.expect;
import org.easymock.IExpectationSetters;
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
import csci310.HistoricalDataFetcher;
import csci310.HistoricalDataSet;
import csci310.SQLiteDB;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ GraphHoldingsServlet.class, SQLiteDB.class})
@PowerMockIgnore({"javax.security.auth.x500.X500Principal","javax.management.", "com.sun.org.apache.xerces.", 
	  "javax.xml.","java.net.ssl", "org.xml.", "org.w3c.dom.","javax.net.ssl.*",
	  "com.sun.org.apache.xalan.", "javax.activation.*", "jdk.internal.reflect.*"})
public class GraphHoldingsServletTest extends Mockito{
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	public HttpServletRequest request;
	public HttpServletResponse response;
	SQLiteDB db;
	String username = "userdheriqebh@O4", password = "passrgk#>4yub0";
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
		db.insertTransaction("Tesla", "TSLA", 1, 350, userId, "2020-11-01");
		db.insertTransaction("Ali Baba", "BABA", 1, 350, userId, "2020-11-02");
		db.insertTransaction("Google", "GOOGL", 1, 350, userId, "2020-11-03");		// buy one of each stock
		db.insertTransaction("Tesla", "TSLA", -1, 350, userId, "2020-11-04");
		db.insertTransaction("Ali Baba", "BABA", -1, 350, userId, "2020-11-05");
		db.insertTransaction("Google", "GOOGL", -1, 350, userId, "2020-11-06");		// sell one of each stock
	}
	
	@After
	public void cleanUp() {
		db.removeUser(username);
	}
	
	@Test
	public void testDoPost() throws Exception {
		List<String> nameList = new ArrayList<String>();
		nameList.add("Tesla");
		nameList.add("Google");
		nameList.add("Ali Baba");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		when(request.getParameter("stockList")).thenReturn("Tesla,Google,Ali Baba");
		when(request.getParameter("lowerBound")).thenReturn("2020-11-01");
		when(request.getParameter("upperBound")).thenReturn("2020-11-06");
		when(request.getParameter("interval")).thenReturn("0");
		SQLiteDB sqlMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(sqlMock);
		expect(sqlMock.getPastHoldingsRange(userId,nameList,"2020-11-01","2020-11-06",0)).andReturn(
				"2020-11-01:388.04,2020-11-02:711.35,2020-11-03:2355.13,2020-11-04:2041.56,2020-11-05:1762.5,2020-11-06:0.0");
		replay(sqlMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new GraphHoldingsServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString(),sw.toString().contains("2020-11-01:388.04,2020-11-02:711.35,2020-11-03:2355.13,2020-11-04:2041.56,2020-11-05:1762.5,2020-11-06:0.0"));
	}

}
