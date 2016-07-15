package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import db.DBCon;

public class AddCandidate {
	
	static Connection con;
 	static {
		con=DBCon.getConnection();
		System.out.println(con);
	}
 	
 	public String add(JsonObject data){
 		String name=data.get("name").getAsString();
 		String age=data.get("age").getAsString();
 		String pName=data.get("pName").getAsString();
 		String pSymbol=data.get("pSymbol").getAsString();
 		String nVotes="0";
 		
 		try {
			PreparedStatement ps=con.prepareStatement("insert into candidatelist values(?,?,?,?,?)");
			ps.setString(1, name);
			ps.setString(2, age);
			ps.setString(3, pName);
			ps.setString(4, pSymbol);
			ps.setString(5, nVotes);
			
			int x=ps.executeUpdate();
			if(x==1){
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
 	
 	public JsonArray getCandidates(){
 		JsonArray jArr=new JsonArray();
 		try {
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from candidatelist");
			
			
			while(rs.next()){
				JsonObject jt1=new JsonObject();
				jt1.addProperty("name",rs.getString("name"));
				jt1.addProperty("age", rs.getString("age"));
				jt1.addProperty("pName",rs.getString("pName"));
				jt1.addProperty("pSymbol",rs.getString("pSymbol"));
				jt1.addProperty("nVotes", rs.getString("nVotes"));
				
				jArr.add(jt1);
				
			}
			
			return jArr;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return jArr;
		}
 		
 		
 	}
 	
 	public String vote(JsonObject data){
 		try {
 			String name=data.get("name").getAsString();
 			System.out.println(name);
 			Statement st=con.createStatement();
 			ResultSet rs=st.executeQuery("select * from candidatelist");
 			int no=0;
 			while(rs.next()){
 				if(rs.getString("name").equals(name)){
 					no=Integer.parseInt(rs.getString("nVotes"));
 					break;
 				}
 			}
 			System.out.println(no);
 			no++;
 			
			PreparedStatement ps=con.prepareStatement("update candidatelist set nVotes=? where name=?");
			ps.setString(1,no+"");
			ps.setString(2, name);
			int x=ps.executeUpdate();
			
			if(x==1){
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
