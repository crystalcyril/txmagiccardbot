/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardBoxInfo;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.ExchangeBoxSlot;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.SafeBoxParser20140320;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SafeBoxParser20140320Test {

	SafeBoxParser20140320 parser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		parser = new SafeBoxParser20140320();

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
	public void testParse_OK_SinglePageOnly() throws Exception {

		String html = ParserTestUtil
				.readResourceAsString("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/mfkp_xchg_safebox-single_page-20140321.htm");

		//
		// WHEN
		//
		CardBoxInfo info = parser.parse(html);

		//
		// THEN
		//
		assertNotNull("return value from parse()", info);

		assertEquals(
				"exchange box URL",
				"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=AQ1xjDbBy3bqoQUIxKw8ZFl7",
				info.getExchangeBoxUrl());

		List<ExchangeBoxSlot> slots = info.getSlots();
		
		
		assertNotNull("slots", slots);

		// number of items in slots
		assertEquals("slots count", 3, slots.size());

		// card theme name
		// card name
		// card price
		// card ID
		// slot ID
		Object[][] expected = {
				{ "蜻蜓款款飞", "蜻蜓款款飞", 150.0, 2373, 2, 
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AQ1xjDbBy3bqoQUIxKw8ZFl7&type=1&slot=2&card=2373&p=1" },
				{ "蜻蜓款款飞", "褐斑蜻蜓", 10.0, 2368, 1, 
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AQ1xjDbBy3bqoQUIxKw8ZFl7&type=1&slot=1&card=2368&p=1" },
				{ "蜻蜓款款飞", "大团扇春蜓", 10.0, 2369, 0, 
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AQ1xjDbBy3bqoQUIxKw8ZFl7&type=1&slot=0&card=2369&p=1" },
		};
		
		//
		// verify slots
		//
		for (int i = 0; i < slots.size(); i++) {
			
			Object[] expectedCardData = expected[i];
			ExchangeBoxSlot actualSlot = slots.get(i);
			
			assertEquals("card theme name", (String)expectedCardData[0], actualSlot.getCardThemeName());
			assertEquals("card name", (String)expectedCardData[1], actualSlot.getCardName());
			assertEquals("card price", (double)(Double)expectedCardData[2], actualSlot.getCardPrice(), 0.00);
			assertEquals("card ID", (int)(Integer)expectedCardData[3], actualSlot.getCardId());
			assertEquals("slot ID", (int)(Integer)expectedCardData[4], actualSlot.getSlotId());
			
			// put to safe box URL
			if (expectedCardData[5] == null) {
				assertNull("put to exchange box URL", actualSlot.getPutToExchangeBoxUrl());
			} else {
				assertEquals("put to exchange box URL", expectedCardData[5], actualSlot.getPutToExchangeBoxUrl());
			}
		}
		
		//
		// verify pages
		//
		assertEquals("number of pages", 1, info.getPageLinks().size());
		assertEquals("current page", 0, info.getCurrentPage());	// 0-based
		
		List<CardBoxInfo.PageLink> links = info.getPageLinks();
		
		assertEquals("number of page links", 1, links.size());
		CardBoxInfo.PageLink plink = links.get(0);
		
		assertEquals("page link number", 0, plink.getPageNumber());
		assertTrue("page link number", plink.isCurrent());
		
	}

	/**
	 * 
	 */
	@Test
	public void testParse_OK_Page1of3() throws Exception {

		String html = ParserTestUtil
				.readResourceAsString("com/cppoon/tencent/magiccard/vendor/qzapp/parser/impl/test/mfkp_xchg_safebox-1of3page-20140321.htm");

		//
		// WHEN
		//
		CardBoxInfo info = parser.parse(html);

		//
		// THEN
		//
		assertNotNull("return value from parse()", info);

		assertEquals(
				"exchange box URL",
				"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=AVMIxjV6_RFGeQ58M3VuPbVD",
				info.getExchangeBoxUrl());

		List<ExchangeBoxSlot> slots = info.getSlots();
		
		
		assertNotNull("slots", slots);

		// number of items in slots
		assertEquals("slots count", 20, slots.size());

		// card theme name
		// card name
		// card price
		// card ID
		// slot ID
		Object[][] expected = {
				{ "斩仙", "云玉仙", 10.0, 4850, 33, 
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AVMIxjV6_RFGeQ58M3VuPbVD&type=1&slot=33&card=4850&p=1" },
				{ "斩仙", "云玉仙", 10.0, 4850, 19, 
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AVMIxjV6_RFGeQ58M3VuPbVD&type=1&slot=19&card=4850&p=1" },
				{ "斩仙", "云玉仙", 10.0, 4850, 17, 
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AVMIxjV6_RFGeQ58M3VuPbVD&type=1&slot=17&card=4850&p=1" },
		};
		
		Object[][] expected_18to20 = {
				{ "彩艺剪纸", "双龙戏珠", 610.0, 4415, 8, 
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AVMIxjV6_RFGeQ58M3VuPbVD&type=1&slot=8&card=4415&p=1" },
				{ "彩艺剪纸", "三阳开泰", 320.0, 4410, 27, 
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AVMIxjV6_RFGeQ58M3VuPbVD&type=1&slot=27&card=4410&p=1" },
				{ "彩艺剪纸", "麟吐五书", 320.0, 4412, 25, 
					"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_move?sid=AVMIxjV6_RFGeQ58M3VuPbVD&type=1&slot=25&card=4412&p=1" },
		};
		
		//
		// verify slots
		//
		for (int i = 0; i < expected.length; i++) {
			
			Object[] expectedCardData = expected[i];
			ExchangeBoxSlot actualSlot = slots.get(i);
			
			assertEquals("card theme name", (String)expectedCardData[0], actualSlot.getCardThemeName());
			assertEquals("card name", (String)expectedCardData[1], actualSlot.getCardName());
			assertEquals("card price", (double)(Double)expectedCardData[2], actualSlot.getCardPrice(), 0.00);
			assertEquals("card ID", (int)(Integer)expectedCardData[3], actualSlot.getCardId());
			assertEquals("slot ID", (int)(Integer)expectedCardData[4], actualSlot.getSlotId());
			
			// put to safe box URL
			if (expectedCardData[5] == null) {
				assertNull("put to exchange box URL", actualSlot.getPutToExchangeBoxUrl());
			} else {
				assertEquals("put to exchange box URL", expectedCardData[5], actualSlot.getPutToExchangeBoxUrl());
			}
		}
		for (int i = 0; i < expected_18to20.length; i++) {
			
			Object[] expectedCardData = expected_18to20[i];
			ExchangeBoxSlot actualSlot = slots.get(i + 17);
			
			assertEquals("card theme name", (String)expectedCardData[0], actualSlot.getCardThemeName());
			assertEquals("card name", (String)expectedCardData[1], actualSlot.getCardName());
			assertEquals("card price", (double)(Double)expectedCardData[2], actualSlot.getCardPrice(), 0.00);
			assertEquals("card ID", (int)(Integer)expectedCardData[3], actualSlot.getCardId());
			assertEquals("slot ID", (int)(Integer)expectedCardData[4], actualSlot.getSlotId());
			
			// put to safe box URL
			if (expectedCardData[5] == null) {
				assertNull("put to exchange box URL", actualSlot.getPutToExchangeBoxUrl());
			} else {
				assertEquals("put to exchange box URL", expectedCardData[5], actualSlot.getPutToExchangeBoxUrl());
			}
		}
		
		//
		// verify pages
		//
		assertEquals("number of pages", 3, info.getPageLinks().size());
		assertEquals("current page", 0, info.getCurrentPage());	// 0-based
		
		List<CardBoxInfo.PageLink> links = info.getPageLinks();
		CardBoxInfo.PageLink plink;
		
		assertEquals("number of page links", 3, links.size());
		
		plink = links.get(0);
		assertEquals("page link number", 0, plink.getPageNumber());
		assertTrue("page link number", plink.isCurrent());
		assertNull("page URL", plink.getUrl());
		
		plink = links.get(1);
		assertEquals("page link number", 1, plink.getPageNumber());
		assertFalse("page link number", plink.isCurrent());
		assertEquals("page URL", "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=AVMIxjV6_RFGeQ58M3VuPbVD&t=1&n=2", plink.getUrl());
		
		plink = links.get(2);
		assertEquals("page link number", 2, plink.getPageNumber());
		assertFalse("page link number", plink.isCurrent());
		assertEquals("page URL", "http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_box?sid=AVMIxjV6_RFGeQ58M3VuPbVD&t=1&n=3", plink.getUrl());
		
	}
}
