
package csci310.servlets;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;

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
import csci310.HistoricalDataFetcher;
import csci310.HistoricalDataSet;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ HistoricalDataServlet.class, HistoricalDataFetcher.class, HistoricalDataSet.class})
@PowerMockIgnore({"javax.security.auth.x500.X500Principal","javax.management.", "com.sun.org.apache.xerces.", 
	  "javax.xml.","java.net.ssl", "org.xml.", "org.w3c.dom.","javax.net.ssl.*",
	  "com.sun.org.apache.xalan.", "javax.activation.*", "jdk.internal.reflect.*"})
public class HistoricalDataServletTest extends Mockito {
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
    when(request.getParameter("lowerBound")).thenReturn("2020-08-10");
		when(request.getParameter("ticker")).thenReturn("AAPL");
		when(request.getParameter("interval")).thenReturn("0");
		when(request.getParameter("upperBound")).thenReturn("2020-10-20");
		HistoricalDataFetcher hdf = PowerMock.createMock(HistoricalDataFetcher.class);
		PowerMock.expectNew(HistoricalDataFetcher.class).andReturn(hdf);
		expect(hdf.validTicker("AAPL")).andReturn(true);
		ArrayList<Double> ca = new ArrayList<Double>();
		ca.add(1.0);
		ca.add(1.1);

		ArrayList<LocalDate> da = new ArrayList<LocalDate>();
		da.add(LocalDate.now());
		da.add(LocalDate.now().plusDays(1));
		int range = 2;
		expect(hdf.getData("2020-08-10","2020-10-20","AAPL",0)).andReturn(new HistoricalDataSet(ca,da,range));
		replay(hdf, HistoricalDataFetcher.class);
		HttpSession session = mock(HttpSession.class);
		when(request.getSession()).thenReturn(session);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new HistoricalDataServlet().doPost(request, response);
		pw.flush();
		assertFalse(sw.toString().contains("Not a valid stock ticker. Please try again"));
		assertFalse(sw.toString().contains("Unirest Exception. See stack trace for more information"));

	}
	
	@Test
	public void testDoPostFailure() throws Exception {
    when(request.getParameter("lowerBound")).thenReturn("2020-08-10");
		when(request.getParameter("ticker")).thenReturn("AAPLESAUCE");
		when(request.getParameter("interval")).thenReturn("0");
		when(request.getParameter("upperBound")).thenReturn("2020-10-20");
		HistoricalDataFetcher hdf = PowerMock.createMock(HistoricalDataFetcher.class);
		expect(hdf.validTicker("AAPLESAUCE")).andReturn(false);
		replay(hdf, HistoricalDataFetcher.class);
		
		HttpSession session = mock(HttpSession.class);
		when(request.getSession()).thenReturn(session);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
		new HistoricalDataServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Not a valid stock ticker. Please try again"));
	}
	
	@Test
	public void testDoPostException() throws Exception {
		when(request.getParameter("lowerBound")).thenReturn("2020-08-10");
		when(request.getParameter("ticker")).thenReturn("AAPL");
		when(request.getParameter("interval")).thenReturn("0");
		when(request.getParameter("upperBound")).thenReturn("2020-10-20");
		HistoricalDataFetcher hdf = PowerMock.createMock(HistoricalDataFetcher.class);
		PowerMock.expectNew(HistoricalDataFetcher.class).andThrow(new Exception());
		replay(hdf, HistoricalDataFetcher.class);
		
		HttpSession session = mock(HttpSession.class);
		when(request.getSession()).thenReturn(session);	
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);	
		new HistoricalDataServlet().doPost(request, response);
		pw.flush();
		assertTrue(sw.toString().contains("Unirest Exception. See stack trace for more information"));
	}
}
	
