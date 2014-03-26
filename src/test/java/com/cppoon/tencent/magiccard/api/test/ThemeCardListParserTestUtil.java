/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;

/**
 * 
 * @author Cyril
 * @since 0.1.0
 */
public final class ThemeCardListParserTestUtil {

	private ThemeCardListParserTestUtil() {
		super();
	}

	
	public static final void assertThemeIsCorrect(
			com.cppoon.tencent.magiccard.api.CardThemeInfo actualTheme, int themeId) {		
		
		if (themeId == 40)  {
			
			// check the first theme.
			assertEquals("theme ID", 40, actualTheme.getId());
			assertEquals("name", "时尚男套装", actualTheme.getName());
			assertEquals("difficulty", 1, actualTheme.getDifficulty());
			assertEquals("publish time", new Date(1251970023000L), actualTheme.getPublishTime());
			assertEquals("pick rate", 0, actualTheme.getPickRate());
			assertTrue("enabled", actualTheme.isEnabled());
			assertEquals("prize", 100, actualTheme.getCoins(), 0.0);
			assertEquals("score", 200, actualTheme.getExperience());
			assertTrue("card IDs", Arrays.equals(new int[] { 66,65,64,39,38,37,36,35,33,32,31,30,29 }, 
					actualTheme.getCardIds()));
			assertEquals("theme type", 0, actualTheme.getType());
			assertEquals("version", 4, actualTheme.getVersion());
			assertEquals("time", new Date(1309831119000L), actualTheme.getTime());
			assertNull("expiry time", actualTheme.getExpiryTime());
			
		} else if (themeId == 313) {
			
			// check the last theme known as time of coding.
			assertEquals("theme ID", 313, actualTheme.getId());
			assertEquals("name", "皮影十二生肖", actualTheme.getName());
			assertEquals("difficulty", 2, actualTheme.getDifficulty());
			assertEquals("publish time", new Date(1388592000000L), actualTheme.getPublishTime());
			assertEquals("pick rate", 800, actualTheme.getPickRate());
			assertTrue("enabled", actualTheme.isEnabled());
			assertEquals("prize", 888, actualTheme.getCoins(), 0.0);
			assertEquals("score", 888, actualTheme.getExperience());
			assertTrue("card IDs", Arrays.equals(new int[] { 4740,4739,4738,4737,4736,4735,4734,4733,4732,4731,4730,4729 }, 
					actualTheme.getCardIds()));
			assertEquals("theme type", 2, actualTheme.getType());
			assertEquals("version", 1, actualTheme.getVersion());
			assertEquals("time", new Date(1388493668000L), actualTheme.getTime());
			assertEquals("expiry time", new Date(1392652800000L), actualTheme.getExpiryTime());

		}
		
	}
	
}
