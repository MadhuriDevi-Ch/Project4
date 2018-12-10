package main.java.cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		useremail = request.getParameter("username");
		eventId = Integer.parseInt(request.getParameter("eventId"));
		hostId = Integer.parseInt(request.getParameter("hostId"));
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		PrintWriter out = response.getWriter();
		
		try {
			eventResult = CommonServer.db.selectEventTable();
			while(eventResult.next()) {
				int dbHost = eventResult.getInt("HostID");
				int dbevent = eventResult.getInt("EventID");
				if(eventId == dbevent) {
				if (hostId == dbHost) {
					browserBody = "<html><title>updateevent</title>"
							+ "<body>Please enter the following details and Submit</body>"
							+ "<form action=\"/modifyordelete\" method=\"post\">" + "<br>"
							+ "EventName : <input type=\"text\" name=\"eventname\" value=\"\""+eventId+"\" required></br><br>"
							+ "Category : <input type=\"text\" name=\"eventcategory\" value=\"\" required></br><br>"
							+ "City : <input type=\"text\" name=\"eventlocation\" value=\"\" required></br><br>"
							+ "Contact Number : <input type=\"tel\" name=\"contact\" pattern=\"[0-9]{3}-[0-9]{3}-[0-9]{4}\" value=\"\" required></br><br>"
							+ "Date Of Event : <input type=\"date\" name=\"date\" value=\"\" min=\"2018-12-01\" required></br><br>"
							+ "Time of Event : <input type=\"time\" name=\"time\" value=\"\" required></br><br>"
							+ "Email ID : <input type=\"text\" name=\"email\" value=\""+useremail+"\" required></br><br>"
							+ "Max Tickets : <input type=\"text\" name=\"maxtickets\" value=\"\"></br><br>"
							+ "Description : <input type=\"text\" name=\"description\" value=\"\" required></br><br>"
							+ "<input list=\"options\" name=\"options\">"
							+ "<datalist id=\"options\">"
							+ "<option value=\"Modify\">"
							+ "<option value=\"Delete\">" + "</datalist>"+ "<br><br>"
							+ "<input type=\"submit\" value=\"Submit\">" + "</form>" 
							+ "<form action=\"/logindisplay\" method=\"get\">" + "<br>"
							+ "<input type =\"hidden\" name=\"username\" value=" + useremail + ">"
							+ "<input type=\"submit\" value=\"Back\">" + "</form>"
							+"<form action=\"/logout\" method=\"get\">" + "<br>"
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
	}
}
