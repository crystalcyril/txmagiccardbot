/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.io.InputStream;

import com.cppoon.tencent.magiccard.api.test.TestCardInfoParserListener;
import com.cppoon.tencent.magiccard.api.test.TestThemeCardListParserListener;
import com.cppoon.tencent.magiccard.api.test.TestThemeComposeListParserListener;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class JsV3CardInfoParser {
	
	TestThemeComposeListParserListener themeComposeListener;

	TestCardInfoParserListener cardInfoListener;
	
	TestThemeCardListParserListener themeCardListener;
	
	public void parse(InputStream is) {

		
		
	}

	public void setListener(
			TestThemeComposeListParserListener themeComposeListener) {
		this.themeComposeListener = themeComposeListener;
	}

	public void setListener(TestCardInfoParserListener cardInfoListener) {
		this.cardInfoListener = cardInfoListener;
	}

	public void setListener(TestThemeCardListParserListener themeCardListener) {
		this.themeCardListener = themeCardListener;
	}

}
