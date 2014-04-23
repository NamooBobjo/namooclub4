package com.namoo.club.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.club.domain.entity.Category;
import com.namoo.club.domain.entity.Club;
import com.namoo.club.domain.entity.Community;
import com.namoo.club.service.facade.ClubService;
import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.facade.TownerService;
import com.namoo.club.web.session.SessionManager;

@Controller
@RequestMapping("/club")
public class ClubController {

	//
	@Autowired
	private CommunityService communityService;
	@Autowired
	private TownerService townerService;
	@Autowired
	private ClubService clubService;
	
	@RequestMapping(value = "clList")
	public ModelAndView clubList(Model model, HttpServletRequest req){
	
		String loginID = SessionManager.getInstance(req).getLoginEmail();
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("loginID");
		int cmId = Integer.parseInt(req.getParameter("cmId"));
		Community community = communityService.findCommunity(cmId);
		List<Club> clubs = new ArrayList<>(communityService.findCommunity(cmId).getClubs());
		List<Club> belongclubs = new ArrayList<>(clubService.findBelongClub(cmId, email));
		List<Club> managedclubs = new ArrayList<>(clubService.findManagedClub(cmId, email));
		
		clubs = filter(clubs, belongclubs);
		belongclubs = filter(belongclubs, managedclubs);
		
		String cmname = community.getName();
		
		
		mav.addObject("managedclubs", managedclubs);
		mav.addObject("belongclubs", belongclubs);
		mav.addObject("clubs", clubs);
		model.addAttribute("cmName",cmname);
		model.addAttribute("cmId",cmId);	
		
		String loginUser = townerService.findTowner(loginID).getName();
		model.addAttribute("loginUser", loginUser);
		mav.setViewName("club/home");
		return mav;
		
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String clubCreate(
			@RequestParam("cmId") String cmid,
			Model model
			){
		int cmId = Integer.parseInt(cmid);
		Community community = communityService.findCommunity(cmId);
		List<Category> category = community.getCategory();		
		model.addAttribute("category", category);
		model.addAttribute("cmId",cmId);
		
		
		return "club/create";
	}
	
	@RequestMapping(value = "clCreate", method =  RequestMethod.POST)
	public String doClubCreate(
			@RequestParam("clName") String clubName,
			@RequestParam("content") String description,
			@RequestParam("cmId") String cmid,
			@RequestParam("category") String categoryName,
			Model model,
			HttpServletRequest req
			){
	
		String loginID = SessionManager.getInstance(req).getLoginEmail();
		int cmId = Integer.parseInt(cmid);
		Category category = new Category();
		category.setName(categoryName);
		
		Community community =	communityService.findCommunity(cmId);
		List<Club> clubs = community.getClubs();
		System.out.println(community.getClubs());
		
		for(Club club : clubs){
			System.out.println(category);
			System.out.println("22");
			System.out.println("클럽이름 " +club.getName());
			System.out.println("카테고리 : " +club.getCategory());
			
			// 해당카테고리의 클럽이 존재할 경우 
			if(category.equals(club.getCategory())){				
				return "redirect:/club/clList";
			}
		}				
		model.addAttribute("category", category);
		
		clubService.registClub(cmId, category, clubName, description, loginID);
		return "redirect:/club/clList";
	}
	
	private List<Club> filter(List<Club> all, List<Club> filters) {
		//
		List<Club> removed = new ArrayList<>();
		for (Club filter : filters) {
			for (Club club : all) {
				if (filter.getId().equals(club.getId())) {
					removed.add(club);
					break;
				}
			}
		}
		if (!removed.isEmpty()) {
			all.removeAll(removed);
		}
		return all;
	}
}
