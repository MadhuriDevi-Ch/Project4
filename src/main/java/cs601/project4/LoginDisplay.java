package main.java.cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginDisplay extends HttpServlet{
	private String browserBody;
	ResultSet eventResult;
	private String userEmail;

	public LoginDisplay() {
		// TODO Auto-generated constructor stub
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		userEmail = request.getParameter("username");
		PrintWriter out = response.getWriter();
		try {
			eventResult = CommonServer.db.selectEventTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		browserBody = "<html><title>login</title>" + "<body>Hello!!	</body>" + "<br>"
				+ "<style> table, th, td {border: 1px solid black;}</style>"
				// convert data into table form and return
				+ "<table style=\"width:100%\">"
				+ "<tr><th>Event Name</th><th>Location</th><th>Event Date</th><th>Book</th></tr>";
		out.println(browserBody);
		try {
			while (eventResult.next()) {
				String name = eventResult.getString("EventName");
				String loc = eventResult.getString("Location");
				String date = eventResult.getString("DateOfEvent");
				int eventId = eventResult.getInt("EventID");
				int maxTicket = eventResult.getInt("MaxTickets");
				out.println("<tr>");
				out.print("<td><a href=\"/viewevent?eventId=" + eventId + "&username=" + userEmail + "\" >" + name + "</a></td>");
				out.print("<td>" + loc + "</td>");
				out.print("<td>" + date + "</td>");
				out.print("<td><form action=\"/bookevent\" method=\"get\">");
				String temp = "<input type=\"hidden\" name=\"eventId\" value=" + eventId + ">"
						+ "<input type=\"hidden\" name=\"eventname\" value=" + name + ">"
						+ "<input type=\"hidden\" name=\"eventDate\" value=" + date + ">"
						+ "<input type =\"hidden\" name=\"username\" value=" + userEmail + ">"
						+ "<input type =\"hidden\" name=\"ticketcount\" value=" + maxTicket + ">";

				out.print(temp);
				out.print("<input type=\"submit\" value=\"Book\">" + "</form></td>");
				out.println("</tr>");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.write("</table>");

		browserBody = "<form action=\"/useraccount\" method=\"get\">" + "<br>"
				+ "<input type =\"hidden\" name=\"username\" value=" + userEmail + ">"
				+ "<input type=\"submit\" value=\"Account Details\">" + "</form>"
				+ "<form action=\"/newevent\" method=\"get\">" + "<br>"
				+ "<input type =\"hidden\" name=\"username\" value=" + userEmail + ">"
				+ "<input type=\"submit\" value=\" Create Event\">" + "</form>"
				+ "<form action=\"/logout\" method=\"get\">" + "<br>"
				+ "<input type=\"submit\" value=\"Logout\">" + "</form>" + "</html>";
		out.println(browserBody);
	
	}

}
