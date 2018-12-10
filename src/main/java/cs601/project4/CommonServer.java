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

		Server server = new Server(7070);

		// create a ServletHander to attach servlets
		ServletContextHandler servhandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

		// set the list of handlers for the server
		server.setHandler(servhandler);
		
		ServletHolder servletHolder1 = new ServletHolder(UserLogin.class);
		servletHolder1.setInitParameter("cacheControl","max-age=0,public"); 
		servhandler.addServlet(servletHolder1, "/login");
		
		ServletHolder servletHolder2 = new ServletHolder(UserRegistration.class);
		servletHolder2.setInitParameter("cacheControl","max-age=0,public"); 
		servhandler.addServlet(servletHolder2, "/userregistration");
		//servhandler.addServlet(UserRegistration.class, "/userregistration");
		
		ServletHolder servletHolder3 = new ServletHolder(HomePage.class);
		servletHolder3.setInitParameter("cacheControl","max-age=0,public"); 
		servhandler.addServlet(servletHolder3, "/homepage");
		//servhandler.addServlet(HomePage.class, "/homepage");
		
		ServletHolder servletHolder4 = new ServletHolder(NewEvent.class);
		servletHolder4.setInitParameter("cacheControl","max-age=0,public"); 
		servhandler.addServlet(servletHolder4, "/newevent");
		//servhandler.addServlet(NewEvent.class, "/newevent");
		
		ServletHolder servletHolder5 = new ServletHolder(LoginDisplay.class);
		servletHolder5.setInitParameter("cacheControl","max-age=0,public"); 
		servhandler.addServlet(servletHolder5, "/logindisplay");
		//servhandler.addServlet(LoginDisplay.class, "/logindisplay");
		
		ServletHolder servletHolder6 = new ServletHolder(BookEvent.class);
		servletHolder6.setInitParameter("cacheControl","max-age=0,public"); 
		servhandler.addServlet(servletHolder6, "/bookevent");
		//servhandler.addServlet(BookEvent.class, "/bookevent");
		
		ServletHolder servletHolder7 = new ServletHolder(ViewEvent.class);
		servletHolder7.setInitParameter("cacheControl","max-age=0,public"); 
		servhandler.addServlet(servletHolder7, "/viewevent");
		//servhandler.addServlet(ViewEvent.class, "/viewevent");
		
		ServletHolder servletHolder8 = new ServletHolder(ModifyOrDeleteEvent.class);
		servletHolder8.setInitParameter("cacheControl","max-age=0,public"); 
		servhandler.addServlet(servletHolder8, "/modifyordelete");
		//servhandler.addServlet(ModifyOrDeleteEvent.class, "/modifyordelete");
		
		ServletHolder servletHolder9 = new ServletHolder(Logout.class);
		servletHolder9.setInitParameter("cacheControl","max-age=0,public"); 
		servhandler.addServlet(servletHolder9, "/logout");
		//servhandler.addServlet(Logout.class, "/logout");
		
		ServletHolder servletHolder10 = new ServletHolder(TicketTransfer.class);
		servletHolder10.setInitParameter("cacheControl","max-age=0,public"); 
		servhandler.addServlet(servletHolder10, "/transfer");
		//servhandler.addServlet(TicketTransfer.class, "/transfer");
		
		ServletHolder servletHolder11 = new ServletHolder(UserAccount.class);
		servletHolder11.setInitParameter("cacheControl","max-age=0,public"); 
		servhandler.addServlet(servletHolder11, "/useraccount");
		//servhandler.addServlet(UserAccount.class, "/useraccount");
		
		 db = new DataBase();
		 snh = new SaltingandHashing();
		
		System.out.println("db connection successfull");
		// start the server
		server.start();
		server.join();
	}

}
