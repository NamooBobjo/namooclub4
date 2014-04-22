package com.namoo.club.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.namoo.club.domain.entity.SocialPerson;
import com.namoo.club.service.facade.TownerService;

public class SessionManager {
	//
	private static final String LOGIN_Towner = "loginTowner";

	private HttpSession session;

	private SessionManager(HttpServletRequest req) {
		//
		this.session = req.getSession();
	}

	//--------------------------------------------------------------------------

	public static SessionManager getInstance(HttpServletRequest req) {
		//
		return new SessionManager(req);
	}

	public boolean isLogin() {
		//
		return (session.getAttribute(LOGIN_Towner) != null) ? true : false;
	}

	public boolean login(String email, String password) {
		//
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		TownerService townerService = context.getBean(TownerService.class);
		
		if (townerService.loginAsTowner(email, password)) {
			SocialPerson loginTowner = townerService.findTowner(email);
			session.setAttribute(LOGIN_Towner, loginTowner);
			session.setAttribute("loginUser", loginTowner.getName());
			return true;
		} else {
			session.invalidate();
			return false;
		}
	}

	public void logout() {
		//
		session.invalidate();
	}

	public String getLoginEmail() {
		// 
		if (isLogin()) {
			SocialPerson loginTowner = (SocialPerson) session.getAttribute(LOGIN_Towner);
			return loginTowner.getEmail();
		}
		return null;
	}
	
	public String getLoginPassword() {
		// 
		if (isLogin()) {
			SocialPerson loginTowner = (SocialPerson) session.getAttribute(LOGIN_Towner);
			return loginTowner.getPassword();
		}
		return null;
	}
}