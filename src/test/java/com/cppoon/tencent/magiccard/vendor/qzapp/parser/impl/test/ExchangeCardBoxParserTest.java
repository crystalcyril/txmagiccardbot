/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardBoxInfo;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.ExchangeBoxSlot;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.ExchangeCardBoxParser20140320;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class ExchangeCardBoxParserTest {

	ExchangeCardBoxParser20140320 parser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		parser = new ExchangeCardBoxParser20140320();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		parser = null;
	}

	/**
	 * 
	 */
	@Test
	public void testParse_OK_EmptySlots() throws Exception {

		String html = ParserTestUtil
				.readResourceAsString("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/mfkp_xchg_cardbox-empty-20140321.htm");

		//
		// WHEN
		//
		CardBoxInfo info = parser.parse(html);

		//
		// THEN
		//
		assertNotNull("return value from parse()", info);

		assertEquals(
				"return value from parse()",
				"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=AQ1xjDbBy3bqoQUIxKw8ZFl7&t=1",
				info.getSafeBoxUrl());

		List<ExchangeBoxSlot> slots = info.getSlots();
		assertNotNull("slots", slots);

		assertTrue("slots should be empty", slots.isEmpty());

	}

	@Test
	public void testParse_OK_SlotsNotFull() throws Exception {

		String html = ParserTestUtil
				.readResourceAsString("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/mfkp_xchg_cardbox-notfull-20140321.htm");

		//
		// WHEN
		//
		CardBoxInfo info = parser.parse(html);

		//
		// THEN
		//
		assertNotNull("return value from parse()", info);

		assertEquals(
				"return value from parse()",
				"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=AVMIxjV6_RFGeQ58M3VuPbVD&t=1",
				info.getSafeBoxUrl());

		List<ExchangeBoxSlot> slots = info.getSlots();
		assertNotNull("slots", slots);

		// number of items in slots
		assertEquals("slots count", 16, slots.size());
		
		// check slots
		ExchangeBoxSlot slot = null;
		Iterator<ExchangeBoxSlot> iter = slots.iterator();
		
		// slot
		slot = iter.next();
		
		// card theme name
		// card name
		// card price
		// card ID
		// slot ID
		Object[][] expected = {
				{ "斩仙", "云玉仙", 10, 4850, 8 }  
		};

		for (int i = 0; i < slots.size(); i++) {
			Object[] expectedCardData = expected[i];
			ExchangeBoxSlot actualSlot = slots.get(i);
			
			assertEquals("card theme name", (String)expectedCardData[0], actualSlot.getCardThemeName());
			assertEquals("card name", (String)expectedCardData[1], actualSlot.getCardName());
			assertEquals("card price", (Double)expectedCardData[2], actualSlot.getCardPrice(), 2);
			assertEquals("card ID", (int)(Integer)expectedCardData[3], actualSlot.getCardId());
			assertEquals("slot ID", (int)(Integer)expectedCardData[4], actualSlot.getSlotId());
		}
		

	}

}
