package main.java.cs601.project4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class DataBase {
	Connection con;
	ResultSet result;

	public DataBase() throws SQLException {
		// TODO Auto-generated constructor stub
		String username = "user11";
		String password = "user11";
		String db = "user11";

		try {
			// load driver
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.err.println("Can't find driver");
			System.exit(1);
		}

		// format "jdbc:mysql://[hostname][:port]/[dbname]"
		// note: if connecting through an ssh tunnel make sure to use 127.0.0.1 and
		// also to that the ports are set up correctly
//		String urlString = "jdbc:mysql://sql.cs.usfca.edu/"+db;
		String urlString = "jdbc:mysql://127.0.0.1/" + db;
		// Must set time zone explicitly in newer versions of mySQL.
		String timeZoneSettings = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

		con = DriverManager.getConnection(urlString + timeZoneSettings, username, password);
	}

	/*
	 * This method updates the Users Table when a new User is created
	 */
	public void updateUserTable(String fname, String lname, String city, String contact, String gender, String email,
			byte[] pwd, String dob, byte[] salt) throws SQLException {
		PreparedStatement updateStmt = con.prepareStatement(
				"INSERT INTO Users (Firstname, LastName, Email, City, Contact, DateOfBirth, Gender, Password, Salt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

		updateStmt.setString(1, fname);
		updateStmt.setString(2, lname);
		updateStmt.setString(3, email);
		updateStmt.setString(4, city);
		updateStmt.setString(5, contact);
		updateStmt.setString(6, dob);
		updateStmt.setString(7, gender);
		updateStmt.setBytes(8, pwd);
		updateStmt.setBytes(9, salt);
		updateStmt.execute();
	}

	/*
	 * This method updates the Events Table when a new event is created by the user
	 */
	public boolean updateEventTable(String email, String eventName, String category, String location, String desc,
			String date, String max, String contact, String time) throws SQLException {
		int hostId = 0;

		// execute a query, which returns a ResultSet object
		result = selectUserTable();

		while (result.next()) {
			// for each result, get the value of the columns name and id
			String dbEmail = result.getString("Email");
			if (dbEmail.equals(email)) {
				hostId = result.getInt("UserID");
			}
		}

		int maxticket = Integer.parseInt(max);

		PreparedStatement updateStmt = con.prepareStatement(
				"INSERT INTO Events (EventName, Category, Location, Description, HostID, DateOfEvent, MaxTickets, Contact, HostEmail, timeofevent) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		updateStmt.setString(1, eventName);
		updateStmt.setString(2, category);
		updateStmt.setString(3, location);
		updateStmt.setString(4, desc);
		updateStmt.setInt(5, hostId);
		updateStmt.setString(6, date);
		updateStmt.setInt(7, maxticket);
		updateStmt.setString(8, contact);
		updateStmt.setString(9, email);
		updateStmt.setString(10, time);
		return updateStmt.execute();

	}

	/*
	 * This methods returns all the data in Users Table
	 */
	public ResultSet selectUserTable() throws SQLException {
		String selectStmt = "SELECT * FROM Users";

		// create a statement object
		PreparedStatement stmt = con.prepareStatement(selectStmt);

		return stmt.executeQuery();

	}

	/*
	 * This methods returns all the data in Users Table
	 */
	public ResultSet selectEventTable() throws SQLException {
		String selectStmt = "SELECT * FROM Events ORDER BY DateOfEvent";

		// create a statement object
		PreparedStatement stmt = con.prepareStatement(selectStmt);

		return stmt.executeQuery();

	}

	public byte[] getDbPassword(String email) throws SQLException {
		result = selectUserTable();
		byte[] dbPwd = null;
		while (result.next()) {
			// for each result, get the value of the columns name and id
			String dbEmail = result.getString("Email");
			if (dbEmail.equals(email)) {
				dbPwd = result.getBytes("Password");
			}
		}
		return dbPwd;

	}

	public byte[] getSalt(String email) throws SQLException {
		result = selectUserTable();
		byte[] dbSalt = null;
		while (result.next()) {
			// for each result, get the value of the columns name and id
			String dbEmail = result.getString("Email");
			if (dbEmail.equals(email)) {
				dbSalt = result.getBytes("Salt");
			}
		}
		return dbSalt;

	}

	public int updateTransactionTable(int eventId, int count, String userEmail) throws SQLException {

		int sellerId = 0;
		int buyerId = 0;
		// execute a query, which returns a ResultSet object
		result = selectEventTable();

		while (result.next()) {
			// for each result, get the value of the columns name and id
			int dbevent = result.getInt("EventID");
			if (dbevent == eventId) {
				sellerId = result.getInt("HostID");
			}
		}

		result = selectUserTable();

		while (result.next()) {
			// for each result, get the value of the columns name and id
			String dbEmail = result.getString("Email");
			if (dbEmail.equals(userEmail)) {
				buyerId = result.getInt("UserID");
			}
		}

		PreparedStatement updateStmt = con.prepareStatement(
				"INSERT INTO Transactions (EventId, SellerId, BuyerId, PurchasedTickets) VALUES (?, ?, ?, ?)");

		updateStmt.setInt(1, eventId);
		updateStmt.setInt(2, sellerId);
		updateStmt.setInt(3, buyerId);
		updateStmt.setInt(4, count);
		updateStmt.execute();
		return buyerId;

	}

	public boolean updateTransactionTable(int sellerId, int buyerId, int eventId, int count) throws SQLException {

		PreparedStatement updateStmt = con.prepareStatement(
				"INSERT INTO Transactions (EventId, SellerId, BuyerId, PurchasedTickets) VALUES (?, ?, ?, ?)");

		updateStmt.setInt(1, eventId);
		updateStmt.setInt(2, sellerId);
		updateStmt.setInt(3, buyerId);
		updateStmt.setInt(4, count);
		return updateStmt.execute();

	}

	public void updateTicketSummaryTable(int eventId, int count, int userId) throws SQLException {
		PreparedStatement updateStmt = con
				.prepareStatement("INSERT INTO Ticketsummary (UserId, Ticketcount, EventId) VALUES (?, ?, ?)");

		updateStmt.setInt(1, userId);
		updateStmt.setInt(2, count);
		updateStmt.setInt(3, eventId);
		updateStmt.execute();
	}

	public void changeTicketCount(int sellerId, int eventId, int ticketcount, int buyerId) throws SQLException {
		PreparedStatement updateStmt1 = con.prepareStatement("Update Ticketsummary set Ticketcount = Ticketcount - "
				+ ticketcount + " where UserId = " + sellerId + " and EventId = " + eventId);
		PreparedStatement updateStmt2 = con.prepareStatement(
				"INSERT INTO Ticketsummary (UserId, Ticketcount, EventId) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE Ticketcount=Ticketcount + "
						+ ticketcount);
		updateStmt2.setInt(1, buyerId);
		updateStmt2.setInt(2, ticketcount);
		updateStmt2.setInt(3, eventId);
		updateStmt1.execute();
		updateStmt2.execute();

	}

	public ResultSet selectTicketTable() throws SQLException {
		String selectStmt = "SELECT * FROM Ticketsummary inner join Events on Ticketsummary.EventId = Events.EventID ORDER BY UserId";

		// create a statement object
		PreparedStatement stmt = con.prepareStatement(selectStmt);

		return stmt.executeQuery();

	}
	
	public boolean deletefromTickets( int eventId) throws SQLException {
		
		PreparedStatement deleteStmt = con.prepareStatement(
				"delete from ticketsummary where EventId = \"" + eventId +"\"");
		return deleteStmt.execute();
	}
	public boolean deletefromEvents( int eventId) throws SQLException {
		
		PreparedStatement deleteStmt = con.prepareStatement(
				"delete from Events where EventID = \"" + eventId +"\"");
		return deleteStmt.execute();
	}
	public void updateEventsTable(String email, String eventName, String category, String location, String desc,
		String date, String max, String contact, String time, int eventID) throws SQLException {
	
	int maxticket = Integer.parseInt(max);

	PreparedStatement updateStmt = con.prepareStatement(
			"update Events Set EventName = ?, Category = ?, Location = ?, Description = ?, DateOfEvent = ?, MaxTickets = ?, Contact = ?, HostEmail = ?, TimeofEVent =  ? where eventId = ?");

	updateStmt.setString(1, eventName);
	updateStmt.setString(2, category);
	updateStmt.setString(3, location);
	updateStmt.setString(4, desc);
	updateStmt.setString(5, date);
	updateStmt.setInt(6, maxticket);
	updateStmt.setString(7, contact);
	updateStmt.setString(8, email);
	updateStmt.setString(9, time);
	updateStmt.setInt(10, eventID);

	updateStmt.execute();
		
	}
}
