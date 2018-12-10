package main.java.cs601.project4;

import org.eclipse.jetty.server.Server;
import javax.servlet.Servlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class CommonServer {
	static DataBase db;
	static SaltingandHashing snh;
	
	public CommonServer() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {

		Server server = new Server(8080);

		// create a ServletHander to attach servlets
		ServletContextHandler servhandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

		// set the list of handlers for the server
		server.setHandler(servhandler);
		
		ServletHolder servletHolder = new ServletHolder(UserLogin.class);
		servletHolder.setInitParameter("cacheControl","max-age=0,public"); 
		servhandler.addServlet(servletHolder, "/login");
		
		
		servhandler.addServlet(UserRegistration.class, "/userregistration");
		servhandler.addServlet(HomePage.class, "/homepage");
		servhandler.addServlet(NewEvent.class, "/newevent");
		servhandler.addServlet(LoginDisplay.class, "/logindisplay");
		servhandler.addServlet(BookEvent.class, "/bookevent");
		servhandler.addServlet(ViewEvent.class, "/viewevent");
		servhandler.addServlet(ModifyOrDeleteEvent.class, "/modifyordelete");
		servhandler.addServlet(Logout.class, "/logout");
		servhandler.addServlet(TicketTransfer.class, "/transfer");
		servhandler.addServlet(UserAccount.class, "/useraccount");
		 db = new DataBase();
		 snh = new SaltingandHashing();
		
		System.out.println("db connection successfull");
		// start the server
		server.start();
		server.join();
	}

}
