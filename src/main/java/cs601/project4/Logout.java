package main.java.cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/*
 * This class is called when the user tried to logout and the corresponding session is invalidated
 */

public class Logout extends HttpServlet{
	private String browserBody;

	public Logout() {
		// TODO Auto-generated constructor stub
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);
		if (session != null) {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			PrintWriter out = response.getWriter();
		
			browserBody = "<html><title>Logout</title>" + "<body>User logged out!!</body>" + "<br>"
					+ "Login to book events and Enjoy!!" + "<form action=\"/login\" method=\"get\">" + "<br>"
					+ "<input type=\"submit\" value=\"login\">" + "</form>" + "New User? Register here: "
					+ "Go back to Homepage : <form action=\"/homepage\" method=\"get\">" + "<br>"
					+ "<input type=\"submit\" value=\"HomePage\">" + "</form></html>";
			out.println(browserBody);
			session.invalidate();
			out.close();
			return;
		}
	}
}
