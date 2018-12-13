package main.java.cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * This class displayes all the user details, hosted event details, transactions for the events for which tickets are booked
 */
public class UserAccount extends HttpServlet {
	private String browserBody;
	ResultSet userResult;
	ResultSet eventResult;
	ResultSet ticketResult;
	private String user;
	private int userId;

	public UserAccount() {
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession checkSession = request.getSession(false);
		if (checkSession != null) {
			String sessionUser = (String) checkSession.getAttribute("username");
			user = request.getParameter("username");
			if (sessionUser.equals(user)) {
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_OK);

				browserBody = "<html><title>User Account</title>" + "<body><h1>User Details</h1><br><br>";
				out.println(browserBody);
				try {
					userResult = CommonServer.db.selectUserTable();
					eventResult = CommonServer.db.selectEventTable();
					ticketResult = CommonServer.db.selectTicketTable();
					while (userResult.next()) {
						String dbUser = userResult.getString("Email");
						if (dbUser.equals(user)) {
							userId = userResult.getInt("UserID");
							String tempbody = " User Name: " + userResult.getString("Email") + "<br>" + "First Name : "
									+ userResult.getString("Firstname") + "<br>" + "Last Name : "
									+ userResult.getString("Lastname") + "<br>" + "City : "
									+ userResult.getString("City") + "<br>" + "Date of Birth : "
									+ userResult.getDate("DateOfBirth") + "<br>" + "Gender : "
									+ userResult.getString("Gender") + "<br>" + " Contact Number : "
									+ userResult.getString("Contact") + "<br>";
							out.println(tempbody);
						}

					}
					out.print("<h2>Hosted Events</h2><br><br>");
					out.print("<style> table, th, td {border: 1px solid black;}</style>");
					out.print("<table style=\"width:100%\">"
							+ "<tr><th>Event Name</th><th>Location</th><th>Event Date</th><th>Action</th></tr>");
					while (eventResult.next()) {

						int hostId = eventResult.getInt("HostID");
						if (userId == hostId) {
							String name = eventResult.getString("EventName");
							String loc = eventResult.getString("Location");
							String date = eventResult.getString("DateOfEvent");
							int eventId = eventResult.getInt("EventID");
							out.println("<tr>");
							out.print("<td><a href=\"/viewevent?eventId=" + eventId + "&username=" + user + "\" >"
									+ name + "</a></td>");
							out.print("<td>" + loc + "</td>");
							out.print("<td>" + date + "</td>");
							out.print("<td><form action=\"/modifyordelete\" method=\"get\">");
							String temp = "<input type=\"hidden\" name=\"eventId\" value=" + eventId + ">"
									+ "<input type=\"hidden\" name=\"eventname\" value=" + name + ">"
									+ "<input type =\"hidden\" name=\"username\" value=" + user + ">"
									+ "<input type =\"hidden\" name=\"hostId\" value=" + hostId + ">";

							out.print(temp);
							out.print("<input type=\"submit\" value=\"modify/delete\">" + "</form></td>");
							out.println("</tr>");
						}
					}
					out.write("</table>");
					out.print("<h3>Tickets for the Events</h3><br><br>");
					out.print("<style> table, th, td {border: 1px solid black;}</style>");
					out.print("<table style=\"width:100%\">"
							+ "<tr><th>Event Name</th><th>Location</th><th>Event Date</th><th>No of Tickets Purchased</th><th>Action</th></tr>");

					while (ticketResult.next()) {
						int userid = ticketResult.getInt("UserId");
						if (userId == userid) {
							String name = ticketResult.getString("EventName");
							String loc = ticketResult.getString("Location");
							String date = ticketResult.getString("DateOfEvent");
							int eventId = ticketResult.getInt("EventID");
							int ticketcount = ticketResult.getInt("Ticketcount");
							out.println("<tr>");
							out.print("<td><a href=\"/viewevent?eventId=" + eventId + "&username=" + user + "\" >"
									+ name + "</a></td>");
							out.print("<td>" + loc + "</td>");
							out.print("<td>" + date + "</td>");
							out.print("<td>" + ticketcount + "</td>");
							out.print("<td><form action=\"/transfer\" method=\"get\">");
							String temp = "<input type=\"hidden\" name=\"eventId\" value=" + eventId + ">"
									+ "<input type=\"hidden\" name=\"eventname\" value=" + name + ">"
									+ "<input type =\"hidden\" name=\"username\" value=" + user + ">"
									+ "<input type =\"hidden\" name=\"ticketcount\" value=" + ticketcount + ">";

							out.print(temp);
							out.print("<input type=\"submit\" value=\"Transfer\">" + "</form></td>");
							out.println("</tr>");
						}

					}
					out.write("</table>");
					String tempbody = "<form action=\"/logindisplay\" method=\"get\">" + "<br>"
							+ "<input type =\"hidden\" name=\"username\" value=" + user + ">"
							+ "<input type=\"submit\" value=\"Back\">" + "</form>"
							+ "<form action=\"/logout\" method=\"get\">" + "<br>"
							+ "<input type=\"submit\" value=\"Logout\">" + "</form>";
					out.print(tempbody);
					out.write("</body></html>");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				out.close();
			} else {
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.sendRedirect("/homepage");
			}
		} else {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendRedirect("/homepage");
		}
	}

}
