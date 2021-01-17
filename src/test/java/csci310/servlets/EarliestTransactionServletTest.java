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

import csci310.SQLiteDB;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EarliestTransactionServlet.class, SQLiteDB.class})
@PowerMockIgnore({"javax.security.auth.x500.X500Principal","javax.management.", "com.sun.org.apache.xerces.", 
	  "javax.xml.","java.net.ssl", "org.xml.", "org.w3c.dom.","javax.net.ssl.*",
	  "com.sun.org.apache.xalan.", "javax.activation.*", "jdk.internal.reflect.*"})
public class EarliestTransactionServletTest extends Mockito {

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
		db.insertTransaction("Tesla", "TSLA", 1, 350, userId, "2020-11-01");		// earliest transaction
		db.insertTransaction("Ali Baba", "BABA", 1, 350, userId, "2020-11-02");
		db.insertTransaction("Google", "GOOGL", 1, 350, userId, "2020-11-03");
		db.insertTransaction("Tesla", "TSLA", -1, 350, userId, "2020-11-04");
		db.insertTransaction("Ali Baba", "BABA", -1, 350, userId, "2020-11-05");
		db.insertTransaction("Google", "GOOGL", -1, 350, userId, "2020-11-06");
	}
	
	@After
	public void cleanUp() {
		db.removeUser(username);
	}
	
	@Test
	public void testDoPost() throws Exception {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("id")).thenReturn(userId);
		SQLiteDB sqlMock = PowerMock.createMock(SQLiteDB.class);
		PowerMock.expectNew(SQLiteDB.class).andReturn(sqlMock);
		expect(sqlMock.getEarliestTransaction(userId)).andReturn("2020-11-01");
		replay(sqlMock, SQLiteDB.class);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new EarliestTransactionServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString(),sw.toString().contains("2020-11-01"));
	}

}
