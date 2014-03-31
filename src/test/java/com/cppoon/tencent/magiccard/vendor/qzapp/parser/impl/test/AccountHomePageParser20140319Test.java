/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.api.StoveStatus;
import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.AccountHomePageParser;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.StoveInfo;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.AccountHomePageParser20140318;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class AccountHomePageParser20140319Test {

	AccountHomePageParser parser = new AccountHomePageParser20140318();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParse_OK() throws Exception {
		
		String html = ParserTestUtil
				.readResourceAsString("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/mfkp_mainpage-20140319.htm");
		
		AccountOverview acOverview = parser.parse(html);
		
		//
		// Assertion
		//
		assertNotNull("parsed page should not be null", acOverview);
		
		
		assertEquals("player level", 1, acOverview.getPlayerLevel());
		assertEquals("coin", 13500, acOverview.getCoins(), 0);
		assertEquals("cards in deck", 16, acOverview.getCardsInDeck());
		assertEquals("available slots in exchange card box", 0, acOverview.getCardsInCardExchangeBox());
		assertEquals("total slots in exchange card box", 10, acOverview.getCardExchangeBoxSize());
		
		// stove related checking
		assertEquals("stove count", 3, acOverview.getStoveCount());
		assertEquals("free stove count", 1, acOverview.getFreeStoveCount());
		// check the slots
		assertStoveInfo(acOverview.getStoves().get(0), "长痣绿蜓", "蜻蜓款款飞", -1, 40, 1, StoveStatus.SYNTHESIZED, 0);
		assertStoveInfo(acOverview.getStoves().get(1), "鼎脈蜻蜓", "蜻蜓款款飞", -1, 40, 0, StoveStatus.SYNTHESIZED, 0);
		assertStoveInfo(acOverview.getStoves().get(2), null, null, -1, 0, 2, StoveStatus.IDLE, 0);
				
		
		// stolen stove checking
		assertEquals("free steal stove count", 1, acOverview.getStealStoveAvailableCount());
		assertEquals("total steal stove count", 1, acOverview.getStealStoveSize());
		
	}
	
	
	@Test
	public void testParse_OK_HasCancellableCards() throws Exception {
		
		String html = ParserTestUtil
				.readResourceAsString("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/mfkp_mainpage-has_cancellable_cards-20140319.htm");
		
		AccountOverview acOverview = parser.parse(html);
		
		//
		// Assertion
		//
		assertNotNull("parsed page should not be null", acOverview);
		
		
		assertEquals("player level", 65, acOverview.getPlayerLevel());
		assertEquals("coin", 781616, acOverview.getCoins(), 0);
		assertEquals("cards in deck", 8, acOverview.getCardsInDeck());
		assertEquals("used slots in exchange card box", 16, acOverview.getCardsInCardExchangeBox());
		assertEquals("total slots in exchange card box", 18, acOverview.getCardExchangeBoxSize());
		
		// stove related checking
		assertEquals("stove count", 5, acOverview.getStoveCount());
		assertEquals("free stove count", 0, acOverview.getFreeStoveCount());
		// check the slots
		assertStoveInfo(acOverview.getStoves().get(0), "洛天", "斩仙", 4862, 540, 2, StoveStatus.SYNTHESIZING, 8445);
		assertStoveInfo(acOverview.getStoves().get(1), "云华女侠", "斩仙", 4861, 150, 0, StoveStatus.SYNTHESIZING, 6168);
		assertStoveInfo(acOverview.getStoves().get(2), "鼎脈蜻蜓", "蜻蜓款款飞", 2371, 40, 4, StoveStatus.PEND_FOR_SYNTHESIS, 3600);
		assertStoveInfo(acOverview.getStoves().get(3), "碧伟蜓", "蜻蜓款款飞", 0, 40, 3, StoveStatus.SYNTHESIZED, 0);
		assertStoveInfo(acOverview.getStoves().get(4), "长痣绿蜓", "蜻蜓款款飞", 2370, 40, 3, StoveStatus.SYNTHESIZING, 2726);
		
		// stolen stove checking
		assertEquals("free steal stove count", 1, acOverview.getStealStoveAvailableCount());
		assertEquals("total steal stove count", 1, acOverview.getStealStoveSize());
		// check the slot
		
	}

	
	
	protected void assertStoveInfo(StoveInfo stove, String expectedCardName,
			String expectedThemeName, int expectedCardId,
			double expectedCardPrice, 
			int expectedSlotId, StoveStatus expectedStoveStatus,
			long expectedSynthesisRemainingTime) {
		
		assertEquals("card name", expectedCardName, stove.getCardName());
		assertEquals("card theme", expectedThemeName, stove.getCardThemeName());
		assertEquals("card id", expectedCardId, stove.getCardId());
		assertEquals("card price", expectedCardPrice, stove.getCardPrice(), 0.00);
		assertEquals("stove slot id", expectedSlotId, stove.getSlotId());
		assertEquals("stove status", expectedStoveStatus, stove.getStatus());
		assertEquals("remaining synthesis time", expectedSynthesisRemainingTime, stove.getSynthesisRemainingTime());
		
	}

}
