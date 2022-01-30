package com.expensetracker.servlets;

import java.io.IOException;
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
 * Servlet implementation class SubmitExpense
 */
@WebServlet("/SubmitExpense")
public class SubmitExpense extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ExpenseTrackerDAO database;   
    
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String date = request.getParameter("transactionDate");
		String name = request.getParameter("transactionName");
		int amount = Integer.parseInt(request.getParameter("transactionAmount"));
		
		
		try {
			database.submitForm(date, name, amount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
