package csci310.servlets;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;

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

import com.mashape.unirest.http.exceptions.UnirestException;

import csci310.CurrentStockInfo;
import csci310.HistoricalDataFetcher;
import csci310.HistoricalDataSet;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CurrentStockInfoServlet.class, CurrentStockInfo.class, HistoricalDataFetcher.class})
@PowerMockIgnore({"javax.security.auth.x500.X500Principal","javax.management.", "com.sun.org.apache.xerces.", 
	  "javax.xml.","java.net.ssl", "org.xml.", "org.w3c.dom.","javax.net.ssl.*",
	  "com.sun.org.apache.xalan.", "javax.activation.*", "jdk.internal.reflect.*"})
public class CurrentStockInfoServletTest extends Mockito {
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
	public void testDoPostSuccess() throws Exception {
		when(request.getParameter("ticker")).thenReturn("AAPL");
		when(request.getParameter("id")).thenReturn("1234");
		CurrentStockInfo csi = PowerMock.createMock(CurrentStockInfo.class);
		expect(csi.getCurrentPrice()).andReturn(10.0);
		expect(csi.getPercentChange()).andReturn(20.0);
		replay(csi, CurrentStockInfo.class);
		HttpSession session = mock(HttpSession.class);
		when(request.getSession()).thenReturn(session);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new CurrentStockInfoServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Success"));
	}
	
	@Test
	public void testDoPostFailure() throws Exception {
		when(request.getParameter("ticker")).thenReturn("AAPLESAUCE");
		when(request.getParameter("id")).thenReturn("1234");
		CurrentStockInfo csi = PowerMock.createMock(CurrentStockInfo.class);
		expect(csi.getCurrentPrice()).andReturn(-420.0);
		expect(csi.getPercentChange()).andReturn(-420.0);
		replay(csi, CurrentStockInfo.class);
		HttpSession session = mock(HttpSession.class);
		when(request.getSession()).thenReturn(session);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new CurrentStockInfoServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Not a valid stock ticker. Please try again"));
	}
	
	@Test
	public void testDoPostException() throws Exception {
		when(request.getParameter("ticker")).thenReturn("AAPLESAUCE");
		when(request.getParameter("id")).thenReturn("1234");
		CurrentStockInfo csi = PowerMock.createMock(CurrentStockInfo.class);
		PowerMock.expectNew(CurrentStockInfo.class).andThrow(new Exception());
		replay(csi, CurrentStockInfo.class);
		
		HttpSession session = mock(HttpSession.class);
		when(request.getSession()).thenReturn(session);	
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);	
		new CurrentStockInfoServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Exception"));
	}
	
}
