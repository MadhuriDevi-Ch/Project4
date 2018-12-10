package main.java.cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*
 * This class is called when the user is trying to login. 
 * If the login validation is successful the page will be redirected to LoginDisplay.class
 */
public class UserLogin extends HttpServlet {
	private String browserBody;
	private String userEmail;
	private String userPwd;
	private byte[] dbSalt;
	private byte[] dbPwd;
	ResultSet eventResult;
	private boolean status;

	public UserLogin() {
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		System.out.println(request.getSession());
		System.out.println(request);
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		PrintWriter out = response.getWriter();

		browserBody = "<html><title>login</title>" + "<body>Please enter the following details and Login"
				+ "<form action=\"/login\" method=\"post\">" + "<br>"
				+ "Email ID/ UserName : <input type=\"email\" name=\"email\" value=\"\" required></br><br>"
				+ "password : <input type=\"password\" name=\"pwd\" value=\"\" required></br><br>"
				+ "<input type=\"submit\" value=\"Login\">" + "</form>" + "</body></html>";

		out.println(browserBody);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		userEmail = request.getParameter("email");
		userPwd = request.getParameter("pwd");

		PrintWriter out = response.getWriter();

		System.out.println(request.getSession());
		System.out.println(request);
		try {
			dbPwd = CommonServer.db.getDbPassword(userEmail);
			dbSalt = CommonServer.db.getSalt(userEmail);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("USER VALIDATION");
		System.out.println("User Email : " + userEmail);
		System.out.println("User password : " + userPwd);
		System.out.println("db Password : " + dbPwd);
		System.out.println("db Salt : " + dbSalt);
		byte[] saltDb = dbSalt;
		System.out.println("DB Salt Byte Array: " + Arrays.toString(saltDb));

		try {
			status = CommonServer.snh.validateLogin(userPwd, dbPwd, dbSalt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(status);
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		if (status) {

			// get the old session and invalidate
			HttpSession oldSession = request.getSession(false);
			System.out.println(oldSession);
			System.out.println();
			if (oldSession != null) {
				oldSession.invalidate();
			}
			// generate a new session
			HttpSession newSession = request.getSession(true);
			System.out.println(newSession);
			System.out.println(oldSession);

			response.sendRedirect("/logindisplay?username="+userEmail);
			loginDisplay(out);
			
		} else {
			browserBody = "<html><title>homepage</title>" + "<body>Invalid credentials. Try again or register</body>"
					+ "<br>" + "<form action=\"/homepage\" method=\"get\">"
					+ "<input type=\"submit\" value=\"Homepage\">" + "</form>"
					+ "<form action=\"/login\" method=\"get\">" + "<input type=\"submit\" value=\"Login Again\">"
					+ "</form>" + "<form action=\"/userregistration\" method=\"get\">"
					+ "<input type=\"submit\" value=\"Register\">" + "</form>" + "</html>";
			out.println(browserBody);
		}
	}
	
	public void loginDisplay(PrintWriter out){
		
	}


	}


