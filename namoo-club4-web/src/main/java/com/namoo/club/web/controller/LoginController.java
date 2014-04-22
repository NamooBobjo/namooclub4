package com.namoo.club.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.club.service.facade.TownerService;
import com.namoo.club.service.logic.exception.NamooExceptionFactory;
import com.namoo.club.web.session.SessionManager;

@Controller
@RequestMapping("/user")
public class LoginController{
	//	
	@Autowired
	private TownerService townerService;
	
	@RequestMapping("/doWithdraw")
	public ModelAndView doWithdraw(HttpServletRequest req){
		
		String email = SessionManager.getInstance(req).getLoginEmail();
		String password = SessionManager.getInstance(req).getLoginPassword();
		
		ModelAndView mav = new ModelAndView();
		
		if (!townerService.findTowner(email).getPassword().equals(password)) {
			throw NamooExceptionFactory.createRuntime("패스워드를 확인해 주세요.");
		}
		
		townerService.removeTowner(email);//삭제
		
		mav.setViewName("user/login");
		return mav;
	}
	
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
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest req){
		//
		SessionManager.getInstance(req).logout();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/join");
		
		return mav;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest req){
		//
		String userID = SessionManager.getInstance(req).getLoginEmail();
		String userPS = SessionManager.getInstance(req).getLoginPassword();
		
		ModelAndView mav = new ModelAndView();
		
		//로그인 된 경우
		if(SessionManager.getInstance(req).login(userID, userPS)){
			mav.setViewName("community/home");
			return mav;
		}
		//로그인 되지 않은 경우		
		else{
			mav.setViewName("user/errer");
			return mav;
		}
	}
}
