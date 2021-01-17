package csci310.servlets;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class AuthenticationFilterTest extends Mockito {
//	public ServletRequest request;
//	public ServletResponse response;
	public FilterChain chain;
	public FilterConfig fConfig;
	public HttpServletRequest req;
	public HttpServletResponse res;
	
	@Before
	public void setUp() throws Exception {
//		request = mock(ServletRequest.class);
//		response = mock(ServletResponse.class);
		chain = mock(FilterChain.class);
		fConfig = mock(FilterConfig.class);
		req = mock(HttpServletRequest.class);
		res = mock(HttpServletResponse.class);
	}
	
	@Test
	public void testInit() throws ServletException {
		new AuthenticationFilter().init(fConfig);
	}

	@Test
	public void testDoFilter() throws IOException, ServletException {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession(false)).thenReturn(session);
		when(req.getRequestURI()).thenReturn("home.jsp");
		when(session.getAttribute("username")).thenReturn("name");
		
		new AuthenticationFilter().doFilter(req, res, chain);
		assertEquals(session.getAttribute("username"), "name");
	}
	
	@Test
	public void testDoFilterOtherUri() throws IOException, ServletException {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession(false)).thenReturn(session);
		when(req.getRequestURI()).thenReturn("index.jsp");
		when(session.getAttribute("username")).thenReturn("name");
		
		new AuthenticationFilter().doFilter(req, res, chain);
		assertEquals(session.getAttribute("username"), "name");
	}
	
	@Test
	public void testDoFilterUsernameNull() throws IOException, ServletException {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession(false)).thenReturn(session);
		when(req.getRequestURI()).thenReturn("home.jsp");
		when(session.getAttribute("username")).thenReturn(null);
		
		new AuthenticationFilter().doFilter(req, res, chain);
		assertEquals(session.getAttribute("username"), null);
	}
	
	@Test
	public void testDoFilterUsernameNullOtherUri() throws IOException, ServletException {
		HttpSession session = mock(HttpSession.class);
		when(req.getSession(false)).thenReturn(session);
		when(req.getRequestURI()).thenReturn("index.jsp");
		when(session.getAttribute("username")).thenReturn(null);
		
		new AuthenticationFilter().doFilter(req, res, chain);
		assertEquals(session.getAttribute("username"), null);
	}
	
	@Test	//FFT     FFF    FTT     FTF    T?F    T?T
	public void testDoFilterNullSessionOtherUri() throws IOException, ServletException {
		when(req.getSession(false)).thenReturn(null);
		when(req.getRequestURI()).thenReturn("index.jsp");
		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("username")).thenReturn(null);
		
		new AuthenticationFilter().doFilter(req, res, chain);
	}
	
	@Test
	public void testDoFilterNullSession() throws IOException, ServletException {
		when(req.getSession(false)).thenReturn(null);
		when(req.getRequestURI()).thenReturn("home.jsp");
		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("username")).thenReturn(null);
		
		new AuthenticationFilter().doFilter(req, res, chain);
	}

	@Test
	public void testDestroy() {
		new AuthenticationFilter().destroy();
	}

}
