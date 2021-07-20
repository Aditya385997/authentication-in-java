package com.example.demoauthserver.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demoauthserver.helper.JwtUtil;



@Component
public class TwilioService{
	private String  otp = "DUMMY";
	private final JwtUtil jwtUtil;
	
	@Autowired
	public TwilioService(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	public JSONObject getOtp() throws Exception{
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("otp", otp);
			return jsonObj;
		}catch(Exception e) {
			throw e;
		}
		
	}
	// as verification completed we will send a jwt token to the client 
	public void verifyOtp(JSONObject jsonObj1) throws Exception{
		try {
			
			System.out.println(jsonObj1);
			String otp2 = (String)jsonObj1.get("otp");
			System.out.println(jsonObj1);
			System.out.println(otp2); 
			if(otp2 == this.otp) {
				System.out.println("the otp is correct verified");
			}
			else {
				System.out.println("the otp is wrong not verified");
				
			}
		}catch(Exception e) {
			throw e;
		}
	}

}

