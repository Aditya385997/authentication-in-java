package com.example.demoauthserver.controller;

//import java.util.Collection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoauthserver.model.ClientDetails;
import com.example.demoauthserver.model.TwilioOtpModel;
import com.example.demoauthserver.services.ClientService;
import com.example.demoauthserver.services.TwilioService;

@RestController
@RequestMapping(path="/auth")
public class AuthController {	
	
//	private String otp;
	private final TwilioService twilioService;
	private final ClientService clientService;
	@Autowired
	public AuthController(ClientService clientService,TwilioService twilioService) {
		this.clientService = clientService;
		this.twilioService = twilioService;
	}
	// this approach is where there are no same users in database further would be integrated by abhijeet chavan 
	
	@PostMapping(value="/otp",consumes="application/json",produces="application/json")
	public ResponseEntity<String> insertUserNameAndPassword(@RequestBody ClientDetails clientDetails){
		JSONObject jsonObj = new JSONObject();
		try {
			//put if condition and check wheather the username is existing or not if not existing generate otp and if existing generate toke and contact with app service
			jsonObj = twilioService.getOtp();	
			System.out.println(jsonObj);
			System.out.println("otp get Sucessfully");
			jsonObj.get("otp");
			jsonObj = clientService.insert(clientDetails);
			return new ResponseEntity<>(jsonObj.toString(),HttpStatus.OK);
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(jsonObj.toString(),HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping(value="/verifyotp",consumes="application/json",produces="application/json")
	public ResponseEntity<String> insertOtpUsernameAndPassword(@RequestBody ClientDetails clientDetails,String otp){
		JSONObject jsonObj = new JSONObject();
//		jsonObj.put("clientDetails",clientDetails);
//		jsonObj.put("otp",this.otp);
		try {
			jsonObj.put("clientid", clientDetails.getId());
			jsonObj.put("clientUsername", clientDetails.getUsername());
			jsonObj.put("password", clientDetails.getPassword());
			jsonObj.put("otp",otp);
			twilioService.verifyOtp(jsonObj);		
			return new ResponseEntity<>(jsonObj.toString(),HttpStatus.OK);

		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(jsonObj.toString(),HttpStatus.BAD_REQUEST);
		}
	}
		
	}
