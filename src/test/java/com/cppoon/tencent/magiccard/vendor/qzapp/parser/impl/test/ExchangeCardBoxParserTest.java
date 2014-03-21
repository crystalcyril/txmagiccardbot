/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.test;

import static org.junit.Assert.*;

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
		assertEquals("slots count", 6, slots.size());
		
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
				{ "斩仙", "云玉仙", 10.0, 4850, 8, 
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_sell?sid=AVMIxjV6_RFGeQ58M3VuPbVD&card=4850&slot=8",
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AVMIxjV6_RFGeQ58M3VuPbVD&type=0&slot=8&card=4850" },  
				{ "非洲风光", "尼罗河", 420.0, 4531, 4,
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_sell?sid=AVMIxjV6_RFGeQ58M3VuPbVD&card=4531&slot=4",
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AVMIxjV6_RFGeQ58M3VuPbVD&type=0&slot=4&card=4531" },  
				{ "陕北民窑", "西安钟楼", 480.0, 2795, 1,
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_sell?sid=AVMIxjV6_RFGeQ58M3VuPbVD&card=2795&slot=1",
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AVMIxjV6_RFGeQ58M3VuPbVD&type=0&slot=1&card=2795" },  
				{ "道具卡", "百变卡", 9999.0, 1162, 0,
					null,
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AVMIxjV6_RFGeQ58M3VuPbVD&type=0&slot=0&card=1162" },  
				{ "道具卡", "百变卡", 9999.0, 1162, 2,
					null,
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AVMIxjV6_RFGeQ58M3VuPbVD&type=0&slot=2&card=1162" },  
				{ "道具卡", "百变卡", 9999.0, 1162, 16,
					null, 
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AVMIxjV6_RFGeQ58M3VuPbVD&type=0&slot=16&card=1162" } 
		};

		for (int i = 0; i < slots.size(); i++) {
			
			Object[] expectedCardData = expected[i];
			ExchangeBoxSlot actualSlot = slots.get(i);
			
			assertEquals("card theme name", (String)expectedCardData[0], actualSlot.getCardThemeName());
			assertEquals("card name", (String)expectedCardData[1], actualSlot.getCardName());
			assertEquals("card price", (double)(Double)expectedCardData[2], actualSlot.getCardPrice(), 0.00);
			assertEquals("card ID", (int)(Integer)expectedCardData[3], actualSlot.getCardId());
			assertEquals("slot ID", (int)(Integer)expectedCardData[4], actualSlot.getSlotId());
			
			// sell URL
			if (expectedCardData[5] == null) {
				assertNull("sell URL", actualSlot.getSellUrl());
			} else {
				assertEquals("sell URL", expectedCardData[5], actualSlot.getSellUrl());
			}
			
			// put to safe box URL
			if (expectedCardData[6] == null) {
				assertNull("sell URL", actualSlot.getPutToSafeBoxUrl());
			} else {
				assertEquals("sell URL", expectedCardData[6], actualSlot.getPutToSafeBoxUrl());
			}
		}
		

	}

}
