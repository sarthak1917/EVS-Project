package db;

import java.sql.*;

public class DBCon {

	private static Connection con;
	static{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sar","s1917");		
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	
	public static Connection getConnection(){
		return con;
	}
	
}
