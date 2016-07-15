package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import db.DBCon;

public class VoterCardList {
	
	static Connection con;
	int p=0;
 	static {
		con=DBCon.getConnection();
		System.out.println(con);
	}
 	
 	public String add(JsonObject data){
 		String name=data.get("name").getAsString();
 		String fname=data.get("fname").getAsString();
 		String proof=data.get("proof").getAsString();
 		String dob=data.get("dob").getAsString();
 		String gender=data.get("gender").getAsString();
 		String address=data.get("address").getAsString();
 		String proofNo=data.get("proofNo").getAsString();
 		String status=data.get("status").getAsString();
 		String email=data.get("email").getAsString();
 		
 		try {
			PreparedStatement ps=con.prepareStatement("insert into votercardlist values(?,?,?,?,?,?,?,?,?)");
			ps.setString(1,name);
			ps.setString(2,fname);
			ps.setString(3,proof);
			ps.setString(4,dob);
			ps.setString(5,gender);
			ps.setString(6,address);
			ps.setString(7,proofNo);
			ps.setString(8,status);
			ps.setString(9,email);
			
			int x=ps.executeUpdate();
			if(x==1){
				System.out.println("success");
				if(updateStatus(status,email).equals("s")){
					return "success";
				}else{
					return "updation failed";
				}	
				
			}else{
				return "insertion failed";
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return "insertion failed";
		}

 		
 	}
 	
 	public String updateStatus(String status,String email){
 		
 		try {
			PreparedStatement ps=con.prepareStatement("update users set status=? where email=?");
			ps.setString(1, status);
			ps.setString(2, email);
			
			int x=ps.executeUpdate();
			if(x==1){
				return "s";
			}else{
				return "f";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "f";
		}
 		
 	}
 	
 	public JsonArray getVotersList(){
 		JsonArray jArr=new JsonArray();
 		try {
 			
			Statement st=con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY	);
			ResultSet rs=st.executeQuery("select * from votercardlist");
			
			while(rs.next()){
				
				if(rs.getString("status").equals("waitingforapproval")){
					JsonObject j1=new JsonObject();

					j1.addProperty("name", rs.getString("name"));
					j1.addProperty("fname", rs.getString("fname"));
					j1.addProperty("proof", rs.getString("proof"));
					j1.addProperty("proofNo", rs.getString("proofNo"));
					j1.addProperty("dob", rs.getString("dob"));
					j1.addProperty("gender", rs.getString("gender"));
					j1.addProperty("address", rs.getString("address"));
					j1.addProperty("email", rs.getString("email"));
		
					System.out.println("in for");
					
					jArr.add(j1);
					
				}
				
			}
			//System.out.println(jArr);
			return jArr;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return jArr; 
		}
 		
 	}
 	
 	public String updateVoterCardDetails(JsonObject obj){
 		String email=obj.get("email").getAsString();
 		String status=obj.get("status").getAsString();
 		System.out.println(email+" ... "+status);
 		String val=updateStatus(status, email);
 		try {
			PreparedStatement ps=con.prepareStatement("update votercardlist set status=? where email=?");
			ps.setString(1, status);
			ps.setString(2, email);
			
			int x=ps.executeUpdate();
			if(x==1&&val.equals("s")){
				return "success";
			}else{
				return "failure";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failure";
		}
 		
 	}


}
