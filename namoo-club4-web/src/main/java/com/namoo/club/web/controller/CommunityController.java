package com.namoo.club.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.club.domain.entity.Community;
import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.facade.TownerService;
import com.namoo.club.web.session.SessionManager;

@Controller
@RequestMapping("/community")
public class CommunityController {
	//
	@Autowired
	private CommunityService communityService;
	@Autowired
	private TownerService townerService;
	
	
	@RequestMapping(value = "/main")
	public ModelAndView communityList(HttpServletRequest req) {
		//
		String loginEmail = SessionManager.getInstance(req).getLoginEmail();

		ModelAndView mav = new ModelAndView();
		
		List<Community> unjoinedCommunities = communityService.findAllUnjoinedCommunities(loginEmail);
		mav.addObject("unjoinedCommunities", unjoinedCommunities);
		
		List<Community> joinedCommunities = communityService.findJoinedCommunities(loginEmail);
		mav.addObject("joinedCommunities", joinedCommunities);
		
		List<Community> managedCommunities = communityService.findManagedCommnities(loginEmail);
		mav.addObject("managedCommunities", managedCommunities);
		
		mav.setViewName("community/home");
		return mav;
	}
	
	@RequestMapping(value = "/main2")
	public String communityList2(HttpServletRequest req, Model model) {
		//
		String loginEmail = SessionManager.getInstance(req).getLoginEmail();
		
		List<Community> unjoinedCommunities = communityService.findAllUnjoinedCommunities(loginEmail);
		model.addAttribute("unjoinedCommunities", unjoinedCommunities);
		
		List<Community> joinedCommunities = communityService.findJoinedCommunities(loginEmail);
		model.addAttribute("joinedCommunities", joinedCommunities);
		
		List<Community> managedCommunities = communityService.findManagedCommnities(loginEmail);
		model.addAttribute("managedCommunities", managedCommunities);
		
		return "community/home";
	}
	
}
