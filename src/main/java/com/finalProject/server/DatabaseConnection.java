package com.finalProject.server;
/*
 * AUTHOR : VRAJESH BHIMAJINAI
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private String dbName = "classicmodels";
    private String userName = "username";
    private String userPass = "password";
    private String hostName = "programdebug.chrc4u8dca9s.us-east-2.rds.amazonaws.com";
    private String port = "3306";
    
    private String jdbcURL = "jdbc:mysql://"+hostName+":"+port+"/"+dbName+"?user="+userName+"&password="+userPass;
  
   private Connection con = null;
    
    public Connection getConnection()
    {
    	try
    	{
    		System.out.println("Loading Drivers....");
    		Class.forName("com.mysql.cj.jdbc.Driver");
    	
    		System.out.println("Makeing Connection with Mysql...");
    		con = DriverManager.getConnection(jdbcURL);
    		
    		return con;
    	
    	}catch(ClassNotFoundException  e)
    	{
    		System.out.println("Class Not Found Error ! : "+e.getMessage());
    		
    	}catch(SQLException e)
    	{
    		System.out.println("SQL Exception Occuared ! : "+e.getMessage());
    	}
    	
    	return con;
    }
}
/*
 * AUTHOR : VRAJESH BHIMAJIANI
 */