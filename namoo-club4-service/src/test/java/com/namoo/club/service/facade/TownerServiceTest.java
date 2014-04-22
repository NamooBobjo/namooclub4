package com.namoo.club.service.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.namoo.club.domain.entity.SocialPerson;
import com.namoo.club.service.logic.exception.NamooRuntimeException;
import com.namoo.club.shared.DbCommonTest;

public class TownerServiceTest extends DbCommonTest {
	//
	private static final String DATASET_XML = "TownerServiceTest_dataset.xml";
	
	@Autowired
	private TownerService townerService;
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testLoginAsTowner() {
		//
		assertFalse(townerService.loginAsTowner("aa", "gggg"));
		assertTrue(townerService.loginAsTowner("sss@nate.com", "1234"));
		
	}

	@Test(expected=NamooRuntimeException.class)
	@DatabaseSetup(DATASET_XML)
	public void testRegistTownerWithException() {
		//
		String name = "정효진";
		String email = "jjj@nate.com";
		String password = "1234";
		
		townerService.registTowner(name, email, password);
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testRegistTowner() {
		//
		String name = "정효진";
		String email = "kkk@nate.com";
		String password = "1234";
		
		townerService.registTowner(name, email, password);
		
		SocialPerson towner = townerService.findTowner(email);
		
		assertEquals(name, towner.getName());
		assertEquals(email, towner.getEmail());
		assertEquals(password, towner.getPassword());

	}
	
	@Test(expected=NamooRuntimeException.class)
	@DatabaseSetup(DATASET_XML)
	public void testRemoveTownerWithException() {
		// 커뮤니티의 회원으로 가입된 경우 삭제하지 못한다.
		townerService.removeTowner("jjj@nate.com");
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testRemoveTowner() {
		//
		String email = "ksy5350@nate.com";
		townerService.removeTowner(email);
		
		assertNull(townerService.findTowner(email));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testFindTowner() {
		//
		SocialPerson towner = townerService.findTowner("ksy5350@nate.com");
		assertEquals("ksy5350@nate.com", towner.getEmail());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testFindAllTowner() {
		//
		List<SocialPerson> persons = townerService.findAllTowner();
		assertEquals(3, persons.size());
	}

}
