/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.vendor.qzapp.parser.CardRefineParser;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.SynthesizeCardInfo;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.impl.CardRefineParser20140328;
import com.google.common.io.Resources;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class CardRefineParserTest {

	CardRefineParser parser;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new CardRefineParser20140328();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		parser = null;
	}

	@Test
	public void testParse_OK() throws IOException {
		
		InputStream is = Resources.getResource(
				"com/cppoon/tencent/magiccard/api/test/wl_card_refine-steal.htm")
				.openStream();
		
		List<SynthesizeCardInfo> cards = parser.parse(is);
		
		//
		// THEN
		//
		assertNotNull("returned cards not null", cards);
		
		assertEquals("number of parsed cards", 3, cards.size());
		
		// check the cards
		SynthesizeCardInfo card = null;
		
		// card 1
		card = cards.get(0);
		assertSynthesizeCardInfo(card, 2370, "长痣绿蜓", 178, 40.0, 0, 
				"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_refine?sid=AZSJbIiayt5edHh3EYpub1iJ&tid=178&fuin=1272197632&steal=1&buy=1&id=2370&tt=1&s1=117440513&s2=117440513&s3=117440513&t1=117440514&t2=117440514&t3=117440514");
		
		// card 2
		card = cards.get(1);
		assertSynthesizeCardInfo(card, 2372, "碧伟蜓", 178, 40.0, 1, 
				"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_refine?sid=AZSJbIiayt5edHh3EYpub1iJ&tid=178&fuin=1272197632&steal=1&buy=1&id=2372&tt=1&s1=117440513&s2=117440513&s3=117440513&t1=117440514&t2=117440514&t3=117440514");

		// card 3
		card = cards.get(2);
		assertSynthesizeCardInfo(card, 2371, "鼎脈蜻蜓", 178, 40.0, 1, 
				"http://mfkp.qzapp.z.qq.com/qshow/cgi-bin/wl_card_refine?sid=AZSJbIiayt5edHh3EYpub1iJ&tid=178&fuin=1272197632&steal=1&buy=1&id=2371&tt=1&s1=117440513&s2=117440513&s3=117440513&t1=117440514&t2=117440514&t3=117440514");
		
		
	}
	
	protected void assertSynthesizeCardInfo(SynthesizeCardInfo actual,
			int expectedCardId, String expectedCardName, int expectedThemeId,
			double expectedCardPrice, int expectedCardsCollected,
			String expectedSynthesizeUrl) {
		
		assertEquals("card id", expectedCardId, actual.getCardId());
		assertEquals("card name", expectedCardName, actual.getCardName());
		assertEquals("card theme ID", expectedThemeId, actual.getCardThemeId());
		assertEquals("card price", expectedCardPrice, actual.getCardPrice(), 0.00);
		assertEquals("cards collected", expectedCardsCollected, actual.getCardsCollected());
		assertEquals("synthesize url", expectedSynthesizeUrl, actual.getSynthesizeUrl());
		
	}

}
