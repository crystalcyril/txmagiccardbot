/**
 * 
 */
package com.cppoon.tencent.magiccard.api.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.api.CardInfo;
import com.cppoon.tencent.magiccard.api.CardInfoParser;
import com.cppoon.tencent.magiccard.api.impl.SimpleCardInfoParser;
import com.cppoon.tencent.magiccard.util.IOUtil;
import com.google.common.io.Resources;

/**
 * 
 * 
 * @author Cyril
 */
public class CardInfoParserAttributeTest {

	CardInfoParser parser;
	
	TestCardInfoParserListener listener;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		parser = new SimpleCardInfoParser();
		listener = new TestCardInfoParserListener();

		// configure listener
		parser.setListener(listener);

		// parse
		InputStream is = Resources
				.getResource(
						"com/cppoon/tencent/magiccard/api/test/fake/card_info_v3-card_list_only.js")
				.openStream();
		parser.parse(is);
		
		IOUtil.close(is);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		listener = null;
		parser = null;

	}
	
	@Test
	public void testCardId() {
		
		CardInfo card = null;

		// card 1: card ID
		card = listener.getCardById(1001);
		assertEquals("card ID", 1001, card.getId());

		// card 2: card ID
		card = listener.getCardById(1002);
		assertEquals("card ID", 1002, card.getId());
		
	}
	
	@Test
	public void testThemeId() {
		
		CardInfo card = null;
		
		card = listener.getCardById(1002);
		assertEquals("theme ID", 9876, card.getThemeId());

		card = listener.getCardById(1003);
		assertEquals("theme ID", 1, card.getThemeId());
		
	}
	
	@Test
	public void testName() {
		
		CardInfo card = null;
		
		card = listener.getCardById(1002);
		assertEquals("name", "Card 2", card.getName());

		card = listener.getCardById(1003);
		assertEquals("name", "Card 3", card.getName());
		
	}
	
	@Test
	public void testPrice() {
		
		CardInfo card = null;
		
		card = listener.getCardById(1003);
		assertEquals("price", 40.10D, card.getPrice(), 0.00);

		card = listener.getCardById(1004);
		assertEquals("price", 10.00D, card.getPrice(), 0.00);
		
	}
	
	@Test
	public void testType() {
		
		CardInfo card = null;
		
		card = listener.getCardById(1004);
		assertEquals("type", 9, card.getType());

		card = listener.getCardById(1005);
		assertEquals("type", 0, card.getType());
		
	}
	

	@Test
	public void testPickRate() {
		
		CardInfo card = null;
		
		card = listener.getCardById(1005);
		assertEquals("pick rate", 88, card.getPickRate());

		card = listener.getCardById(1006);
		assertEquals("pick rate", 0, card.getPickRate());
		
	}
	
	@Test
	public void testEnable() {
		
		CardInfo card = null;
		
		card = listener.getCardById(1006);
		assertFalse("enabled", card.isEnabled());

		card = listener.getCardById(1007);
		assertTrue("enabled", card.isEnabled());
		
	}
	
	@Test
	public void testVersion() {
		
		CardInfo card = null;
		
		card = listener.getCardById(1007);
		assertEquals("version", 3, card.getVersion());

		card = listener.getCardById(1008);
		assertEquals("version", 1, card.getVersion());
		
	}
	
	@Test
	public void testTime() {
		
		CardInfo card = null;
		
		card = listener.getCardById(1008);
		assertEquals("time", new Date(1388493668L * 1000), card.getTime());

		card = listener.getCardById(1001);
		assertEquals("time", new Date(1299136878L * 1000), card.getTime());
		
		// null time
		card = listener.getCardById(1002);
		assertNull("time", card.getTime());
		
	}
	
	@Test
	public void testItemNo() {
		
		CardInfo card = null;
		
		card = listener.getCardById(1008);
		assertEquals("item number", 1027639, card.getItemNo());

		card = listener.getCardById(1001);
		assertEquals("item number", 1027529, card.getItemNo());
		
	}
	
	@Test
	public void test() throws Exception {

		//
		// THEN
		//
		CardInfo card = null;

		// card 4: type
		card = listener.getCardById(1004);
		assertEquals("type", 9, card.getType());

		// card 4: type
		card = listener.getCardById(1004);
		assertEquals("type", 9, card.getType());

	}

}
