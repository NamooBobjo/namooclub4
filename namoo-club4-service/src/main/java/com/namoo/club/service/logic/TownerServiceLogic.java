package com.namoo.club.service.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namoo.club.dao.CommunityDao;
import com.namoo.club.dao.SocialPersonDao;
import com.namoo.club.domain.entity.Community;
import com.namoo.club.domain.entity.SocialPerson;
import com.namoo.club.service.facade.TownerService;
import com.namoo.club.service.logic.exception.NamooRuntimeException;

@Service
public class TownerServiceLogic implements TownerService {

	@Autowired
	private SocialPersonDao dao;
	
	@Autowired
	private CommunityDao comdao;
	
	@Override
	public boolean loginAsTowner(String email, String password) {
		// 
		SocialPerson towner = dao.readPerson(email);								
		if (towner != null && towner.getPassword().equals(password)) {
			return true; 
		}
		
		return false;
	}

	@Override
	public void registTowner(String name, String email, String password) {
		// 
		if (dao.readPerson(email) != null) {
			throw new NamooRuntimeException("해당 주민이 이미 존재합니다. 등록할 수 없습니다.");
		}
		
		SocialPerson towner = new SocialPerson(name, email, password);
		dao.createPerson(towner);
	}

	@Override
	public void removeTowner(String email) {
		// 
		// 커뮤니티의 회원으로 가입된 경우 삭제하지 못한다.
		
		List<Community> communities = comdao.readJoinedCommunities(email);
		if (communities != null && !communities.isEmpty()) {
			throw new NamooRuntimeException(
				"커뮤니티에 가입된 회원은 삭제할 수 없습니다. 먼저 커뮤니티를 탈퇴하세요. ["+communities.get(0).getName()+"]");
		}
		
		dao.deletePerson(email);
	}

	@Override
	public SocialPerson findTowner(String email) {
		//
		return dao.readPerson(email);
	}

	@Override
	public List<SocialPerson> findAllTowner() {
		// 
		return dao.readAllPerson();
	}
}
