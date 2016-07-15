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

public class UsersServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path=req.getParameter("path");
		
		
		PrintWriter out=resp.getWriter();
		resp.setContentType("application/json");
		
		Gson gson=new Gson();
		JsonParser parser=new JsonParser();
		JsonObject obj=(JsonObject)parser.parse(req.getReader());
		
		System.out.println(path+" ...  "+obj); 
		
		if(path.equals("voterCard")){
			
			VoterCardList ls=new VoterCardList();
			String response=ls.add(obj);
			JsonObject resData=new JsonObject();
			
			if(response.equals("success")){
				resData.addProperty("code", 200);
				resData.addProperty("data", response);
			}else{
				resData.addProperty("code", 500);
				resData.addProperty("data", response);
			}
			
			out.println(resData.toString());
			System.out.println(resData.toString());
			
		}else if(path.equals("vote")){
			System.out.println(path);
			AddCandidate adc=new AddCandidate();
			String verify=adc.vote(obj);
			JsonObject res=new JsonObject();
			
			if(verify.equals("success")){
				res.addProperty("code", 200);
				res.addProperty("data", verify);
				System.out.println(res.toString());
				out.println(res.toString());
			}else{
				res.addProperty("code", 500);
				res.addProperty("data", verify);
				System.out.println(res.toString());
				out.println(res.toString());
			}
		}
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path=req.getParameter("path");
		PrintWriter out=resp.getWriter();
		
		if(path.equals("getCandidates")){
			System.out.println(path);
			AddCandidate adc=new AddCandidate();
			JsonArray jArr=adc.getCandidates();
			
			if(jArr!=null){
				System.out.println(jArr.toString());
				out.println(jArr.toString());
			}else{
				JsonObject res=new JsonObject();
				res.addProperty("code", 500);
				res.addProperty("data", "error occured while fetching candidates");
				System.out.println(res.toString());
				out.println(res.toString());
			}
		}
	}
	
	

}
