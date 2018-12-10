package main.java.cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserRegistration extends HttpServlet {
	private String browserBody;
	private String firstName;
	private String lastName;
	private String location;
	private String contact;
	private String dob;
	private String gender;
	private String email;
	private String pwd;
	private byte[] dbPwd;
	private String salt;

	public UserRegistration() {
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		PrintWriter out = response.getWriter();

		browserBody = "<html><title>userregistration</title>"
				+ "<body>Please enter the following details and Register</body>"
				+ "<form action=\"/userregistration\" method=\"post\">" + "<br>"
				+ "FirstName : <input type=\"text\" name=\"firstname\" value=\"\" required></br><br>"
				+ "LastName : <input type=\"text\" name=\"lastname\" value=\"\" required></br><br>"
				+ "City : <input type=\"text\" name=\"location\" value=\"\" required></br><br>"
				+ "Contact Number : <input type=\"tel\" name=\"contact\" pattern=\"[0-9]{3}-[0-9]{3}-[0-9]{4}\" value=\"\" required></br><br>"
				+ "Date Of Birth : <input type=\"date\" name=\"date\" value=\"\" max=\"2018-12-01\" required></br><br>"
				+ "Gender : <input type=\"text\" name=\"sex\" value=\"\" required></br><br>"
				+ "Email ID : <input type=\"email\" name=\"email\" value=\"\" required></br><br>"
				+ "password : <input type=\"password\" name=\"pwd\" value=\"\" required></br><br>"
				+ "<input type=\"submit\" value=\"Register\">" + "</form>" + "</html>";

		out.println(browserBody);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		firstName = request.getParameter("firstname");
		lastName = request.getParameter("lastname");
		location = request.getParameter("location");
		contact = request.getParameter("contact");
		gender = request.getParameter("sex");
		email = request.getParameter("email");
		pwd = request.getParameter("pwd");
		dob = request.getParameter("date");
		
		byte[] userSalt = CommonServer.snh.createSalt();
		System.out.println("USER REGISTRATION");

		System.out.println("Salt Array: " + Arrays.toString(userSalt));
		
		try {
			dbPwd = CommonServer.snh.createHash(pwd, userSalt);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println("Password : "+ pwd);
		System.out.println();
		try {
			CommonServer.db.updateUserTable(firstName, lastName, location, contact, gender, email, dbPwd, dob, userSalt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = response.getWriter();
		browserBody = "<html><title>userregistration</title><body>The user is created!<br/>Thanks for visiting</body>"
				+ "<form action=\"/login\" method=\"get\">" 
				+ "<input type=\"submit\" value=\"Login to Continue\">"+ "</form>"
				+"</html>"; 
		out.println(browserBody);

	}

}
