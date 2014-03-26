/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.api.CardThemeInfo;
import com.cppoon.tencent.magiccard.api.ThemeCardListParser;
import com.cppoon.tencent.magiccard.api.impl.SimpleThemeCardListParser;
import com.cppoon.tencent.magiccard.util.IOUtil;
import com.google.common.io.Resources;

/**
 * This is a very low level test which each attribute will be checked to ensure
 * no error will occur.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ThemeCardListParserAttributeTest {

	ThemeCardListParser parser;
	
	TestThemeCardListParserListener listener;
	
	@Before
	public void setUp() throws Exception {
		
		parser = new SimpleThemeCardListParser();
		listener = new TestThemeCardListParserListener();
		
		// configure listener
		parser.setListener(listener);

		// parse
		InputStream is = Resources
				.getResource(
						"com/cppoon/tencent/magiccard/api/test/fake/card_info_v3-theme_card_list_only.js")
				.openStream();
		parser.parse(is);
		
		IOUtil.close(is);
		
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		listener = null;
		parser = null;

	}
	
	@Test
	public void testParse_ID() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1001);
		assertEquals("theme ID", 1001, actualTheme.getId());
		
		actualTheme = listener.getThemeById(1002);
		assertEquals("theme ID", 1002, actualTheme.getId());
		
	}
	
	@Test
	public void testParse_Name() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1002);
		assertEquals("name", "Theme 2", actualTheme.getName());
		
		actualTheme = listener.getThemeById(1003);
		assertEquals("name", "Theme 3", actualTheme.getName());
		
	}
	
	@Test
	public void testParse_Difficulty() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1003);
		assertEquals("difficulty", 5, actualTheme.getDifficulty());
		
		actualTheme = listener.getThemeById(1004);
		assertEquals("difficulty", 1, actualTheme.getDifficulty());
		
	}
	
	@Test
	public void testParse_PublishTime() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1004);
		assertEquals("publish time", new Date(1395244800L * 1000), actualTheme.getPublishTime());
		
		actualTheme = listener.getThemeById(1005);
		assertEquals("publish time", new Date(1251970023L * 1000), actualTheme.getPublishTime());
		
	}
	
	@Test
	public void testParse_PickRate() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1005);
		assertEquals("pick rate", 88, actualTheme.getPickRate());
		
		actualTheme = listener.getThemeById(1006);
		assertEquals("pick rate", 0, actualTheme.getPickRate());
		
	}
	
	@Test
	public void testParse_Enable() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1006);
		assertFalse("enable", actualTheme.isEnabled());
		
		actualTheme = listener.getThemeById(1007);
		assertTrue("enable", actualTheme.isEnabled());
		
	}
	
	@Test
	public void testParse_Coins() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1007);
		assertEquals("coins", 7777.789D, actualTheme.getCoins(), 0.00D);
		
		actualTheme = listener.getThemeById(1008);
		assertEquals("coins", 400.00D, actualTheme.getCoins(), 0.00D);
		
	}
	
	@Test
	public void testParse_Experience() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1008);
		assertEquals("experience", 8888, actualTheme.getExperience());
		
		actualTheme = listener.getThemeById(1009);
		assertEquals("experience", 540, actualTheme.getExperience());
		
	}
	
	@Test
	public void testParse_Color() {
		
//		CardTheme actualTheme;
//
//		actualTheme = listener.getThemeById(1007);
//		assertEquals("experience", 8888, actualTheme.getExperience());
//		
//		actualTheme = listener.getThemeById(1008);
//		assertEquals("experience", 540, actualTheme.getExperience());
		
	}
	
	@Test
	public void testParse_GiftIDs() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1010);
		assertTrue("gift IDs (actual: " + actualTheme.getGiftIds() + ")", Arrays.equals(new int[] { 101 }, actualTheme.getGiftIds()));
		
		actualTheme = listener.getThemeById(1011);
		assertTrue("gift IDs (actual: " + actualTheme.getGiftIds() + ")", Arrays.equals(new int[] { 201, 292, 273, 204 }, actualTheme.getGiftIds()));
		
	}
	
	@Test
	public void testParse_Text() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1011);
		assertEquals("text", "Theme 11 text", actualTheme.getText());
		
		actualTheme = listener.getThemeById(1012);
		assertEquals("text", "", actualTheme.getText());
		
	}
	
	@Test
	public void testParse_CardIDs() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1012);
		assertTrue("gift IDs (actual: " + actualTheme.getGiftIds() + ")", Arrays.equals(new int[] { 819,808,804,875,891,875 }, actualTheme.getCardIds()));
		
		actualTheme = listener.getThemeById(1013);
		assertTrue("gift IDs (actual: " + actualTheme.getGiftIds() + ")", Arrays.equals(new int[] { 403,402,401 }, actualTheme.getCardIds()));

	}
	
	@Test
	public void testParse_Type() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1013);
		assertEquals("type", 9, actualTheme.getType());
		
		actualTheme = listener.getThemeById(1014);
		assertEquals("type", 1, actualTheme.getType());

	}
	
	@Test
	public void testParse_Version() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1014);
		assertEquals("version", 301, actualTheme.getVersion());
		
		actualTheme = listener.getThemeById(1015);
		assertEquals("version", 42, actualTheme.getVersion());

	}
	
	@Test
	public void testParse_Time() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1015);
		assertEquals("time", new Date(1395743966L * 1000), actualTheme.getTime());
		
		actualTheme = listener.getThemeById(1016);
		assertEquals("time", new Date(1309831121L * 1000), actualTheme.getTime());

		actualTheme = listener.getThemeById(1014);
		assertNull("time", actualTheme.getTime());

	}
	
	@Test
	public void testParse_OffTime() {
		
		CardThemeInfo actualTheme;

		actualTheme = listener.getThemeById(1016);
		assertEquals("time", new Date(1309909684L * 1000), actualTheme.getExpiryTime());
		
		actualTheme = listener.getThemeById(1015);
		assertEquals("time", new Date(1395743967L * 1000), actualTheme.getExpiryTime());

		actualTheme = listener.getThemeById(1014);
		assertNull("time", actualTheme.getExpiryTime());

	}
	
}