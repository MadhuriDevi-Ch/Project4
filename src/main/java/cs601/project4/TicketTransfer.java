package main.java.cs601.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * this class is called when the user wishes to transfer tickets to another valid user
 */
public class TicketTransfer extends HttpServlet {
	private String browserBody;
	private String userEmail;
	private int eventId;
	private int ticketcount;
	private String eventname;
	ResultSet userResult;
	private int ticketCount;
	private boolean transferStatus;

	public TicketTransfer() {
		// TODO Auto-generated constructor stub
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		eventId = Integer.parseInt(request.getParameter("eventId"));
		userEmail = request.getParameter("username");
		eventname = request.getParameter("eventname");
		ticketcount = Integer.parseInt(request.getParameter("ticketcount"));
		
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		

		PrintWriter out = response.getWriter();

		browserBody = "<html><title>Transfer Tickets</title>" + "<body>Please enter the following details and Transfer</body>"
				+ "<form action=\"/transfer\" method=\"post\">" + "<br>"
				+ " Event Name: "+ eventname + "<br>"
				+ "No of Tickets to be transferred : <input type=\"number\" name=\"count\" value=\"\" min=\"1\" max=\""+ ticketcount +"\" required><br><br>"
				+ "Transfer to user: <input type=\"email\" name=\"touseremail\" value=\"\" required></br><br>"
				+ "<input type=\"hidden\" name=\"eventId\" value=" + eventId + ">"
				+ "<input type =\"hidden\" name=\"username\" value=" + userEmail + ">"
				+ "<input type=\"submit\" value=\"Transfer\">" + "</form>" 
				+ "<form action=\"/logindisplay\" method=\"get\">" + "<br>"
				+ "<input type =\"hidden\" name=\"username\" value=" + userEmail + ">"
				+ "<input type=\"submit\" value=\"Cancel\">" + "</form>"
				+"<form action=\"/logout\" method=\"get\">" + "<br>"
				+ "<input type=\"submit\" value=\"Logout\">" + "</form>"
				+ "</html>";

		out.println(browserBody);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String toUsername = request.getParameter("touseremail");
		userEmail = request.getParameter("username");
		eventId = Integer.parseInt(request.getParameter("eventId"));
		ticketCount = Integer.parseInt(request.getParameter("count"));
		PrintWriter out = response.getWriter();
		int buyerId = 0;
		int sellerId = 0;
		try {
			userResult = CommonServer.db.selectUserTable();
			while(userResult.next()) {
				if(userEmail.equals(userResult.getString("Email"))){
					sellerId = userResult.getInt("UserID");
				}
				if(toUsername.equals(userResult.getString("Email"))){
					buyerId = userResult.getInt("UserID");
				}
			}
			System.out.println(buyerId);
			if(buyerId == 0) {
				

				browserBody = "<html><title>Transfer Tickets</title>" + "<body>Illegal transfer</body>"
						+ "<form action=\"/useraccount\" method=\"get\">" + "<br>"
						+ "<input type =\"hidden\" name=\"username\" value=" + userEmail + ">"
						+ "<input type=\"submit\" value=\"Account Details\">" + "</form></html>";

				out.println(browserBody);
			}else {
				transferStatus = CommonServer.db.updateTransactionTable(sellerId, buyerId, eventId, ticketCount);
				System.out.println(transferStatus);
				if(!transferStatus) {
					CommonServer.db.changeTicketCount(sellerId,eventId, ticketCount, buyerId);
					browserBody = "<html><title>Transfer Tickets</title>" + "<body>Transfer Success</body>"
							+ "<form action=\"/useraccount\" method=\"get\">" + "<br>"
							+ "<input type =\"hidden\" name=\"username\" value=" + userEmail + ">"
							+ "<input type=\"submit\" value=\"Account Details\">" + "</form></html>";

					out.println(browserBody);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
