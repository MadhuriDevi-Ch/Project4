package main.java.cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomePage extends HttpServlet {
	private String browserBody;
	ResultSet eventResult;

	public HomePage() {
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println(request.getSession());
		System.out.println(request);
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		try {
			eventResult = CommonServer.db.selectEventTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();

		browserBody = "<html><title>homepage</title>" + "<body>Welcome!!</body>" + "<br>"
				+ "Login to book events and Enjoy!!" + "<form action=\"/login\" method=\"get\">" + "<br>"
				+ "<input type=\"submit\" value=\"login\">" + "</form>" + "New User? Register here: "
				+ "<form action=\"/userregistration\" method=\"get\">" + "<br>"
				+ "<input type=\"submit\" value=\"Register\">" + "</form>"
				+ "<style> table, th, td {border: 1px solid black;}</style>"
				// convert data into table form and return
				+ "<table style=\"width:100%\">" + "<tr><th>Event Name</th><th>Location</th><th>Event Date</th></tr>";
		out.println(browserBody);		
		try {
			while (eventResult.next()) {
				String name = eventResult.getString("EventName");
				String loc = eventResult.getString("Location");
				String date = eventResult.getString("DateOfEvent");
				int eventId = eventResult.getInt("EventID");
				out.println("<tr>");
				out.print("<td><a href=\"/viewevent?eventId=" + eventId + "\" >" + name + "</a></td>");
				out.print("<td>" + loc + "</td>");
				out.print("<td>" + date + "</td>");
				out.println("</tr>");
				

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.write("</table>");
		out.println("</html>");
		out.close();

	}

	

}
