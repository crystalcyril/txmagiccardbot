/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import static org.junit.Assert.*;

import java.util.Date;

import com.cppoon.tencent.magiccard.api.CardInfo;

/**
 * 
 * @author Cyril
 * @since 0.1.0
 */
public final class CardInfoParserTestUtil {

	private CardInfoParserTestUtil() {
		super();
	}

	/**
	 * Test if the specified card contains valid data against the specified 
	 * card ID. Only a limited set of data is supported.
	 * 
	 * @param cardInfo
	 * @param cardId
	 */
	public static final void assertCardInfoIsCorrect(final CardInfo cardInfo,
			int cardId) {		
		
		if (cardId == 29) {
			
			assertEquals("card ID", 29, cardInfo.getId());
			assertEquals("theme ID", 40, cardInfo.getThemeId());
			assertEquals("card name", "城市街道", cardInfo.getName());
			assertEquals("price", 40.00D, cardInfo.getPrice(), 0.00);
			assertEquals("type", 1, cardInfo.getType());
			assertEquals("pick rate", 0, cardInfo.getPickRate());
			assertTrue("enabled", cardInfo.isEnabled());
			assertEquals("version", 1, cardInfo.getVersion());
			assertEquals("time", null, cardInfo.getTime());
			assertEquals("item no", 1027529, cardInfo.getItemNo());
			
		} else if (cardId == 4740) {
		
			// check the latest card at the time of coding
			assertEquals("card ID", 4740, cardInfo.getId());
			assertEquals("theme ID", 313, cardInfo.getThemeId());
			assertEquals("card name", "生肖鼠", cardInfo.getName());
			assertEquals("price", 214.00D, cardInfo.getPrice(), 0.00D);
			assertEquals("type", 0, cardInfo.getType());
			assertEquals("pick rate", 0, cardInfo.getPickRate());
			assertTrue("enabled", cardInfo.isEnabled());
			assertEquals("version", 0, cardInfo.getVersion());
			assertEquals("time", new Date(1388493668000L), cardInfo.getTime());
			assertEquals("item no", 0, cardInfo.getItemNo());
			
		} else {
			
			fail("card ID " + cardId + " not supported for testing");
			
		}
		
	}
	
}
