package com.namoo.club.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.namoo.club.domain.entity.SocialPerson;
import com.namoo.club.shared.DbCommonTest;

public class SocialPersonDaoTest extends DbCommonTest {
	//
	private static final String DATASET_XML = "/SocialPersonDaoTest_dataset.xml";
	
	@Autowired
	private SocialPersonDao dao;

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllPerson() {
		//
		List<SocialPerson> persons = dao.readAllPerson();
		assertEquals(2, persons.size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadPerson() {
		//
		SocialPerson person = dao.readPerson("ksy5350@nate.com");
		assertEquals("ksy5350@nate.com",person.getEmail());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testCreatePerson() {
		
		SocialPerson person = new SocialPerson();
		person.setName("정효진");
		person.setPassword("1234");
		person.setEmail("jjj@nate.com");
		
		assertEquals("jjj@nate.com", person.getEmail());
		
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeletePerson() {
		//
		dao.deletePerson("ksy5350@nate.com");
		assertNull(dao.readPerson("ksy5350@nate.com"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testUpdatePerson() {
		//
		SocialPerson person = dao.readPerson("ksy5350@nate.com");
		person.setName("삼천포");
		person.setPassword("1234");
		dao.updatePerson(person);
		
		person = dao.readPerson("ksy5350@nate.com");
		assertEquals("삼천포", person.getName());
	}

}
