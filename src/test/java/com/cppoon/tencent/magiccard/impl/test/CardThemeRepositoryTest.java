/**
 * 
 */
package com.cppoon.tencent.magiccard.impl.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.CardTheme;
import com.cppoon.tencent.magiccard.CardThemeManager;
import com.cppoon.tencent.magiccard.impl.SimpleCardThemeManager;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class CardThemeRepositoryTest {

	CardThemeManager cardThemeManager;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cardThemeManager = new SimpleCardThemeManager();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		cardThemeManager = null;
	}

	/**
	 * 
	 * <pre>
	 * [40,'时尚男套装',1,1251970023,0,1,100,200,0xffffff,'24','',[66,65,64,39,38,37,36,35,33,32,31,30,29],0,4,1309831119,0,0,1],
	 * </pre>
	 */
	@Test
	public void test_builder_buildCard_OK() {
		
		CardTheme theme = cardThemeManager.createBuilder().id(40).name("时尚男套装")
				.difficulty(1).publishTime(new Date(1251970023 * 1000)).type(0)
				.version(0).color(0xffffff).time(new Date(1309831119 * 1000))
				.build();
		
		assertNotNull("return value", theme);
		
		// check attributes
		assertEquals("difficulty", 1, theme.getDifficulty());
		assertNull("expiry time", theme.getExpiryTime());
		assertEquals("id", 40, theme.getId());
		assertEquals("pick rate", 0, theme.getPickRate());
		assertEquals("name", "时尚男套装", theme.getName());
		assertEquals("publish time", new Date(1251970023 * 1000), theme.getPublishTime());
		assertEquals("score", 0, theme.getExperience());
		assertEquals("time", new Date(1309831119 * 1000), theme.getTime());
		assertEquals("type", 0, theme.getType());
		assertEquals("version", 0, theme.getVersion());

	}

	@Test
	public void test_UpdateRepositoryWithNewCards() {

	}

}
