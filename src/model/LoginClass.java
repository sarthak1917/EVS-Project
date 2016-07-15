package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import db.DBCon;

public class LoginClass {

	static Connection con;
	 ResultSet rs;
 	static {
		con=DBCon.getConnection();
		
	}
	

	public String verify(String username,String password){
		getRecords();
		try {
			while(rs.next()){
				if(rs.getString("email").equals(username)&&rs.getString("password").equals(password)){
					return rs.getString("type")+":"+rs.getString("status");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOT_FOUND";
	}
	
	private void getRecords(){
		try {
			Statement st=con.createStatement();
			rs=st.executeQuery("select * from users");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
