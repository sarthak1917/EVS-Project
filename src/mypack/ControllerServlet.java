package mypack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AddUser;
import model.LoginClass;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ControllerServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path=req.getParameter("path");
		PrintWriter out=resp.getWriter();
		resp.setContentType("application/json");
		
		Gson gson=new Gson();
		JsonParser parser=new JsonParser();
		JsonObject obj=(JsonObject)parser.parse(req.getReader());
		
		System.out.println(obj+ "\n"+path);
		
		if(path.equalsIgnoreCase("login")){
			System.out.println("here in login");

			LoginClass cls=new LoginClass();
			String check= cls.verify(obj.get("username").getAsString(),obj.get("password").getAsString());
			System.out.println("here "+check);
			JsonObject resData=new JsonObject();
			
			if(!check.equals("NOT_FOUND")){
				String str[]=check.split(":");
				resData.addProperty("code", 200);
				resData.addProperty("type",str[0]);
				resData.addProperty("status", str[1]);
			}else{
				resData.addProperty("code", 500);
				resData.addProperty("data","NOT_FOUND");
			}
			out.println(resData.toString());
			System.out.println(resData.toString());
			
		}else if(path.equalsIgnoreCase("saveUser")){
			System.out.println("here in save user");
			AddUser us=new AddUser();
			String response=us.add(obj);
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
		}
		
	}
	
	

}
