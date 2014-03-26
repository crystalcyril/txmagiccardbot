/**
 * 
 */
package com.cppoon.tencent.magiccard.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.Card;
import com.cppoon.tencent.magiccard.CardInfoSynchronizer;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.CardSynthesisFormula;
import com.cppoon.tencent.magiccard.CardTheme;
import com.cppoon.tencent.magiccard.CardThemeManager;
import com.cppoon.tencent.magiccard.api.impl.DesktopSiteJsCardInfoSynchronizer;
import com.cppoon.tencent.magiccard.impl.SimpleCardManager;
import com.cppoon.tencent.magiccard.impl.SimpleCardThemeManager;
import com.google.common.io.Resources;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class CardInfoSynchronizerTest {

	CardThemeManager cardThemeManager;
	
	CardManager cardManager;
	
	CardInfoSynchronizer synchronizer;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		cardThemeManager = new SimpleCardThemeManager();
		cardManager = new SimpleCardManager();
		
		DesktopSiteJsCardInfoSynchronizer synchronizer = new DesktopSiteJsCardInfoSynchronizer();
		synchronizer.setCardManager(cardManager);
		synchronizer.setCardThemeManager(cardThemeManager);
		this.synchronizer = synchronizer; 

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		cardThemeManager = null;
		synchronizer = null;
		
	}

	@Test
	public void testSync_OK_OneCardTheme() throws Exception {

		// read the card info with only one theme.
		InputStream is = Resources
				.getResource("com/cppoon/tencent/magiccard/api/test/card_info_v3-one_card_set-1_star.js")
				.openStream();
		
		//
		// WHEN
		//
		synchronizer.synchronize(is);
		
		//
		// THEN
		//

		Set<CardTheme> themes = cardThemeManager.getAllThemes();
		CardTheme theme = null;
		
		//
		// check theme
		assertEquals("numbers of themes", 1, themes.size());
		theme = themes.iterator().next();
		
		assertThemeID_40(theme);

	}

	
	/**
	 * Parse two themes.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSync_OK_TwoCardTheme() throws Exception {

		// read the card info with only one theme.
		InputStream is = Resources
				.getResource("com/cppoon/tencent/magiccard/api/test/card_info_v3-two_card_set-1_star.js")
				.openStream();
		
		//
		// WHEN
		//
		synchronizer.synchronize(is);
		
		//
		// THEN
		//

		Set<CardTheme> themes = cardThemeManager.getAllThemes();
		CardTheme theme = null;
		
		//
		// check theme
		assertEquals("numbers of themes", 2, themes.size());
		
		Iterator<CardTheme> cardThemeIter = themes.iterator();
		Set<Integer> checkedThemeIds = new HashSet<Integer>();
		for (int i = 0; i < 2; i++) {

			theme = cardThemeIter.next();
			
			if (theme.getId() == 40) {
				assertThemeID_40(theme);
			} else if (theme.getId() == 45) {
				assertThemeID_45(theme);
			} else {
				fail("unknown theme ID " + theme.getId());
			}
			
			checkedThemeIds.add(theme.getId());
			
		}
		
		assertEquals("number of unique themes checked", 2, checkedThemeIds.size());
		
	}

	
	/**
	 * 
	 * 
	 * @param theme
	 */
	protected void assertThemeID_40(CardTheme theme) {
		
		assertEquals("difficulty", 1, theme.getDifficulty());
		assertNull("expiry time", theme.getExpiryTime());
		assertEquals(40, 1, theme.getId());
		assertEquals("name", "时尚男套装", theme.getName());
		assertEquals("pick rate", 0, theme.getPickRate());
		assertEquals("prize", 100, theme.getCoins(), 0.0);
		assertEquals("publish time", new Date(1251970023000L), theme.getPublishTime());
		assertEquals("bonus experience", 200, theme.getExperience());
		assertEquals("time", new Date(1309831119000L), theme.getTime());
		assertEquals("type", 0, theme.getType());
		assertEquals("version", 4, theme.getVersion());
		assertTrue("enabled", theme.isEnabled());
		
		//
		// check top card
		List<Card> topCards = theme.getChildrenCards();
		Card topCard = null;
		
		assertEquals("number of top cards", 1, topCards.size());
		
		topCard = topCards.get(0);
		
		// check card details
		assertEquals("ID", 38, topCard.getId());
		assertEquals("item number", 0, topCard.getItemNo());
		assertEquals("name", "整套搭配", topCard.getName());
		assertEquals("pick rate", 0, topCard.getPickRate());
		assertEquals("price", 150.0, topCard.getPrice(), 0);
		assertEquals("theme", theme, topCard.getTheme());
		assertNull("time", topCard.getTime());
		assertEquals("type", 1, topCard.getType());
		assertEquals("version", 1, topCard.getVersion());

		//
		// check the children cards of the top cards, which should be three
		//
		CardSynthesisFormula topCardSynthesisFormula = topCard.getSynthesisFormula();
		assertNotNull("top card synthesis formula", topCardSynthesisFormula);
		// check formula
		assertEquals("target card", topCard, topCardSynthesisFormula.getTarget());
		assertEquals("synthesis time", 14400, topCardSynthesisFormula.getTime());
		// check the children
		List<Card> tier2Cards = topCardSynthesisFormula.getMaterials();
		Card tier2Card;
		// the children cards should be: ID = 37,35,29
		Iterator<Card> tier2CardsIter = tier2Cards.iterator();
		//
		// check 1st card in tier 2 cards
		//
		tier2Card = tier2CardsIter.next();
		assertEquals("ID", 37, tier2Card.getId());
		assertEquals("item number", 1027639, tier2Card.getItemNo());
		assertEquals("ID", "套白马甲", tier2Card.getName());
		assertEquals("pick rate", 0, tier2Card.getPickRate());
		assertEquals("price", 40.0, tier2Card.getPrice(), 0.00);
		assertEquals("theme", theme, tier2Card.getTheme());
		assertNull("time", tier2Card.getTime());
		assertEquals("type", 1, tier2Card.getType());
		assertEquals("version", 3, tier2Card.getVersion());
		//
		// check 2nd card
		//
		tier2Card = tier2CardsIter.next();
		assertEquals("ID", 35, tier2Card.getId());
		assertEquals("item number", 0, tier2Card.getItemNo());
		assertEquals("ID", "酷帅打扮", tier2Card.getName());
		assertEquals("pick rate", 0, tier2Card.getPickRate());
		assertEquals("price", 40.0, tier2Card.getPrice(), 0.00);
		assertEquals("theme", theme, tier2Card.getTheme());
		assertNull("time", tier2Card.getTime());
		assertEquals("type", 1, tier2Card.getType());
		assertEquals("version", 1, tier2Card.getVersion());
		//
		// check 3rd card.
		//
		tier2Card = tier2CardsIter.next();
		assertEquals("ID", 29, tier2Card.getId());
		assertEquals("item number", 1027529, tier2Card.getItemNo());
		assertEquals("ID", "城市街道", tier2Card.getName());
		assertEquals("pick rate", 0, tier2Card.getPickRate());
		assertEquals("price", 40.0, tier2Card.getPrice(), 0.00);
		assertEquals("theme", theme, tier2Card.getTheme());
		assertNull("time", tier2Card.getTime());
		assertEquals("type", 1, tier2Card.getType());
		assertEquals("version", 1, tier2Card.getVersion());
		
	}
	
	/**
	 * 
	 * 
	 * @param theme
	 */
	protected void assertThemeID_45(CardTheme theme) {
		
		assertEquals("difficulty", 1, theme.getDifficulty());
		assertNull("expiry time", theme.getExpiryTime());
		assertEquals("id", 45, 1, theme.getId());
		assertEquals("name", "时尚女套装", theme.getName());
		assertEquals("pick rate", 0, theme.getPickRate());
		assertEquals("coins", 100, theme.getCoins(), 0.0);
		assertEquals("publish time", new Date(1251970023000L), theme.getPublishTime());
		assertEquals("bonus experience", 200, theme.getExperience());
		assertEquals("time", new Date(1309831120000L), theme.getTime());
		assertEquals("type", 0, theme.getType());
		assertEquals("version", 4, theme.getVersion());
		assertTrue("enabled", theme.isEnabled());
		
		//
		// check top card
		List<Card> topCards = theme.getChildrenCards();
		Card topCard = null;
		
		assertEquals("number of top cards", 1, topCards.size());
		
		topCard = topCards.get(0);
		
		// check card details
		assertEquals("ID", 42, topCard.getId());
		assertEquals("item number", 0, topCard.getItemNo());
		assertEquals("name", "整套搭配", topCard.getName());
		assertEquals("pick rate", 0, topCard.getPickRate());
		assertEquals("price", 150.0, topCard.getPrice(), 0);
		assertEquals("theme", theme, topCard.getTheme());
		assertNull("time", topCard.getTime());
		assertEquals("type", 1, topCard.getType());
		assertEquals("version", 1, topCard.getVersion());

		//
		// check the children cards of the top cards, which should be three
		//
		CardSynthesisFormula topCardSynthesisFormula = topCard.getSynthesisFormula();
		assertNotNull("top card synthesis formula", topCardSynthesisFormula);
		// check formula
		assertEquals("target card", topCard, topCardSynthesisFormula.getTarget());
		assertEquals("synthesis time", 14400, topCardSynthesisFormula.getTime());
		// check the children
		List<Card> tier2Cards = topCardSynthesisFormula.getMaterials();
		Card tier2Card;
		// the children cards should be: ID = 41,40,43
		Iterator<Card> tier2CardsIter = tier2Cards.iterator();
		//
		// check 1st card in tier 2 cards: ID = 41
		//
		tier2Card = tier2CardsIter.next();
		assertEquals("ID", 41, tier2Card.getId());
		assertEquals("item number", 0, tier2Card.getItemNo());
		assertEquals("name", "潮流打扮", tier2Card.getName());
		assertEquals("pick rate", 0, tier2Card.getPickRate());
		assertEquals("price", 40.0, tier2Card.getPrice(), 0.00);
		assertEquals("theme", theme, tier2Card.getTheme());
		assertNull("time", tier2Card.getTime());
		assertEquals("type", 1, tier2Card.getType());
		assertEquals("version", 1, tier2Card.getVersion());
		//
		// check 2nd card ID = 40
		//
		tier2Card = tier2CardsIter.next();
		assertEquals("ID", 40, tier2Card.getId());
		assertEquals("item number", 1019458, tier2Card.getItemNo());
		assertEquals("ID", "彩虹短袖", tier2Card.getName());
		assertEquals("pick rate", 0, tier2Card.getPickRate());
		assertEquals("price", 40.0, tier2Card.getPrice(), 0.00);
		assertEquals("theme", theme, tier2Card.getTheme());
		assertNull("time", tier2Card.getTime());
		assertEquals("type", 1, tier2Card.getType());
		assertEquals("version", 3, tier2Card.getVersion());
		//
		// check 3rd card: ID = 43
		//
		tier2Card = tier2CardsIter.next();
		assertEquals("ID", 43, tier2Card.getId());
		assertEquals("item number", 1007188, tier2Card.getItemNo());
		assertEquals("name", "别墅背景", tier2Card.getName());
		assertEquals("pick rate", 0, tier2Card.getPickRate());
		assertEquals("price", 40.0, tier2Card.getPrice(), 0.00);
		assertEquals("theme", theme, tier2Card.getTheme());
		assertNull("time", tier2Card.getTime());
		assertEquals("type", 1, tier2Card.getType());
		assertEquals("version", 1, tier2Card.getVersion());
		
	}
	
	
}
