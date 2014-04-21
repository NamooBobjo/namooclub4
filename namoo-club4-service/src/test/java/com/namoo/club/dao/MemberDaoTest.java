package com.namoo.club.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.namoo.club.domain.entity.ClubMember;
import com.namoo.club.domain.entity.CommunityMember;
import com.namoo.club.domain.entity.SocialPerson;
import com.namoo.club.shared.DbCommonTest;

public class MemberDaoTest extends DbCommonTest{
	//
	private static final String DATASET_XML = "/MemberDaoTest_dataset.xml";
	
	@Autowired
	private MemberDao dao;
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadCommunityMember() {
		//
		List<CommunityMember> test = dao.readCommunityMembers(1);
		assertEquals(2, test.size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadClubMember() {
		List<ClubMember> test = dao.readClubMembers(1);
		assertEquals(2, test.size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testJoinAsCommunityMember() {
		SocialPerson person = new SocialPerson("yong", "yong@nate.com", "1234");
		
		dao.joinAsCommunityMember(1, 1, person);
		List<CommunityMember> test = dao.readCommunityMembers(1);
		assertEquals(2, test.size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testJoinAsClubMember() {
		SocialPerson person = new SocialPerson("yong", "yong@nate.com", "1234");		
		dao.joinAsClubMember(1, 0, person);
		List<ClubMember> test = dao.readClubMembers(1);
		assertEquals(2, test.size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteCommunityMember() {
		int beforeCount = dao.readCommunityMembers(1).size();
		dao.deleteCommunityMember(1, "jjj@nate.com");
		int afterCount = dao.readCommunityMembers(1).size();
		assertEquals(beforeCount - 1, afterCount);
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteClubMember() {
		int brforeCount = dao.readClubMembers(1).size();
		dao.deleteClubMember(1, "sss@nate.com");
		int afterCount = dao.readClubMembers(1).size();
		assertEquals(afterCount + 1, brforeCount);
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testUpdateClubMember() {
		//
		int clubId = 1;
		String email = "sss@nate.com";
		boolean isManager = true;
		
		int beforeCount = dao.readClubManagers(clubId).size();
		
		dao.updateClubMember(clubId, email, isManager);
		
		int afterCount = dao.readClubManagers(clubId).size();
		assertEquals(beforeCount + 1, afterCount);
		
	}
}
