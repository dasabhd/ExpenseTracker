package com.expensetracker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.ConnectionEvent;

public class ExpenseTrackerDAO {
	
	private Connection conn;
	
	public ExpenseTrackerDAO(String className, String dbURL, String userID, String pass) throws ClassNotFoundException {
		
		Class.forName(className);
		
		try {
			
			this.conn = DriverManager.getConnection(dbURL, userID, pass);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void submitForm(String expensedate, String name, int amount) throws SQLException {
		
		PreparedStatement stmt = this.conn.prepareStatement("insert into expensetracker(expensedate, name, amount"
				+ ") values (?,?,?)");
		
		stmt.setString(1, expensedate);
		stmt.setString(2, name);
		stmt.setInt(3, amount);
		
		int count = stmt.executeUpdate();
		
		
	}
	
	public ResultSet showData(String fromdate, String todate) throws SQLException {
		
		PreparedStatement stmt = this.conn.prepareStatement("Select * from expensetracker where expensedate>= ? and expensedate<= ?");
		stmt.setString(1, fromdate);
		stmt.setString(2, todate);
		
		ResultSet result = stmt.executeQuery();
		
		return result;
		
	}

	public Connection getConn() {
		return conn;
	}

}
