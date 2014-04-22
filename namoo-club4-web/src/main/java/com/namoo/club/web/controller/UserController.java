package com.namoo.club.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.club.service.facade.TownerService;
import com.namoo.club.service.logic.exception.NamooExceptionFactory;
import com.namoo.club.web.session.SessionManager;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private TownerService townerService;
	
	@RequestMapping("/init")
	public ModelAndView init(HttpServletRequest req){
		//
		ModelAndView mav = new ModelAndView();
		
		if(SessionManager.getInstance(req).getLoginEmail()!=null){
			mav.setViewName("redirect:/community/main");
			return mav;
		}
		else{
			mav.setViewName("user/login");
			return mav;
		}
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
	
	@RequestMapping(value="withdraw", method=RequestMethod.GET)
	public String withdraw(){
		//
		return "user/withdraw";
	}
	
	@RequestMapping(value="withdraw", method=RequestMethod.POST)
	public String doWithdraw(
			@RequestParam("password") String password,
			HttpServletRequest req){
		
		String email = SessionManager.getInstance(req).getLoginEmail();
		if (!townerService.loginAsTowner(email, password)) {
			throw NamooExceptionFactory.createRuntime("패스워드를 확인해 주세요.");
		}
		
		townerService.removeTowner(email);//삭제
		SessionManager.getInstance(req).logout();
		
		return "redirect:/user/init";
	}
}
