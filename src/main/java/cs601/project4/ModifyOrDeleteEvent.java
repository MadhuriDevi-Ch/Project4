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
 * This class is part of the additional functionality feature: to modify or delete and event from the back end tables
 * if the user wished to delete the event created by user then all the transaction and tickets are deleted.
 * else the event details are updated
 */
public class ModifyOrDeleteEvent extends HttpServlet {
	private String browserBody;
	private String useremail;
	private int eventId;
	private int hostId;
	private ResultSet eventResult;

	public ModifyOrDeleteEvent() {
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession checkSession = request.getSession(false);
		if (checkSession != null) {
			String sessionUser = (String) checkSession.getAttribute("username");
			useremail = request.getParameter("username");
			if (sessionUser.equals(useremail)) {
				eventId = Integer.parseInt(request.getParameter("eventId"));
				hostId = Integer.parseInt(request.getParameter("hostId"));
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_OK);

				PrintWriter out = response.getWriter();

				try {
					eventResult = CommonServer.db.selectEventTable();
					while (eventResult.next()) {
						int dbHost = eventResult.getInt("HostID");
						int dbevent = eventResult.getInt("EventID");
						if (eventId == dbevent) {
							if (hostId == dbHost) {
								browserBody = "<html><title>updateevent</title>"
										+ "<body>Please enter the following details and Update</body>"
										+ "<form action=\"/modifyordelete\" method=\"post\">" + "<br>"
										+ "EventName : <input type=\"text\" name=\"eventname\" value=\""
										+ eventResult.getString("EventName") + "\" required></br><br>"
										+ "Category : <input type=\"text\" name=\"eventcategory\" value=\""
										+ eventResult.getString("Category") + "\" required></br><br>"
										+ "City : <input type=\"text\" name=\"eventlocation\" value=\""
										+ eventResult.getString("Location") + "\" required></br><br>"
										+ "Contact Number : <input type=\"tel\" name=\"contact\" pattern=\"[0-9]{3}-[0-9]{3}-[0-9]{4}\" value=\""
										+ eventResult.getString("Contact") + "\" required></br><br>"
										+ "Date Of Event : <input type=\"date\" name=\"date\" value=\""
										+ eventResult.getString("DateOfEvent")
										+ "\" min=\"2018-12-01\" required></br><br>"
										+ "Time of Event : <input type=\"time\" name=\"time\" value=\""
										+ eventResult.getString("timeofevent") + "\" required></br><br>"
										+ "Email ID : <input type=\"text\" name=\"email\" value=\"" + useremail
										+ "\" required></br><br>"
										+ "Max Tickets : <input type=\"text\" name=\"maxtickets\" value=\""
										+ eventResult.getString("MaxTickets") + "\"></br><br>"
										+ "Description : <input type=\"text\" name=\"description\" value=\""
										+ eventResult.getString("Description") + "\" required></br><br>"
										+ "Action: <input list=\"options\" name=\"options\">"
										+ "<datalist id=\"options\">" + "<option value=\"Modify\">"
										+ "<option value=\"Delete\">" + "</datalist>" + "<br><br>"
										+ "<input type =\"hidden\" name=\"username\" value=" + useremail + ">"
										+ "<input type =\"hidden\" name=\"eventId\" value=" + eventId + ">"
										+ "<input type=\"submit\" value=\"Submit\">" + "</form>"
										+ "<form action=\"/logindisplay\" method=\"get\">" + "<br>"
										+ "<input type =\"hidden\" name=\"username\" value=" + useremail + ">"
										+ "<input type =\"hidden\" name=\"eventId\" value=" + eventId + ">"
										+ "<input type=\"submit\" value=\"Back\">" + "</form>"
										+ "<form action=\"/logout\" method=\"get\">" + "<br>"
										+ "<input type=\"submit\" value=\"Logout\">" + "</form></html>";

							}

						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				out.println(browserBody);
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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String action = request.getParameter("options");
		try {
			eventId = Integer.parseInt(request.getParameter("eventId"));
			if (action.equals("Delete")) {

				boolean delete = CommonServer.db.deletefromTickets(eventId);
				if (!delete) {
					delete = CommonServer.db.deletefromEvents(eventId);
				}

			} else if (action.equals("Modify")) {
				useremail = request.getParameter("username");
				String eventName = request.getParameter("eventname");
				String category = request.getParameter("eventcategory");
				String location = request.getParameter("eventlocation");
				String time = request.getParameter("time");
				String email = request.getParameter("email");
				String maxTickets = request.getParameter("maxtickets");
				String description = request.getParameter("description");
				String doe = request.getParameter("date");
				String contact = request.getParameter("contact");


				CommonServer.db.updateEventsTable(email, eventName, category, location, description, doe, maxTickets,
						contact, time, eventId);

				response.sendRedirect("/useraccount?username="+useremail);
			} else {

				response.sendRedirect("/modifyordelete?eventId=" + eventId+ "&username="+useremail);
			}

		} catch (

		SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
