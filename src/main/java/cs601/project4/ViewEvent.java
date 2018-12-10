package main.java.cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewEvent extends HttpServlet {

	ResultSet eventResult;
	private int eventId;
	private String username;
	private String browserBody;

	public ViewEvent() {
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		eventId = Integer.parseInt(request.getParameter("eventId"));
		username = request.getParameter("username");
		PrintWriter out = response.getWriter();
		
		try {
			eventResult = CommonServer.db.selectEventTable();
			while (eventResult.next()) {
				int dbevent = eventResult.getInt("EventID");
				if (dbevent == eventId) {
					

					browserBody = "<html><title>Event Details</title>" + "<body>Below are the event details</body><br><br>"
							+ " Event Name: " + eventResult.getString("EventName") + "<br>" + "Event Date : "
							+ eventResult.getDate("DateOfEvent") + "<br>" + "Event Category : "
							+ eventResult.getString("Category") + "<br>" + "Event Held at : "
							+ eventResult.getString("Location") + "<br>" + "Event Description : "
							+ eventResult.getString("Description") + "<br>";
					out.println(browserBody);
					if (username == null) {
						String tempBody = "<form action=\"/homepage\" method=\"get\">" + "<br>"
								+ "<input type=\"submit\" value=\"back\">" + "</form>" + "</html>";
						out.println(tempBody);
					} else {
						String tempBody = "<form action=\"/bookevent\" method=\"post\">" + "<br>"
								+ "No of Tickets : <input type=\"number\" name=\"count\" value=\"\"  min=\"1\" required><br><br>"
								+ "<input type=\"hidden\" name=\"eventId\" value=" + eventId + ">"
								+ "<input type=\"hidden\" name=\"eventname\" value="
								+ eventResult.getString("EventName") + ">"
								+ "<input type=\"hidden\" name=\"eventDate\" value="
								+ eventResult.getString("DateOfEvent") + ">"
								+ "<input type =\"hidden\" name=\"username\" value=" + username + ">"
								+ "<input type=\"submit\" value=\"Pay\">" + "</form>";

						out.println(tempBody);
					}

					String tempbody =  "<form action=\"/logindisplay\" method=\"get\">" + "<br>"
							+ "<input type =\"hidden\" name=\"username\" value=" + username + ">"
							+ "<input type=\"submit\" value=\"Cancel\">" + "</form>"
							+"<form action=\"/logout\" method=\"get\">" + "<br>"
							+ "<input type=\"submit\" value=\"Logout\">" + "</form>";
					out.print(tempbody);
					out.println("</html>");
				} 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
