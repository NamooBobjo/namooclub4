package com.namoo.club.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.namoo.club.web.session.SessionManager;

@Controller
@RequestMapping("/login")
public class LoginController{
	//	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(
			@RequestParam("userID") String userID,
			@RequestParam("userPS") String userPS,
			HttpServletRequest req){
		//
		SessionManager sessionManager = SessionManager.getInstance(req);
		if(sessionManager.login(userID, userPS)){
			//로그인 된 경우
			return "redirect:/community/main";
		}
		else{
			//로그인 되지 않은 경우
			return "user/error";
		}
	}
	
	@RequestMapping(value="/logout.do", method=RequestMethod.GET)
	public String logout(HttpServletRequest req){
		//
		SessionManager manager = SessionManager.getInstance(req);
		manager.logout();
		
		return "redirect:/user/init";
	}
}
