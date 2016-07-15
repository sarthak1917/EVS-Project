package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import db.DBCon;

public class AddUser {
	
	static Connection con;
 	static {
		con=DBCon.getConnection();
		System.out.println(con);
	}
 	
 	public String add(JsonObject data){
 		String name=data.get("name").getAsString();
 		String email=data.get("email").getAsString();
 		String dob=data.get("dob").getAsString();
 		String type=data.get("type").getAsString();
 		String password=data.get("password").getAsString();
 		String status=data.get("status").getAsString();
 		try {
			PreparedStatement ps=con.prepareStatement("insert into users values(?,?,?,?,?,?)");
			ps.setString(1,name);
			ps.setString(2,password);
			ps.setString(3,email);
			ps.setString(4,dob);
			ps.setString(5,type);
			ps.setString(6,status);
			
			int x=ps.executeUpdate();
			if(x==1){
				System.out.println("success");
				return "success";
			}else{
				return "insertion failed";
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "insertion failed";
		}

 		
 	}

}
