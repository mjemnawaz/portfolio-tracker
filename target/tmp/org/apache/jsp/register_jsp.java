/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: jetty/9.4.31.v20200723
 * Generated at: 2020-11-13 23:41:40 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class register_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("<head>\r\n");
      out.write("\t<title>Register</title>\r\n");
      out.write("\t<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" integrity=\"sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z\" crossorigin=\"anonymous\">\r\n");
      out.write("\t<!-- \tMeta tag required to make web page responsive -->\r\n");
      out.write("\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"css/login.css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"container-fluid pageHeader\">\r\n");
      out.write("\t\t<div class = \"row pt-3 pb-3\">\r\n");
      out.write("\t\t\t<div class = \"col-7\">\r\n");
      out.write("\t\t\t\t<h2 class=\"textColor\">USC CS 310 Stock Portfolio Management</h2>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class = \"col-5 text-right\">\r\n");
      out.write("\t\t\t\t<h2 class=\"textColor\"><a class=\"formatLink\" href=\"index.jsp\">Login</a></h2>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"container text-center mt-5 titleColor\">\r\n");
      out.write("\t\t\t<h1>Register</h1>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<!-- need to add action file that corresponds to homepage -->\r\n");
      out.write("\t<form class=\"mt-5 mb-5 col-5 mx-auto\" id=\"form-container\" action=\"index.jsp\" method=\"POST\" onsubmit = \"return validateRegistration();\">\r\n");
      out.write("\r\n");
      out.write("\t\t<!-- place to put error message from backend -->\r\n");
      out.write("\t\t<div class=\"text-danger pt-3 errorMessage\" id = \"registerError\">\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"row justify-content-center pt-3\">\r\n");
      out.write("\t\t\t<div class =\"col-12\">\r\n");
      out.write("\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label class=\"label\" for=\"username-register\">\r\n");
      out.write("\t\t\t\t\t<span class=\"text-danger\">* </span>Username\r\n");
      out.write("\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" name=\"username-register\" class=\"form-control\" id=\"username-register\">\r\n");
      out.write("\t\t\t\t\t<div class=\"text-danger\" id=\"username-error\"></div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"row justify-content-center pt-3\">\r\n");
      out.write("\t\t\t<div class=\"col-12\">\r\n");
      out.write("\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label class=\"label\" for=\"password-register\">\r\n");
      out.write("\t\t\t\t\t<span class=\"text-danger\">* </span>Password\r\n");
      out.write("\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t<input type=\"password\" name=\"password-register\" class=\"form-control\" id=\"password-register\">\r\n");
      out.write("\t\t\t\t\t<div class=\"text-danger\" id=\"password-error\"></div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"row justify-content-center pt-3\">\r\n");
      out.write("\t\t\t<div class=\"col-12\">\r\n");
      out.write("\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t<label class=\"label\" for=\"password-register-confirm\">\r\n");
      out.write("\t\t\t\t\t<span class=\"text-danger\">* </span>Confirm Password\r\n");
      out.write("\t\t\t\t\t</label>\r\n");
      out.write("\t\t\t\t\t<input type=\"password\" name=\"password-register-confirm\" class=\"form-control\" id=\"password-register-confirm\">\r\n");
      out.write("\t\t\t\t\t<div class=\"text-danger\" id=\"password-confirm-error\"></div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"text-center pt-3 pb-3\">\r\n");
      out.write("\t\t\t<button type=\"submit\" class=\"btn btn-primary\" id=\"registerButton\">Create User</button>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"text-center pt-3 pb-3\">\r\n");
      out.write("\t\t\t<a href=\"index.jsp\" class=\"btn btn-danger ml-3\" id=\"cancelRegistration\">Cancel</a>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t</form>\r\n");
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t// function to send username/password for login to backend\r\n");
      out.write("\t\tfunction validateRegistration() {\r\n");
      out.write("\t\t\tvar xhttp = new XMLHttpRequest(); //only need this for ajax\r\n");
      out.write("\t\t\tvar username = document.getElementById(\"username-register\").value;\r\n");
      out.write("\t\t\tvar password = document.getElementById(\"password-register\").value;\r\n");
      out.write("\t\t\tvar confirm = document.getElementById(\"password-register-confirm\").value;\r\n");
      out.write("\t\t\tpassword = md5(password);\r\n");
      out.write("\t\t\tconfirm = md5(confirm);\r\n");
      out.write("\t\t\txhttp.open(\"POST\", \"/register?username=\" + username + \"&password=\"+ password + \"&confirm=\" + confirm, false); //false means synchronous\r\n");
      out.write("\t\t\txhttp.send();\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t// handle any error messages\r\n");
      out.write("\t\t\tif(xhttp.responseText.trim() != \"Success\") {\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tdocument.getElementById(\"registerError\").innerHTML = xhttp.responseText;\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\treturn true; // redirect to file pointed at by action field above\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t</script>\r\n");
      out.write("\r\n");
      out.write("\t<script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\" integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\" crossorigin=\"anonymous\"></script>\r\n");
      out.write("\t<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js\" integrity=\"sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN\" crossorigin=\"anonymous\"></script>\r\n");
      out.write("\t<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js\" integrity=\"sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV\" crossorigin=\"anonymous\"></script>\r\n");
      out.write("\t<script src=\"https://rawgit.com/emn178/js-md5/master/build/md5.min.js\"></script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
