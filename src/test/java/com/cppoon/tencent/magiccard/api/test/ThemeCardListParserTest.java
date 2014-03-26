/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import com.cppoon.tencent.magiccard.api.CardThemeInfo;
import com.cppoon.tencent.magiccard.api.ThemeCardListParser;
import com.cppoon.tencent.magiccard.api.impl.SimpleThemeCardListParser;
import com.cppoon.tencent.magiccard.util.IOUtil;
import com.google.common.io.Resources;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ThemeCardListParserTest {

	/**
	 * Test the case which the gift ID has 
	 * 
	 * @throws IOException
	 */
	@Test
	public void testParse_OK_GiftIDHasSharp() throws IOException {
		
		ThemeCardListParser parser = new SimpleThemeCardListParser();
		// configure listener
		TestThemeCardListParserListener listener = new TestThemeCardListParserListener();
		parser.setListener(listener);
		
		
		// parse
		InputStream is = Resources.getResource(
					"com/cppoon/tencent/magiccard/api/test/card_info_v3-theme_card_list_only-gift_id_has_sharp.js")
					.openStream();
		parser.parse(is);
		IOUtil.close(is);
		
		//
		// THEN
		//
		
		// check number of parsed cards.
		assertEquals("number of parsed themes", 1, listener.getThemeCount());

		// check the only theme
		// theme_id, theme_name,theme_Difficulty,theme_PublishTime,theme_PickRate, theme_Enable, theme_Prize,theme_Score,theme_color, gift, text,
		// card1_id,..,cardn_id,theme_type,version,time,offtime,flash_src_tid
		// [69,'海宝游中美',2,1264728250,0,1,200,850,0xa20703,'102|103|104|105#1_608','',[406,405,404,403,402,401,400,399,398,397,396,395,394,393,392,391,390,389,388,387,386,385,384],1,6,1309831123,1267687521,0,81]
		CardThemeInfo cardTheme = listener.getThemeById(69);
		assertEquals("theme ID", 69, cardTheme.getId());
		assertEquals("name", "海宝游中美", cardTheme.getName());
		assertEquals("difficulty", 2, cardTheme.getDifficulty());
		assertEquals("publish time", new Date(1264728250000L), cardTheme.getPublishTime());
		assertEquals("pick rate", 0, cardTheme.getPickRate());
		assertTrue("enabled", cardTheme.isEnabled());
		assertEquals("prize", 200.00, cardTheme.getCoins(), 0.0);
		assertEquals("score", 850, cardTheme.getExperience());
		assertTrue("gift IDs", Arrays.equals(new int[] { 102,103,104,105 }, 
				cardTheme.getGiftIds()));
		assertTrue("card IDs", Arrays.equals(new int[] { 406,405,404,403,402,401,400,399,398,397,396,395,394,393,392,391,390,389,388,387,386,385,384 }, 
				cardTheme.getCardIds()));
		assertEquals("theme type", 1, cardTheme.getType());
		assertEquals("version", 6, cardTheme.getVersion());
		assertEquals("time", new Date(1309831123000L), cardTheme.getTime());
		assertEquals("expiry time", new Date(1267687521000L), cardTheme.getExpiryTime());
		
	}
	
	/**
	 * The theme "道具卡" (ID = 111) has no gift IDs at all.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testParse_OK_GiftIDIsEmpty() throws IOException {
		
		ThemeCardListParser parser = new SimpleThemeCardListParser();
		// configure listener
		TestThemeCardListParserListener listener = new TestThemeCardListParserListener();
		parser.setListener(listener);
		
		
		// parse
		InputStream is = Resources.getResource(
					"com/cppoon/tencent/magiccard/api/test/card_info_v3-theme_card_list_only-gift_id_is_empty.js")
					.openStream();
		parser.parse(is);
		IOUtil.close(is);
		
		//
		// THEN
		//
		
		// check number of parsed cards.
		assertEquals("number of parsed themes", 1, listener.getThemeCount());

		// check the only theme
		// theme_id, theme_name,theme_Difficulty,theme_PublishTime,theme_PickRate, theme_Enable, theme_Prize,theme_Score,theme_color, gift, text,
		// card1_id,..,cardn_id,theme_type,version,time,offtime,flash_src_tid
		// [111,'道具卡',1,1290772354,0,1,0,0,0xffffff,'','',[1162],5,1,1291617551,0,0,0],
		CardThemeInfo cardTheme = listener.getThemeById(111);
		assertEquals("theme ID", 111, cardTheme.getId());
		assertEquals("name", "道具卡", cardTheme.getName());
		assertEquals("difficulty", 1, cardTheme.getDifficulty());
		assertEquals("publish time", new Date(1290772354000L), cardTheme.getPublishTime());
		assertEquals("pick rate", 0, cardTheme.getPickRate());
		assertTrue("enabled", cardTheme.isEnabled());
		assertEquals("prize", 0, cardTheme.getCoins(), 0.0);
		assertEquals("score", 0, cardTheme.getExperience());
		assertEquals("gift IDs", null, cardTheme.getGiftIds());
		assertTrue("card IDs", Arrays.equals(new int[] { 1162 }, 
				cardTheme.getCardIds()));
		assertEquals("theme type", 5, cardTheme.getType());
		assertEquals("version", 1, cardTheme.getVersion());
		assertEquals("time", new Date(1291617551000L), cardTheme.getTime());
		assertEquals("expiry time", null, cardTheme.getExpiryTime());
		
	}
	
	@Test
	public void test() throws IOException {

		ThemeCardListParser parser = new SimpleThemeCardListParser();
		// configure listener
		TestThemeCardListParserListener listener = new TestThemeCardListParserListener();
		parser.setListener(listener);

		// parse
		InputStream is = Resources.getResource(
					"com/cppoon/tencent/magiccard/api/test/card_info_v3.js")
					.openStream();
		parser.parse(is);
		IOUtil.close(is);

		//
		// assertion
		//

		// check number of parsed cards.
		assertEquals("number of parsed themes", 254, listener.getThemeCount());

		// check the first theme.
		CardThemeInfo cardTheme = listener.getThemeById(40);
		assertEquals("theme ID", 40, cardTheme.getId());
		assertEquals("name", "时尚男套装", cardTheme.getName());
		assertEquals("difficulty", 1, cardTheme.getDifficulty());
		assertEquals("publish time", new Date(1251970023000L), cardTheme.getPublishTime());
		assertEquals("pick rate", 0, cardTheme.getPickRate());
		assertTrue("enabled", cardTheme.isEnabled());
		assertEquals("prize", 100, cardTheme.getCoins(), 0.0);
		assertEquals("score", 200, cardTheme.getExperience());
		assertTrue("card IDs", Arrays.equals(new int[] { 66,65,64,39,38,37,36,35,33,32,31,30,29 }, 
				cardTheme.getCardIds()));
		assertEquals("theme type", 0, cardTheme.getType());
		assertEquals("version", 4, cardTheme.getVersion());
		assertEquals("time", new Date(1309831119000L), cardTheme.getTime());
		assertNull("expiry time", cardTheme.getExpiryTime());
		
		// check the last theme known as time of coding.
		cardTheme = listener.getThemeById(313);
		assertEquals("theme ID", 313, cardTheme.getId());
		assertEquals("name", "皮影十二生肖", cardTheme.getName());
		assertEquals("difficulty", 2, cardTheme.getDifficulty());
		assertEquals("publish time", new Date(1388592000000L), cardTheme.getPublishTime());
		assertEquals("pick rate", 100, cardTheme.getPickRate());
		assertTrue("enabled", cardTheme.isEnabled());
		assertEquals("prize", 888, cardTheme.getCoins(), 0.0);
		assertEquals("score", 888, cardTheme.getExperience());
		assertTrue("card IDs", Arrays.equals(new int[] { 4740,4739,4738,4737,4736,4735,4734,4733,4732,4731,4730,4729 }, 
				cardTheme.getCardIds()));
		assertEquals("theme type", 1, cardTheme.getType());
		assertEquals("version", 1, cardTheme.getVersion());
		assertEquals("time", new Date(1388493668000L), cardTheme.getTime());
		assertEquals("expiry time", new Date(1392652800000L), cardTheme.getExpiryTime());
		
	}

}
