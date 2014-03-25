/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.api.ThemeComposeListParser;
import com.cppoon.tencent.magiccard.api.ThemeComposeRule;
import com.cppoon.tencent.magiccard.api.impl.SimpleThemeComposeListParser;
import com.cppoon.tencent.magiccard.util.IOUtil;
import com.google.common.io.Resources;

/**
 * This is a very low level test which each attribute will be checked to ensure
 * no error will occur.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ThemeComposerListParserAttributeTest {

	ThemeComposeListParser parser;
	
	TestThemeComposeListParserListener listener;
	
	@Before
	public void setUp() throws Exception {
		
		parser = new SimpleThemeComposeListParser();
		listener = new TestThemeComposeListParserListener();
		
		// configure listener
		parser.setListener(listener);

		// parse
		InputStream is = Resources
				.getResource(
						"com/cppoon/tencent/magiccard/api/test/fake/card_info_v3-theme_compose_list_only.js")
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
	
	/**
	 * <strong>Note</strong>
	 * <p>
	 * 
	 * Make sure the two rules to be checked should have completely different
	 * attribute values!
	 */
	@Test
	public void testParse_OK() {
		
		ThemeComposeRule actualRule;
		
		// rule 1
		actualRule = listener.getByCardId(42);
		assertEquals("theme ID", 45, actualRule.getThemeId());
		assertEquals("state", 1, actualRule.getState());
		assertEquals("target card ID", 42, actualRule.getTargetCardId());
		assertTrue("material card IDs", Arrays.equals(new int[] { 41,40,43 }, actualRule.getChildrenCardIds()));
		assertEquals("build time", 14400, actualRule.getBuildTime());			
		
		// rule 2
		actualRule = listener.getByCardId(4854);
		assertEquals("theme ID", 322, actualRule.getThemeId());
		assertEquals("state", 2, actualRule.getState());
		assertEquals("target card ID", 4854, actualRule.getTargetCardId());
		assertTrue("material card IDs", Arrays.equals(new int[] { 4852,4850,4848 }, actualRule.getChildrenCardIds()));
		assertEquals("build time", 3600, actualRule.getBuildTime());			
		
	}
	
}