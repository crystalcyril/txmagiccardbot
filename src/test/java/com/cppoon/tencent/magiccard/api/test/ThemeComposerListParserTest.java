/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import com.cppoon.tencent.magiccard.api.ThemeComposeListParser;
import com.cppoon.tencent.magiccard.api.ThemeComposeRule;
import com.cppoon.tencent.magiccard.api.impl.SimpleThemeComposeListParser;
import com.cppoon.tencent.magiccard.util.IOUtil;
import com.google.common.io.Resources;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ThemeComposerListParserTest {

	@Test
	public void test() {

		ThemeComposeListParser parser = new SimpleThemeComposeListParser();

		// configure listener
		TestThemeComposeListParserListener listener = new TestThemeComposeListParserListener();
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
		assertEquals("number of compose rules", 2987, listener.getCount());

		// check the first record
		ThemeComposeRule rule = listener.getByCardId(42);
		assertEquals("theme ID", 45, rule.getThemeId());
		assertEquals("state", 1, rule.getState());
		assertEquals("target card ID", 42, rule.getTargetCardId());
		assertTrue("childen card IDs", Arrays.equals(new int[] { 41,40,43 }, rule.getChildrenCardIds()));
		assertEquals("build time", 14400, rule.getBuildTime());
		
		// check the latest card at the time of coding.
		// 312,1,4717,4716,4712,4713,3600
		rule = listener.getByCardId(4717);
		assertEquals("theme ID", 312, rule.getThemeId());
		assertEquals("state", 1, rule.getState());
		assertEquals("target card ID", 4717, rule.getTargetCardId());
		assertTrue("childen card IDs", Arrays.equals(new int[] { 4716,4712,4713 }, rule.getChildrenCardIds()));
		assertEquals("build time", 3600, rule.getBuildTime());
		
	}

}
