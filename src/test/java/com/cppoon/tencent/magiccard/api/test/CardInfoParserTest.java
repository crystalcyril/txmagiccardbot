/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Test;

import com.cppoon.tencent.magiccard.CardInfo;
import com.cppoon.tencent.magiccard.api.CardInfoParser;
import com.cppoon.tencent.magiccard.api.impl.SimpleCardInfoParser;
import com.cppoon.tencent.magiccard.util.IOUtil;
import com.google.common.io.Resources;

/**
 * 
 * 
 * @author Cyril
 */
public class CardInfoParserTest {

	@Test
	public void test() {

		CardInfoParser parser = new SimpleCardInfoParser();

		// configure listener
		TestCardInfoParserListener listener = new TestCardInfoParserListener();
		parser.setListener(listener);

		// parse
		InputStream is = null;
		try {
			is = Resources.getResource(
					"com/cppoon/tencent/magiccard/api/test/card_info_v3.js")
					.openStream();
			parser.parse(is);
		} catch (IOException e) {
		} finally {
			IOUtil.close(is);
		}

		//
		// assertion
		//

		// check number of parsed cards.
		assertEquals("number of parsed cards", 4653, listener.getCardCount());

		// check the first card.
		CardInfo cardInfo = listener.getCardById(29);
		assertEquals("card ID", 29, cardInfo.getId());
		assertEquals("theme ID", 40, cardInfo.getThemeId());
		assertEquals("card name", "城市街道", cardInfo.getName());
		assertEquals("price", 40, cardInfo.getPrice());
		assertEquals("type", 1, cardInfo.getType());
		assertEquals("pick rate", 0, cardInfo.getPickRate());
		assertTrue("enabled", cardInfo.isEnabled());
		assertEquals("version", 1, cardInfo.getVersion());
		assertEquals("time", null, cardInfo.getTime());
		assertEquals("item no", 1027529, cardInfo.getItemNo());

		
		// check the latest card at the time of coding
		cardInfo = listener.getCardById(4740);
		assertEquals("card ID", 4740, cardInfo.getId());
		assertEquals("theme ID", 313, cardInfo.getThemeId());
		assertEquals("card name", "生肖鼠", cardInfo.getName());
		assertEquals("price", 214, cardInfo.getPrice());
		assertEquals("type", 0, cardInfo.getType());
		assertEquals("pick rate", 0, cardInfo.getPickRate());
		assertTrue("enabled", cardInfo.isEnabled());
		assertEquals("version", 0, cardInfo.getVersion());
		assertEquals("time", new Date(1388493668000L), cardInfo.getTime());
		assertEquals("item no", 0, cardInfo.getItemNo());
		
		
	}

}
