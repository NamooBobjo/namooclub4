package com.namoo.club.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.namoo.club.domain.entity.Community;
import com.namoo.club.shared.DbCommonTest;

public class CommunityDaoTest extends DbCommonTest{
	//
	private static final String DATASET_XML = "/CommunityDaoTest_dataset.xml";
	
	@Autowired
	private CommunityDao dao;

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllCommunity() {
		//
		List<Community> communities = dao.readAllCommunity();
		
		assertEquals(2 ,communities.size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadCommunityByName(){
		Community community = dao.readCommunity(1);
		
		assertEquals("요리커뮤니티", community.getName());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testCreateCommunity() {
		//
		Community community = new Community();
		community.setName("abdbsdb");
		community.setDescription("dsfsdfsdf");
		
		int communityId = dao.createCommunity(community);
		
		community = dao.readCommunity(communityId);
		
		assertEquals("dsfsdfsdf", community.getDescription());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteCommunity() {
		//
		assertNotNull(dao.readCommunity(1));
		
		dao.deleteCommunity(1);
		
		assertNull(dao.readCommunity(1));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testUpdateCommunity() {
		//
		Community community =dao.readCommunity(1);
		community.setName("아아아아아아아");
		
		dao.updateCommunity(community);
		
		community = dao.readCommunity(1);
		assertEquals("아아아아아아아", community.getName());
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadJoinedCommunities(){
		//
		String email = "jjj@nate.com";
		List<Community> community = dao.readJoinedCommunities(email);
		
		assertEquals(1, community.size());
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadManagedCommunities(){
		//
		String email = "jjj@nate.com";
		List<Community> community = dao.readManagedCommunities(email);
		
		assertEquals(1, community.size());
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadUnjoinedCommunities(){
		//
		String email = "sss@nate.com";
		List<Community> community = dao.readUnjoinedCommunities(email);
		
		assertEquals(1, community.size());
	}
}
