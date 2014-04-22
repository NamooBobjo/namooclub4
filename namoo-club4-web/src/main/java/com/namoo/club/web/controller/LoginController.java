package com.namoo.club.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.club.service.facade.TownerService;
import com.namoo.club.web.session.SessionManager;

@Controller
@RequestMapping("/user")
public class LoginController{
	//	
	@Autowired
	private TownerService townerService;
	
	@RequestMapping("/init")
	public ModelAndView init(HttpServletRequest req){
		//
		ModelAndView mav = new ModelAndView();
		
		if(SessionManager.getInstance(req).getLoginEmail()!=null){
			mav.setViewName("community/home");
			return mav;
		}
		mav.setViewName("user/login");
		return mav;
	}
	
	@RequestMapping(value="/regist", method=RequestMethod.GET)
	public String regist(){
		return "user/join";
	}
	
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public String doRegist(
			@RequestParam("userName") String name,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpServletRequest req){
		//
		if(name.equals("") || email.equals("")|| password.equals("")){
			return "redirect:/user/regist";
		}
		townerService.registTowner(name, email, password);
		
		return "redirect:/user/init";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(
			@RequestParam("userID") String userID,
			@RequestParam("userPS") String userPS,
			HttpServletRequest req){
		//
		//로그인 된 경우
		if(townerService.loginAsTowner(userID, userPS)){
			return "redirect:/community/main";
		}
		
		//로그인 되지 않은 경우		
		else{
			return "common/error";
		}
	}
}
