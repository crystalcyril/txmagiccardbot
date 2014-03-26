/**
 * 
 */
package com.cppoon.tencent.magiccard.api.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardInfoSynchronizer;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.CardThemeManager;
import com.cppoon.tencent.magiccard.api.CardInfo;
import com.cppoon.tencent.magiccard.api.CardInfoParserListener;
import com.cppoon.tencent.magiccard.api.CardThemeInfo;
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
	
	private static Logger log = LoggerFactory.getLogger(DesktopSiteJsCardInfoSynchronizer.class);

	CardThemeManager cardThemeManager;

	CardManager cardManager;

	Map<Integer /* card ID */, CardInfo> parsedCardInfos;

	Map<Integer /* theme ID */, CardThemeInfo> parsedCardThemes;

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
		
		for (ThemeComposeRule rule : parsedThemComposeRules) {

			// find out the card
			
			com.cppoon.tencent.magiccard.CardTheme theme = cardThemeManager.findThemeById(rule.getThemeId());
			
			if (theme == null) {
				log.warn("card theme (id={}) cannot be found, card synthesis rule will be ignored", rule.getThemeId());
				continue;
			}
			
			com.cppoon.tencent.magiccard.Card targetCard = theme.getCardById(rule.getTargetCardId());
			ArrayList<Card> materials = new ArrayList<Card>();
			for (int cardId : rule.getChildrenCardIds()) {
				Card material = theme.getCardById(cardId);
				if (material == null) {
					// FIXME cyril do better handling.
					log.error("card id={} not found in card theme, rule is ignored", cardId);
					return;
				}
				materials.add(material);
			}
			
			// create a rule.
			targetCard.setSynthesisFormula(rule.getBuildTime(), materials.toArray(new Card[0]));
			
			
		}
		
	}

	private void processCards() {
		
//		for (CardInfo cardInfo : parsedCardInfos.values()) {
//			
//		}
		
	}

	protected void processThemes() {
		
		for (CardThemeInfo parsedCardTheme : parsedCardThemes.values()) {
			
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
				.enabled(parsedCardInfo.isEnabled())
				.itemNumber(parsedCardInfo.getItemNo())
				.name(parsedCardInfo.getName())
				.pickRate(parsedCardInfo.getPickRate())
				.price(parsedCardInfo.getPrice())
				.theme(cardTheme)
				.time(parsedCardInfo.getTime())
				.type(parsedCardInfo.getType())
				.version(parsedCardInfo.getVersion())
				.build();
			
		}
		
	}
	

	protected void doParseData(InputStream is) {
		
		JsV3CardInfoParser parser = new JsV3CardInfoParser();
		
		parser.setListener((ThemeCardListParserListener) this);
		parser.setListener((CardInfoParserListener) this);
		parser.setListener((ThemeComposeListParserListener) this);
		
		parser.parse(is);

//		ThemeCardListParser themeCardListParser = new SimpleThemeCardListParser();
//		themeCardListParser.setListener(this);
//		
//		SimpleCardInfoParser cardInfoParser = new SimpleCardInfoParser();
//		cardInfoParser.setListener(this);
//		
//		SimpleThemeComposeListParser themeComposeListParser = new SimpleThemeComposeListParser();
//		themeComposeListParser.setListener(this);
//
//		// star parsing
//		
//		themeComposeListParser.parse(is);
//		themeCardListParser.parse(is);
//		cardInfoParser.parse(is);

	}

	protected void reset() {

		parsedCardInfos = new Hashtable<Integer, CardInfo>();
		parsedCardThemes = new Hashtable<Integer, CardThemeInfo>();
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
	public void cardThemeParsed(CardThemeInfo ct) {
		parsedCardThemes.put(ct.getId(), ct);
	}

	public void setCardThemeManager(CardThemeManager cardThemeManager) {
		this.cardThemeManager = cardThemeManager;
	}

	public void setCardManager(CardManager cardManager) {
		this.cardManager = cardManager;
	}

}
