package com.expensetracker.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.expensetracker.database.ExpenseTrackerDAO;

/**
 * Servlet implementation class showServlet
 */
@WebServlet("/showServlet")
public class showServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ExpenseTrackerDAO database; 
	private String fromDate;
	private String toDate;
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		ServletContext context = config.getServletContext();
		String className = context.getInitParameter("className");
		String dbURL = context.getInitParameter("dbURL");
		String userID = context.getInitParameter("userID");
		String pass = context.getInitParameter("pass");
		
		try {
			database = new ExpenseTrackerDAO(className, dbURL, userID, pass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		
		try {
			database.getConn().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		this.fromDate = request.getParameter("fromDate");
		this.toDate = request.getParameter("toDate");
		
		System.out.println(this.fromDate+" "+this.toDate);
		try {
			ResultSet result = database.showData(this.fromDate, this.toDate);
			PrintWriter out = response.getWriter();
			
			out.println("<html>");
			out.println("<head>\r\n"
					+ "    <meta charset=\"UTF-8\">\r\n"
					+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "    <title>Expense Details</title>\r\n"
					+ "</head>");
			out.println("<body>\r\n"
					+ "    <h2>Expense Details</h2>\r\n"
					+ "    <table>\r\n"
					+ "        <tr>\r\n"
					+ "            <td>Expense Date</td>\r\n"
					+ "            <td>Transaction Name</td>\r\n"
					+ "            <td>Transaction Amount</td>\r\n"
					+ "        </tr>");
			while(result.next()) {
				
				out.println("<tr>");
				out.println("<td>"+result.getString(1)+"</td>");
				out.println("<td>"+result.getString(2)+"</td>");
				out.println("<td>"+result.getString(3)+"</td>");
				out.println("</tr>");
				
			}
			out.println("</table>\r\n"
					+ "</body>");
			out.println("</html>");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

}
