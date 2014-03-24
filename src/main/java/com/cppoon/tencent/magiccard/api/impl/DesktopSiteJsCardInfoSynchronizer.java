/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.io.InputStream;

import com.cppoon.tencent.magiccard.CardInfoSynchronizer;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.CardThemeManager;
import com.cppoon.tencent.magiccard.api.ThemeCardListParser;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class DesktopSiteJsCardInfoSynchronizer implements CardInfoSynchronizer {

	CardThemeManager cardThemeManager;

	CardManager cardManager;
	
	
	
	@Override
	public void synchronize(InputStream is) {
		
		ThemeCardListParser themeCardListParser = new SimpleThemeCardListParser();
		SimpleCardInfoParser cardInfoParser = new SimpleCardInfoParser();
		SimpleThemeComposeListParser themeComposeListParser = new SimpleThemeComposeListParser();
		
		
		themeCardListParser.parse(is);
		themeComposeListParser.parse(is);
		cardInfoParser.parse(is);
		
		
	}

	public void setCardThemeManager(CardThemeManager cardThemeManager) {
		this.cardThemeManager = cardThemeManager;
	}

	public void setCardManager(CardManager cardManager) {
		this.cardManager = cardManager;
	}

}
