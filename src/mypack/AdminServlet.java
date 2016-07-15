package mypack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AddCandidate;
import model.VoterCardList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AdminServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path=req.getParameter("path");
		PrintWriter out=resp.getWriter();
		
		
		if(path.equals("getApplyUsers")){
			System.out.println(path);
			VoterCardList ls=new VoterCardList();
			JsonArray jArr=ls.getVotersList();
			if(jArr!=null){
				System.out.println(jArr.toString());
				out.println(jArr.toString());
			}else{
				JsonObject obj=new JsonObject();
				obj.addProperty("code", "500");
				obj.addProperty("data", "error in json array");
				System.out.println(obj.toString());
				out.println(obj.toString());
				
			}
			
		}
		
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String path=req.getParameter("path");
		PrintWriter out=resp.getWriter();
		
		Gson gson=new Gson();
		JsonParser parser=new JsonParser();
		JsonObject obj=(JsonObject)parser.parse(req.getReader());

		
		if(path.equals("approveCard")){
			System.out.println(path);
			VoterCardList ls=new VoterCardList();
			String data=ls.updateVoterCardDetails(obj);
			JsonObject res=new JsonObject();
			if(data.equals("success")){
				res.addProperty("code",200);
				res.addProperty("data",data);
			}else{
				res.addProperty("code",500);
				res.addProperty("data",data);
			}
			System.out.println(res.toString());
			out.println(res.toString());
            
		}else if(path.equals("addCandidate")){
			System.out.println(path);
			AddCandidate can=new AddCandidate();
			String verify=can.add(obj);
			JsonObject res=new JsonObject();
			if(verify.equals("success")){
				res.addProperty("code", 200);
				res.addProperty("data", "success");
			}else{
				res.addProperty("code", 500);
				res.addProperty("data", "failure");
			}
			System.out.println(out.toString());
			out.println(res.toString());
		}
		
		
		
	}
	
	
	
	

}
