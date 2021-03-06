package com.namoo.club.service.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namoo.club.dao.ClubDao;
import com.namoo.club.dao.MemberDao;
import com.namoo.club.dao.SocialPersonDao;
import com.namoo.club.domain.entity.Category;
import com.namoo.club.domain.entity.Club;
import com.namoo.club.domain.entity.ClubMember;
import com.namoo.club.domain.entity.SocialPerson;
import com.namoo.club.service.facade.ClubService;
import com.namoo.club.service.logic.exception.NamooExceptionFactory;


@Service
public class ClubServiceLogic implements ClubService {

	@Autowired
	private ClubDao clubDao; 

	@Autowired
	private SocialPersonDao socialdao;

	@Autowired
	private MemberDao memberdao;

	private SocialPerson createPerson(String name, String email,
			String password) {
		//
		SocialPerson person = new SocialPerson(name, email, password);
		socialdao.createPerson(person);
		
		return person;
	}

	@Override
	public void registClub(int communityId, Category category, String clubName, String description, String email) {
		//
		Club club = clubDao.readClubByName(clubName);
		if (club != null) {
			throw NamooExceptionFactory.createRuntime("이미 존재하는 클럽입니다.");
		}
		
		SocialPerson towner = socialdao.readPerson(email);
		if (towner == null) {
			throw NamooExceptionFactory.createRuntime("존재하지 않는 주민입니다.");
		}
		
		club = new Club(communityId, clubName, description, category);
		clubDao.createClub(club);			
	}

	@Override
	public Club findClub(String clubName) {
		//
		return clubDao.readClubByName(clubName);
	}
	
	
	@Override
	public Club findClub(int clid) {
		//
		return clubDao.readClub(clid);
	}

	@Override
	public void joinAsMember(int clid, String name, String email,String password) {
		
		
		Club club = clubDao.readClub(clid);
		
		if (club == null) {
			throw NamooExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		
		Integer clId = club.getId();
		
		if (isExistMember(clId, email)) {
			throw NamooExceptionFactory.createRuntime("해당 주민이 이미 존재합니다.");
		}

		SocialPerson towner = createPerson(name, email, password);
		club.addMember(towner);		
		
		memberdao.joinAsClubMember(clId, 0, towner);
	}

	private boolean isExistMember(int clId, String email) {
		
		List<ClubMember> persons = memberdao.readClubMembers(clId);
		for(ClubMember person : persons){
			if(person.getEmail().equals(email)){
				return true;
			}
		}		
		return false;
	}


	@Override
	public void joinAsMember(int clid, String email) {
		//
		Club club = clubDao.readClub(clid);
		
		if (club == null) {
			throw NamooExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		
		SocialPerson towner = socialdao.readPerson(email);
		if (towner == null) {
			throw NamooExceptionFactory.createRuntime("존재하지 않는 주민입니다.");
		}
		int clId = club.getId();
		club.addMember(towner);
		memberdao.joinAsClubMember(clId, 0, towner);
	}

	@Override
	public List<Club> findAllClubs() {
		// 
		return clubDao.readAllClub();
	}

	@Override
	public ClubMember findClubMember(int clubId, String email) {
		//
		return memberdao.readClubMember(clubId, email);
	}

	@Override
	public List<ClubMember> findAllClubMember(int clid) {
		// 
		Club club = clubDao.readClub(clid);
		if (club == null) {
			throw NamooExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		
		Integer clId = club.getId();
		List<ClubMember> persons = memberdao.readClubMembers(clId);
		
		return persons;
	}

	@Override
	public int countMembers(int clubId) {
		//
		return memberdao.readClubMembers(clubId).size();
	}

	@Override
	public void removeClub(int clid) {
		
		Club club = clubDao.readClub(clid);
		Integer clId = club.getId();
		clubDao.deleteClub(clId);
	}

	@Override
	public List<Club> findBelongClub(int cmId, String email) {
		//
		List<Club> clubs = clubDao.readAllClub(cmId);
		if (clubs == null) return null;
		
		List<Club> belongs = new ArrayList<>();
		for (Club club : clubs) {
			if (club.findMember(email) != null && club.getCmid() == cmId) {
				belongs.add(club);
			}
		}
		return belongs;
	}

	@Override
	public List<Club> findClubsById(int clubId) {
		// 
		List<Club> clubs = clubDao.readAllClub(clubId);
		return clubs;
	}
	
	@Override
	public List<Club> findManagedClub(int cmId, String email) {
		//
		List<Club> clubs = clubDao.readManagedClubs(cmId, email);
		
		return clubs;
	}

	@Override
	public void withdrawalClub(int clid, String email) {
		//
		Club club =clubDao.readClub(clid);
		if (club == null) {
			throw NamooExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		
		Integer clId = club.getId();
		memberdao.deleteClubMember(clId, email);
			
	}
}
