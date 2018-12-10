package main.java.cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModifyOrDeleteEvent extends HttpServlet {
	private String browserBody;
	private String useremail;
	private int eventId;
	private int hostId;
	

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
		
		browserBody = "<html><title>newevent</title>"
				+ "<body>Please enter the following details and Submit</body>"
				+ "<form action=\"/newevent\" method=\"post\">" + "<br>"
				+ "EventName : <input type=\"text\" name=\"eventname\" value=\"\" required></br><br>"
				+ "Category : <input type=\"text\" name=\"eventcategory\" value=\"\" required></br><br>"
				+ "City : <input type=\"text\" name=\"eventlocation\" value=\"\" required></br><br>"
				+ "Contact Number : <input type=\"tel\" name=\"contact\" pattern=\"[0-9]{3}-[0-9]{3}-[0-9]{4}\" value=\"\" required></br><br>"
				+ "Date Of Event : <input type=\"date\" name=\"date\" value=\"\" min=\"2018-12-01\" required></br><br>"
				+ "Time of Event : <input type=\"time\" name=\"time\" value=\"\" required></br><br>"
				+ "Email ID : <input type=\"text\" name=\"email\" value=\""+useremail+"\" required></br><br>"
				+ "Max Tickets : <input type=\"text\" name=\"maxtickets\" value=\"\"></br><br>"
				+ "Description : <input type=\"text\" name=\"description\" value=\"\" required></br><br>"
				+ "<input type=\"submit\" value=\"Register\">" + "</form>" 
				+ "<form action=\"/logindisplay\" method=\"get\">" + "<br>"
				+ "<input type =\"hidden\" name=\"username\" value=" + useremail + ">"
				+ "<input type=\"submit\" value=\"Back\">" + "</form>"
				+"<form action=\"/logout\" method=\"get\">" + "<br>"
				+ "<input type=\"submit\" value=\"Logout\">" + "</form></html>";
		

		out.println(browserBody);
	}
}
