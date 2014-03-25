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

import com.cppoon.tencent.magiccard.api.CardTheme;
import com.cppoon.tencent.magiccard.api.ThemeCardListParser;
import com.cppoon.tencent.magiccard.api.impl.SimpleThemeCardListParser;
import com.cppoon.tencent.magiccard.util.IOUtil;
import com.google.common.io.Resources;

/**
 * 
 * 
 * @author Cyril
 */
public class ThemeCardListParserTest {

	@Test
	public void test() {

		ThemeCardListParser parser = new SimpleThemeCardListParser();

		// configure listener
		TestThemeCardListParserListener listener = new TestThemeCardListParserListener();
		parser.setListener(listener);

		// parse
		InputStream is = null;
		try {
			is = Resources.getResource(
					"com/cppoon/tencent/magiccard/api/test/card_info_v3.js")
					.openStream();
			parser.parse(is);
		} catch (IOException e) {
		} finally {
			IOUtil.close(is);
		}

		//
		// assertion
		//

		// check number of parsed cards.
		assertEquals("number of parsed themes", 245, listener.getThemeCount());

		// check the first theme.
		CardTheme cardTheme = listener.getThemeById(40);
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
		assertEquals("pick rate", 800, cardTheme.getPickRate());
		assertTrue("enabled", cardTheme.isEnabled());
		assertEquals("prize", 888, cardTheme.getCoins(), 0.0);
		assertEquals("score", 888, cardTheme.getExperience());
		assertTrue("card IDs", Arrays.equals(new int[] { 4740,4739,4738,4737,4736,4735,4734,4733,4732,4731,4730,4729 }, 
				cardTheme.getCardIds()));
		assertEquals("theme type", 2, cardTheme.getType());
		assertEquals("version", 1, cardTheme.getVersion());
		assertEquals("time", new Date(1388493668000L), cardTheme.getTime());
		assertEquals("expiry time", new Date(1392652800000L), cardTheme.getExpiryTime());

		
	}

}
