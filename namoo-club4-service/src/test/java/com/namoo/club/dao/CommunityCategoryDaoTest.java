package com.namoo.club.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.namoo.club.domain.entity.Category;
import com.namoo.club.shared.DbCommonTest;

public class CommunityCategoryDaoTest extends DbCommonTest {
	//
	private static final String DATASET_XML = "CommunityCategoryDaoTest_dataset.xml";
	
	@Autowired
	private CommunityCategoryDao dao;

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllCategory() {
		List<Category> categories = dao.readAllCategory(1);
		
		assertEquals(2, categories.size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadCategory() {
		Category category = dao.readCategory(1, 1);
		assertEquals("한국요리", category.getName());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteCategory() {
		dao.deleteCategory(1);
		assertNull(dao.readCategory(1, 1));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testCreateCategory() {
		Category category = new Category();
		category.setName("한국요리");
		category.setCmId(1);
		
		assertEquals("한국요리", category.getName());
		
	}
}
