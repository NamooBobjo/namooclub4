package com.namoo.club.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.namoo.club.domain.entity.Category;
import com.namoo.club.domain.entity.Club;
import com.namoo.club.shared.DbCommonTest;

public class ClubDaoTest extends DbCommonTest {
	//
	private static final String DATASET_XML = "ClubDaoTest_dataset.xml";
	
	@Autowired
	private ClubDao dao;

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllClub() {
		//
		List<Club> clubs = dao.readAllClub();
		assertEquals(2,clubs.size());	
			
		}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllClubByCmId() {
		//
		List<Club> clubs = dao.readAllClub(1);
		assertEquals(2,clubs.size());		
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadManagedClubs() {
		//
		int cmId = 1;
		String email = "sss@nate.com";
		List<Club> clubs = dao.readManagedClubs(cmId, email);
		
		assertEquals(1, clubs.size());
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadClub() {
		//
		Club club = dao.readClub(1);
		assertEquals("한식", club.getName());
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadClubByName() {
		//
		Club club = dao.readClubByName("한식");
		assertEquals("한식", club.getName());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testCreateClub() {

		Club club = new Club();
		club.setName("한식클럽");
		club.setDescription("한식 연구모임");
		club.setCmid(1);
		club.setCategory(new Category(1, "한국요리"));
		Integer clid = dao.createClub(club);
	
		
		club = dao.readClub(clid);
	
		assertEquals("한식클럽", club.getName());
		assertEquals("한식 연구모임", club.getDescription());
		
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteClub() {

		dao.deleteClub(1);
		assertNull(dao.readClub(1));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testUpdateClub() {
		//
		Club club = dao.readClub(1);
		club.setName("양식");
		dao.updateClub(club);
		
		club = dao.readClub(1);
		assertEquals("양식", club.getName());
	}
}
