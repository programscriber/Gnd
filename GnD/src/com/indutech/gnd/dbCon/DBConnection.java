package com.indutech.gnd.dbCon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	
	 private static Properties properties = new Properties();
	 private InputStream inputStream =getClass().getResourceAsStream("/camel-config.properties");
	 private static DBConnection dbCon;  
	 private static Connection con=null; 
     private DBConnection() {  }  
     public static DBConnection getInstance() {    
	     if (dbCon==null)  
	     {  
	           dbCon=new  DBConnection();  
	     }  
	     return dbCon;  
     }  
            
	  public Connection getConnection() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException  
	  {   
		  if(con == null) {
		  properties.load(inputStream);  
	      Class.forName(properties.getProperty("driverClassName"));  
//	      con= DriverManager.getConnection(properties.getProperty("connectionURL"));
	      con= DriverManager.getConnection(properties.getProperty("connectionURL"), properties.getProperty("username"), properties.getProperty("password"));
	      
		  }
	      return con;  
	        
	  }  
}
