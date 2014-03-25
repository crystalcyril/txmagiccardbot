/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.api.impl.JsV3CardInfoParser;
import com.cppoon.tencent.magiccard.util.IOUtil;
import com.google.common.io.Resources;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class JsV3CardInfoParserTest {

	JsV3CardInfoParser parser;

	TestThemeCardListParserListener themeCardListener = new TestThemeCardListParserListener();
	TestThemeComposeListParserListener themeComposeListener = new TestThemeComposeListParserListener();
	TestCardInfoParserListener cardInfoListener = new TestCardInfoParserListener();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new JsV3CardInfoParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
		parser = null;

		themeCardListener = null;
		themeCardListener = null;
		cardInfoListener = null;
		
	}

	@Test
	public void testParse_NormalFile_OK() throws Exception {

		setStandardTestListeners();

		// parse
		InputStream is = Resources.getResource(
				"com/cppoon/tencent/magiccard/api/test/card_info_v3.js")
				.openStream();
		
		parser.parse(is);
		
		IOUtil.close(is);
		
		//
		// THEN
		//
		
		// check some theme compose rules.
		ThemeComposerListParserTestUtil
				.assertThemeComposeRuleOfCardIdIsCorrect(
						themeComposeListener.getByCardId(42), 42);
		ThemeComposerListParserTestUtil
				.assertThemeComposeRuleOfCardIdIsCorrect(
						themeComposeListener.getByCardId(4717), 4717);

		// check some card
		CardInfoParserTestUtil.assertCardInfoIsCorrect(cardInfoListener.getCardById(29), 29);
		CardInfoParserTestUtil.assertCardInfoIsCorrect(cardInfoListener.getCardById(4740), 4740);
		
		// check some theme
		ThemeCardListParserTestUtil.assertThemeIsCorrect(themeCardListener.getThemeById(40), 40);
		ThemeCardListParserTestUtil.assertThemeIsCorrect(themeCardListener.getThemeById(313), 313);
		
	}

	protected void setStandardTestListeners() {

		themeCardListener = new TestThemeCardListParserListener();
		themeComposeListener = new TestThemeComposeListParserListener();
		cardInfoListener = new TestCardInfoParserListener();

		parser.setListener(themeCardListener);
		parser.setListener(themeComposeListener);
		parser.setListener(cardInfoListener);
	}

}
