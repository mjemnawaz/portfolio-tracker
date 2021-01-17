/**
 * 
 */
package csci310.servlets;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author ykc03
 *
 */
public class LogoutServletTest extends Mockito {
	
	@Test
	public void testDoPost() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(request.getSession(false)).thenReturn(session);
		when(session.getAttribute("username")).thenReturn(null);
		
		new LogoutServlet().doPost(request, response);
		
		assertTrue(session.getAttribute("username") == null);
	}
	
	@Test
	public void testNull() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getSession(false)).thenReturn(null);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("username")).thenReturn(null);
		
		new LogoutServlet().doPost(request, response);
		assertTrue(session.getAttribute("username") == null);
	}

}
