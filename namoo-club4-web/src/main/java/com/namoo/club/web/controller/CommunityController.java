package com.namoo.club.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.namoo.club.domain.entity.Category;
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
		String loginID = SessionManager.getInstance(req).getLoginEmail();

		ModelAndView mav = new ModelAndView();
		
		List<Community> unjoinedCommunities = communityService.findAllUnjoinedCommunities(loginID);
		mav.addObject("unjoinedCommunities", unjoinedCommunities);
		
		List<Community> joinedCommunities = communityService.findJoinedCommunities(loginID);
		mav.addObject("joinedCommunities", joinedCommunities);
		
		List<Community> managedCommunities = communityService.findManagedCommnities(loginID);
		mav.addObject("managedCommunities", managedCommunities);
		
		mav.setViewName("community/home");
		return mav;
	}
	
	@RequestMapping(value="cmcreate", method= RequestMethod.GET)
	public String communityCreate() {
		//
		return "community/create";
	}
	
	@RequestMapping(value="cmcreate", method= RequestMethod.POST)
	public String doCommuniyCreate (
			@RequestParam("cmName") String communityName,
			@RequestParam("description") String description,
			@RequestParam("cateogyr") String[] categoryNames,
			HttpServletRequest req) {
		//
		String loginEmail = SessionManager.getInstance(req).getLoginEmail();
		
		List<Category> categories = new ArrayList<>();

		for (String value : categoryNames) {
			if (!value.equals("")) {
				Category category = new Category(value);
				categories.add(category);
			}
		}

		communityService.registCommunity(communityName, description, loginEmail, categories);
		
		//return new RedirectView("community/main", true);
		return "redirect:/community/main";
	}
	
	
}
	
