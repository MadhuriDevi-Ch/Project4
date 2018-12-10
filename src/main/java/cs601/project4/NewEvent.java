package main.java.cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * This class is available for any logged in user to create an event
 */
public class NewEvent extends HttpServlet {
	private String browserBody;
	private String eventName;
	private String category;
	private String location;
	private String time;
	private String doe;
	private String email;
	private String maxTickets;
	private String description;
	private boolean status;
	private String contact;
	
	public NewEvent() {
		// TODO Auto-generated constructor stub
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		email = request.getParameter("username");
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
				+ "Email ID : <input type=\"text\" name=\"email\" value=\""+email+"\" required></br><br>"
				+ "Max Tickets : <input type=\"text\" name=\"maxtickets\" value=\"\"></br><br>"
				+ "Description : <input type=\"text\" name=\"description\" value=\"\" required></br><br>"
				+ "<input type=\"submit\" value=\"Register\">" + "</form>" 
				+ "<form action=\"/logindisplay\" method=\"get\">" + "<br>"
				+ "<input type =\"hidden\" name=\"username\" value=" + email + ">"
				+ "<input type=\"submit\" value=\"Back\">" + "</form>"
				+"<form action=\"/logout\" method=\"get\">" + "<br>"
				+ "<input type=\"submit\" value=\"Logout\">" + "</form></html>";
		

		out.println(browserBody);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		eventName = request.getParameter("eventname");
		category = request.getParameter("eventcategory");
		location = request.getParameter("eventlocation");
		time = request.getParameter("time");
		email = request.getParameter("email");
		maxTickets = request.getParameter("maxtickets");
		description = request.getParameter("description");
		doe = request.getParameter("date");
		contact = request.getParameter("contact");
		
//		String eventTiming = doe.concat(" "+time+"");
		try {
			status = CommonServer.db.updateEventTable(email, eventName, category, location, description, doe, maxTickets, contact);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!status) {
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.println("<html><title>newevent</title><body>The Event is created!<br/>Thanks for visiting</body>");
        String tempbody =  "<form action=\"/logindisplay\" method=\"get\">" + "<br>"
				+ "<input type =\"hidden\" name=\"username\" value=" + email + ">"
				+ "<input type=\"submit\" value=\"Back\">" + "</form>"
				+"<form action=\"/logout\" method=\"get\">" + "<br>"
				+ "<input type=\"submit\" value=\"Logout\">" + "</form>";
		out.print(tempbody);
		out.println("</html>");
		} else {
			response.setContentType("text/html");
	        response.setStatus(HttpServletResponse.SC_ACCEPTED);
	        PrintWriter out = response.getWriter();
	        out.println("<html><title>newevent</title><body>The Event is not created. Please try again later.<br/>Thanks for visiting</body>");
	        String tempbody =  "<form action=\"/logindisplay\" method=\"get\">" + "<br>"
					+ "<input type =\"hidden\" name=\"username\" value=" + email + ">"
					+ "<input type=\"submit\" value=\"Back\">" + "</form>"
					+"<form action=\"/logout\" method=\"get\">" + "<br>"
					+ "<input type=\"submit\" value=\"Logout\">" + "</form>";
			out.print(tempbody);
			out.println("</html>");
		}
		
		
	}
}
