package com.namoo.club.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.club.service.facade.TownerService;
import com.namoo.club.web.session.SessionManager;

@Controller
@RequestMapping("/user")
public class LoginController{
	//	
	@Autowired
	private TownerService townerService;
	
//	public ModelAndView login(HttpServletRequest req){
//		//
//		String userID = SessionManager.getInstance(req).getLoginEmail();
//		String userPS = SessionManager.getInstance(req).getLoginPassword();
//		
//		//로그인 된 경우
//		if(SessionManager.getInstance(req).login(userID, userPS)){
//			session.setAttribute("loginID", userID);
//			resp.sendRedirect("cmList.xhtml");
//			return;
//		}
//		
//		//로그인 되지 않은 경우		
//		else{
//			RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/views/user/error.jsp");
//			dispatcher.forward(req, resp);
//			return;
//		}
//	}

	
}
