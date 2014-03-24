/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.cppoon.tencent.magiccard.CardInfoSynchronizer;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.CardThemeManager;
import com.cppoon.tencent.magiccard.api.CardInfo;
import com.cppoon.tencent.magiccard.api.CardInfoParserListener;
import com.cppoon.tencent.magiccard.api.CardTheme;
import com.cppoon.tencent.magiccard.api.ThemeCardListParser;
import com.cppoon.tencent.magiccard.api.ThemeCardListParserListener;
import com.cppoon.tencent.magiccard.api.ThemeComposeListParserListener;
import com.cppoon.tencent.magiccard.api.ThemeComposeRule;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class DesktopSiteJsCardInfoSynchronizer implements CardInfoSynchronizer,
		ThemeCardListParserListener, CardInfoParserListener,
		ThemeComposeListParserListener {

	CardThemeManager cardThemeManager;

	CardManager cardManager;

	Map<Integer /* card ID */, CardInfo> parsedCardInfos;

	Map<Integer /* theme ID */, CardTheme> parsedCardThemes;

	List<ThemeComposeRule> parsedThemComposeRules;

	@Override
	public void synchronize(InputStream is) {

		reset();

		// parse data.
		doParseData(is);

		// process the data.
		doSynchonize();

	}

	protected void doSynchonize() {
		
		

	}

	protected void doParseData(InputStream is) {

		ThemeCardListParser themeCardListParser = new SimpleThemeCardListParser();
		SimpleCardInfoParser cardInfoParser = new SimpleCardInfoParser();
		SimpleThemeComposeListParser themeComposeListParser = new SimpleThemeComposeListParser();

		themeCardListParser.parse(is);
		themeComposeListParser.parse(is);
		cardInfoParser.parse(is);

	}

	protected void reset() {

		parsedCardInfos = new Hashtable<Integer, CardInfo>();
		parsedCardThemes = new Hashtable<Integer, CardTheme>();
		parsedThemComposeRules = new ArrayList<ThemeComposeRule>();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cppoon.tencent.magiccard.api.ThemeComposeListParserListener#
	 * themeComposeRuleParsed(com.cppoon.tencent.magiccard.api.ThemeComposeRule)
	 */
	@Override
	public void themeComposeRuleParsed(ThemeComposeRule rule) {
		parsedThemComposeRules.add(rule);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.api.CardInfoParserListener#cardInfoParsed
	 * (com.cppoon.tencent.magiccard.api.CardInfo)
	 */
	@Override
	public void cardInfoParsed(CardInfo ci) {
		parsedCardInfos.put(ci.getId(), ci);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cppoon.tencent.magiccard.api.ThemeCardListParserListener#cardThemeParsed
	 * (com.cppoon.tencent.magiccard.api.CardTheme)
	 */
	@Override
	public void cardThemeParsed(CardTheme ct) {
		parsedCardThemes.put(ct.getId(), ct);
	}

	public void setCardThemeManager(CardThemeManager cardThemeManager) {
		this.cardThemeManager = cardThemeManager;
	}

	public void setCardManager(CardManager cardManager) {
		this.cardManager = cardManager;
	}

}
