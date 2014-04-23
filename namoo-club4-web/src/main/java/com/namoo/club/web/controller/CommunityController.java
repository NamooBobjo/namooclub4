package com.namoo.club.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
		mav.addObject("unjoinedCommunityMembers", fill(unjoinedCommunities));
		
		List<Community> joinedCommunities = communityService.findJoinedCommunities(loginID);
		mav.addObject("joinedCommunities", joinedCommunities);
		mav.addObject("joinedCommunityMembers", fill(joinedCommunities));
		
		List<Community> managedCommunities = communityService.findManagedCommunities(loginID);
		mav.addObject("managedCommunities", managedCommunities);
		mav.addObject("managedCommunityMembers", fill(managedCommunities));
		
		mav.setViewName("community/home");
		return mav;
	}
	
	private int fill(List<Community> communities) {
		// 
		for (Community community : communities) {
			community.setMembers(communityService.findAllCommunityMember(community.getId()));
			return community.getMembers().size();
		}
		
		return 0;
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
			@RequestParam("category") String[] categoryNames,
			HttpServletRequest req) {
		//
		String loginID = SessionManager.getInstance(req).getLoginEmail();
		
		List<Category> categories = new ArrayList<>();

		for (String value : categoryNames) {
			if (!value.equals("")) {
				Category category = new Category(value);
				categories.add(category);
			}
		}

		communityService.registCommunity(communityName, description, loginID, categories);
		
		//return new RedirectView("community/main", true);
		return "redirect:/community/main";
	}
	
	
	@RequestMapping(value="cmjoin", method= RequestMethod.GET)
	public String joinCommunity(
			@RequestParam("cmId") int communityId, 
			Model model,
			HttpServletRequest req) {
		//
		Community community = communityService.findCommunity(communityId);
		String cmName = community.getName();
		
		model.addAttribute("cmId", communityId);
		model.addAttribute("cmName", cmName);
		
		
		return "community/join";
	}
	
	@RequestMapping(value="cmjoin", method=RequestMethod.POST)
	public String doCommunityJoin (
			@RequestParam("cmId") int communityId,
			HttpServletRequest req) {
		//
		String loginID = SessionManager.getInstance(req).getLoginEmail();
		communityService.joinAsMember(communityId, loginID);
		
		return "redirect:/community/main";
	}
	
	@RequestMapping(value="cmwithdraw", method=RequestMethod.GET)
	public String withdrawCommunity(
			@RequestParam("cmId") int communityId,
			Model model) {
		//
		Community community = communityService.findCommunity(communityId);
		String communityName = community.getName();
		
		model.addAttribute("cmName", communityName);
		model.addAttribute("cmId", communityId);
		
		return "community/withdraw";
	}
	
	@RequestMapping(value="cmwithdraw", method=RequestMethod.POST)
	public String doWithdrawCommunity(
			@RequestParam("cmId") int communityId, 
			HttpServletRequest req) {
		//
		String loginID = SessionManager.getInstance(req).getLoginEmail();
		communityService.withdrawalCommunity(communityId, loginID);
			
		return "redirect:/community/main";
	}
	
	@RequestMapping(value="cmremove", method=RequestMethod.GET)
	public String CommunityRemove (
			@RequestParam("cmId") int communityId,
			Model model) {
		//
		Community community = communityService.findCommunity(communityId);
		String communityName = community.getName();
		
		model.addAttribute("cmName", communityName);
		model.addAttribute("cmId", communityId);
		
		return "community/remove";
	}
	
	@RequestMapping(value="cmremove", method=RequestMethod.POST)
	public String doCommunityRemove (
			@RequestParam("cmId") int communityId) {
		
		
		communityService.removeCommunity(communityId);
		
		return "redirect:/community/main";
	}
}