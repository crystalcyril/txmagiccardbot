/**
 * 
 */
package com.cppoon.tencent.magiccard.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import com.cppoon.tencent.magiccard.test.data.CardDataTestUtil;
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
	 * Check the complete theme suite for theme ID = 40, a 1-star theme.
	 * <p>
	 * 
	 * Theme Name: <strong>时尚男套装</strong>
	 * 
	 * @param theme
	 */
	protected void assertThemeID_40(CardTheme theme) {
		
		// [40,'时尚男套装',1,1251970023,0,1,100,200,0xffffff,'24','',[66,65,64,39,38,37,36,35,33,32,31,30,29],0,4,1309831119,0,0,1],
		CardDataTestUtil.assertCardTheme(theme, 40, "时尚男套装", 
				1, new Date(1251970023000L), 0, true, 100.00, 200, "", 0, 4, 
				new Date(1309831119000L), null);
		
		//
		// check top card
		List<Card> topCards = theme.getChildrenCards();
		Card topCard = null;
		
		assertEquals("number of top cards", 1, topCards.size());
		
		// check card details
		topCard = topCards.get(0);
		CardDataTestUtil.assertCard(topCard, 38, theme, "整套搭配", 150.0, 1, 0, true, 1, null, 0);

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
		// the children cards should be: ID = 37,35,29
		Iterator<Card> tier2CardsIter = tier2Cards.iterator();
		
		//
		// check 1st card in tier 2 cards: ID = 37
		//
		Card tier2Card = tier2CardsIter.next();
		// [37,40,'套白马甲',40,1,0,1,3,0,1027639,[0]],
		CardDataTestUtil.assertCard(tier2Card,
				37, theme, "套白马甲", 40.00, 1, 0, true, 3, null, 1027639);
		{
			// check the material cards of this card
			
			Card tier3Card;
			CardSynthesisFormula formula = tier2Card.getSynthesisFormula();
			
			assertNotNull("synthesis formula", formula);
			assertEquals("synthesis time", 3600, formula.getTime());
			assertTrue("formula's target card has same reference", formula.getTarget() == tier2Card);
			assertEquals("number of material cards", 3, formula.getMaterials().size());
			
			// the material cards are: ID = 39,36,32

			// check 1st card: card ID = 39
			tier3Card = formula.getMaterials().get(0);
			CardDataTestUtil.assertCard(tier3Card, 
					39, theme, "鸭舌帽子", 10.00, 0, 0, true, 3, null, 1022513);
			
			// check 2nd card: card ID = 36
			tier3Card = formula.getMaterials().get(1);
			CardDataTestUtil.assertCard(tier3Card, 
					36, theme, "魅力黑裤", 10.00, 0, 0, true, 3, null, 1026801);
			
			// check 2nd card: card ID = 32
			// [32,40,'黑白布鞋',10,0,0,1,1,0,0,[0]],
			tier3Card = formula.getMaterials().get(2);
			CardDataTestUtil.assertCard(tier3Card,
					32, theme, "黑白布鞋", 10.00, 0, 0, true, 1, null, 0);
			
		}
		//
		// check 2nd card
		//
		tier2Card = tier2CardsIter.next();
		// [35,40,'酷帅打扮',40,1,0,1,1,0,0,[0]],
		CardDataTestUtil.assertCard(tier2Card,
				35, theme, "酷帅打扮", 40.00, 1, 0, true, 1, null, 0);
		{
			// check the material cards of this card
			
			Card tier3Card;
			CardSynthesisFormula formula = tier2Card.getSynthesisFormula();
			
			assertNotNull("synthesis formula", formula);
			assertEquals("synthesis time", 3600, formula.getTime());
			assertTrue("formula's target card has same reference", formula.getTarget() == tier2Card);
			assertEquals("number of material cards", 3, formula.getMaterials().size());
			
			// the material cards are: ID = 31,30,33

			// check 1st card: card ID = 31
			// [31,40,'韩版长发',10,0,0,1,1,0,1025921,[0]],
			tier3Card = formula.getMaterials().get(0);
			CardDataTestUtil.assertCard(tier3Card, 
					31, theme, "韩版长发", 10.00, 0, 0, true, 1, null, 1025921);
			
			// check 2nd card: card ID = 30
			// [30,40,'搞怪笑脸',10,0,0,1,1,0,1011379,[0]],
			tier3Card = formula.getMaterials().get(1);
			CardDataTestUtil.assertCard(tier3Card, 
					30, theme, "搞怪笑脸", 10.00, 0, 0, true, 1, null, 1011379);
			
			// check 2nd card: card ID = 33
			// [33,40,'黑框大镜',10,0,0,1,1,0,1009328,[0]],
			tier3Card = formula.getMaterials().get(2);
			CardDataTestUtil.assertCard(tier3Card, 
					33, theme, "黑框大镜", 10.00, 0, 0, true, 1, null, 1009328);
			
		}
		
		
		//
		// check 3rd card.
		//
		tier2Card = tier2CardsIter.next();
		// [29,40,'城市街道',40,1,0,1,1,0,1027529,[0]],
		CardDataTestUtil.assertCard(tier2Card,
				29, theme, "城市街道", 40.00, 1, 0, true, 1, null, 1027529);
		{
			// check the material cards of this card
			
			Card tier3Card;
			CardSynthesisFormula formula = tier2Card.getSynthesisFormula();
			
			assertNotNull("synthesis formula", formula);
			assertEquals("synthesis time", 3600, formula.getTime());
			assertTrue("formula's target card has same reference", formula.getTarget() == tier2Card);
			assertEquals("number of material cards", 3, formula.getMaterials().size());
			
			// the material cards are: ID = 65,64,66

			// check 1st card: card ID = 65
			// [65,40,'黄色TAXI',10,0,0,1,1,0,0,[0]],
			tier3Card = formula.getMaterials().get(0);
			CardDataTestUtil.assertCard(tier3Card, 
					65, theme, "黄色TAXI", 10.00, 0, 0, true, 1, null, 0);
			
			// check 2nd card: card ID = 64
			// [64,40,'黑色提包',10,0,0,1,3,0,1016676,[0]],
			tier3Card = formula.getMaterials().get(1);
			CardDataTestUtil.assertCard(tier3Card, 
					64, theme, "黑色提包", 10.00, 0, 0, true, 3, null, 1016676);
			
			// check 2nd card: card ID = 66
			// [66,40,'银珠裤链',10,0,0,1,3,0,1009418,[0]],
			tier3Card = formula.getMaterials().get(2);
			CardDataTestUtil.assertCard(tier3Card, 
					66, theme, "银珠裤链", 10.00, 0, 0, true, 3, null, 1009418);
			
		}
		
	}
	
	/**
	 * Check the complete theme suite for theme ID = 45, a 1-star theme.
	 * <p>
	 * 
	 * Theme Name: <strong>时尚女套装</strong>
	 * 
	 * @param theme
	 */
	protected void assertThemeID_45(CardTheme theme) {

		// [45,'时尚女套装',1,1251970023,0,1,100,200,0x000000,'26','',[69,68,67,49,48,47,46,45,44,43,42,41,40],0,4,1309831120,0,0,1],
		CardDataTestUtil.assertCardTheme(theme, 45, "时尚女套装", 
				1, new Date(1251970023000L), 0, true, 100.00, 200, "", 0, 4, 
				new Date(1309831120000L), null);
		
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
		CardDataTestUtil.assertCard(tier2Card, 41, theme, "潮流打扮", 40.0, 1, 0, true, 1, null, 0);

		//
		// check 2nd card ID = 40
		//
		// [40,45,'彩虹短袖',40,1,0,1,3,0,1019458,[0]],
		tier2Card = tier2CardsIter.next();
		CardDataTestUtil.assertCard(tier2Card, 40, theme, "彩虹短袖", 40.0, 1, 0, true, 3, null, 1019458);
		
		//
		// check 3rd card: ID = 43
		//
		// [43,45,'别墅背景',40,1,0,1,1,0,1007188,[0]],
		tier2Card = tier2CardsIter.next();
		CardDataTestUtil.assertCard(tier2Card, 43, theme, "别墅背景", 40.0, 1, 0, true, 1, null, 1007188);
		
	}
	

	/**
	 * This theme has <strong>two</strong> top level cards instead of the 
	 * usual one.
	 * <p>
	 * 
	 * <ul>
	 * <li>Card Theme: <strong>斩仙</strong></li>
	 * <li>Theme ID: <strong>322</strong></li>
	 * </ul>
	 */
	@Test
	public void testSync_OK_TwoChildren2StarTheme() throws Exception {
		
		// read the card info with only one theme.
		InputStream is = Resources
				.getResource("com/cppoon/tencent/magiccard/api/test/card_info_v3.js")
				.openStream();
		
		//
		// WHEN
		//
		synchronizer.synchronize(is);
		
	}
	
	/**
	 * Just parse the whole javascript file.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSync_OK_CompleteCardInfoJs() throws Exception {

		// read the card info with only one theme.
		InputStream is = Resources
				.getResource("com/cppoon/tencent/magiccard/api/test/card_info_v3.js")
				.openStream();
		
		//
		// WHEN
		//
		synchronizer.synchronize(is);
		
	}
	
}
