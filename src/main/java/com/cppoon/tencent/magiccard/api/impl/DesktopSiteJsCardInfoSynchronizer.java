/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.cppoon.tencent.magiccard.Card;
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
		
		// process themes first
		processThemes();
		
		// then cards
		processCards();
		
		// then card synthesis rules.
		processCardSynthesisRules();
		
		
	}
	
	private void processCardSynthesisRules() {
		
	}

	private void processCards() {
		
		for (CardInfo cardInfo : parsedCardInfos.values()) {
			
			
			
		}
		
	}

	protected void processThemes() {
		
		for (CardTheme parsedCardTheme : parsedCardThemes.values()) {
			
			com.cppoon.tencent.magiccard.CardTheme cardTheme = cardThemeManager.findThemeById(parsedCardTheme.getId());
			if (cardTheme == null) {
				// it is a new card theme
				cardTheme = cardThemeManager.createBuilder()
					.difficulty(parsedCardTheme.getDifficulty())
					.experience(parsedCardTheme.getExperience())
					.expiryTime(parsedCardTheme.getExpiryTime())
					.id(parsedCardTheme.getId())
					.name(parsedCardTheme.getName())
					.coins(parsedCardTheme.getCoins())
					.publishTime(parsedCardTheme.getPublishTime())
					.time(parsedCardTheme.getTime())
					.type(parsedCardTheme.getType())
					.version(parsedCardTheme.getVersion()).build();
				
				// we need to build the complete theme hierarchy now for this 
				// new theme.
				buildNewTheme(cardTheme);
				
				cardThemeManager.registerTheme(cardTheme);
				
			}
			
		}
		
	}
	
	protected void buildNewTheme(com.cppoon.tencent.magiccard.CardTheme cardTheme) {
		
		for (CardInfo parsedCardInfo : parsedCardInfos.values()) {
			
			// only process cards with matching theme ID.
			if (parsedCardInfo.getThemeId() != cardTheme.getId()) {
				continue;
			}
			
			Card card = cardManager.createBuilder()
				.id(parsedCardInfo.getId())
				.name(parsedCardInfo.getName())
				.price(parsedCardInfo.getPrice())
				.theme(cardTheme)
				.time(cardTheme.getTime())
				.type(cardTheme.getType())
				.version(cardTheme.getVersion())
				.build();
			
		}
		
	}
	

	protected void doParseData(InputStream is) {

		ThemeCardListParser themeCardListParser = new SimpleThemeCardListParser();
		themeCardListParser.setListener(this);
		
		SimpleCardInfoParser cardInfoParser = new SimpleCardInfoParser();
		cardInfoParser.setListener(this);
		
		SimpleThemeComposeListParser themeComposeListParser = new SimpleThemeComposeListParser();
		themeComposeListParser.setListener(this);

		// star parsing
		
		themeComposeListParser.parse(is);
		themeCardListParser.parse(is);
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
