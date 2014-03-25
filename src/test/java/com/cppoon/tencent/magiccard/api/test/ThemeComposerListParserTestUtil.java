/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import com.cppoon.tencent.magiccard.api.ThemeComposeRule;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ThemeComposerListParserTestUtil {
	
	/**
	 * Check the rule against the specified card ID. Only a limit set of 
	 * card ID is supported.
	 * 
	 * @param actualRule
	 * @param cardId
	 */
	public static void assertThemeComposeRuleOfCardIdIsCorrect(ThemeComposeRule actualRule, int cardId) {
		
		if (cardId == 42) {
			
			assertEquals("theme ID", 45, actualRule.getThemeId());
			assertEquals("state", 1, actualRule.getState());
			assertEquals("target card ID", 42, actualRule.getTargetCardId());
			assertTrue("childen card IDs", Arrays.equals(new int[] { 41,40,43 }, actualRule.getChildrenCardIds()));
			assertEquals("build time", 14400, actualRule.getBuildTime());			
			
		} else if (cardId == 4717) {
			
			assertEquals("theme ID", 312, actualRule.getThemeId());
			assertEquals("state", 1, actualRule.getState());
			assertEquals("target card ID", 4717, actualRule.getTargetCardId());
			assertTrue("childen card IDs", Arrays.equals(new int[] { 4716,4712,4713 }, actualRule.getChildrenCardIds()));
			assertEquals("build time", 3600, actualRule.getBuildTime());
			
		} else {
			
			fail("I don't support check compose rule for card ID " + cardId);
			
		}
		
	}

}
