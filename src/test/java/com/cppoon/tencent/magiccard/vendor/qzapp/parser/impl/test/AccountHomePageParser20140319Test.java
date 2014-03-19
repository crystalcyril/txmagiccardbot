/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.AccountHomePageParser;
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
	public void test() throws Exception {
		
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
		
		// stove related checks
		assertEquals("stove count", 3, acOverview.getStoveCount());
		assertEquals("free stove count", 1, acOverview.getFreeStoveCount());
		//
		assertEquals("free steal stove count", 1, acOverview.getStealStoveAvailableCount());
		assertEquals("total steal stove count", 1, acOverview.getStealStoveSize());		
		
	}

}
