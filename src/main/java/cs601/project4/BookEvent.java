package main.java.cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * This class is called when when the user tries to book ticket for Event
 */
public class BookEvent extends HttpServlet {
	private String browserBody;
	private int eventId;
	private String name;
	private String date;
	private int ticketCount;
	private String userEmail;

	public BookEvent() {
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession checkSession = request.getSession(false);
		if (checkSession != null ) {
		String sessionUser = (String) checkSession.getAttribute("username");
		userEmail = request.getParameter("username");
//		System.out.println(request.getSession());
//		System.out.println(request);
		if (sessionUser.equals(userEmail)) {
			name = request.getParameter("eventname");
			date = request.getParameter("eventDate");
			eventId = Integer.parseInt(request.getParameter("eventId"));

			int maxTicket = Integer.parseInt(request.getParameter("ticketcount"));

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

			PrintWriter out = response.getWriter();

			browserBody = "<html><title>Event Booking</title>"
					+ "<body>Please enter the following details and Pay</body>"
					+ "<form action=\"/bookevent\" method=\"post\">" + "<br>" + " Event Name: " + name + "<br>"
					+ "Event Date : " + date + "<br>"
					+ "No of Tickets : <input type=\"number\" name=\"count\" value=\"\" min=\"1\" max=\"" + maxTicket
					+ "\" required><br><br>" + "<input type=\"hidden\" name=\"eventId\" value=" + eventId + ">"
					+ "<input type=\"hidden\" name=\"eventname\" value=" + name + ">"
					+ "<input type=\"hidden\" name=\"eventDate\" value=" + date + ">"
					+ "<input type =\"hidden\" name=\"username\" value=" + userEmail + ">"
					+ "<input type=\"submit\" value=\"Buy\">" + "</form>"
					+ "<form action=\"/logindisplay\" method=\"get\">" + "<br>"
					+ "<input type =\"hidden\" name=\"username\" value=" + userEmail + ">"
					+ "<input type=\"submit\" value=\"Cancel\">" + "</form>"
					+ "<form action=\"/logout\" method=\"get\">" + "<br>" + "<input type=\"submit\" value=\"Logout\">"
					+ "</form>" + "</html>";

			out.println(browserBody);
			out.close();
		} else {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendRedirect("/homepage");
		}
		}else {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendRedirect("/homepage");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		name = request.getParameter("eventname");
		date = request.getParameter("eventDate");
		eventId = Integer.parseInt(request.getParameter("eventId"));
		ticketCount = Integer.parseInt(request.getParameter("count"));
		userEmail = request.getParameter("username");

		PrintWriter out = response.getWriter();
		try {
			int userId = CommonServer.db.updateTransactionTable(eventId, ticketCount, userEmail);
			if (userId != 0) {
				CommonServer.db.updateTicketSummaryTable(eventId, ticketCount, userId);
				browserBody = "<html><title>Transfer Tickets</title>" + "<body>Booking Sucess</body>"
						+ "<form action=\"/useraccount\" method=\"get\">" + "<br>"
						+ "<input type =\"hidden\" name=\"username\" value=" + userEmail + ">"
						+ "<input type=\"submit\" value=\"Account Details\">" + "</form></html>";

				out.println(browserBody);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
