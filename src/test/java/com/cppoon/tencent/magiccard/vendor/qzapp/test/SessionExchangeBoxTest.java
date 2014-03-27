/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionFactory;
import com.cppoon.tencent.magiccard.vendor.qzapp.impl.DefaultSessionFactory;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.ExchangeBoxSlot;

/**
 * Test the exchange box.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SessionExchangeBoxTest {

	Logger log = LoggerFactory.getLogger(getClass());

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

	/**
	 * 
	 */
	@Test
	public void testGetExchangeBoxSlots_OK() {

		String username = TestAccount.getUsername();
		String password = TestAccount.getPassword();

		SessionFactory sm = new DefaultSessionFactory();
		Session session = sm.createSession(username, password);

		//
		// WHEN
		//
		List<ExchangeBoxSlot> slots = session.getExchangeBoxSlots();

		//
		// THEN
		//
		assertNotNull("exchange box slots", slots);

		assertEquals("exchange box slots count", 3, slots.size());

		// check each card
		ExchangeBoxSlot card;

		Iterator<ExchangeBoxSlot> iter = slots.iterator();

		// card
		card = iter.next();
		assertEquals("card theme name", "蜻蜓款款飞", card.getCardThemeName());
		assertEquals("card name", "蜻蜓款款飞", card.getCardName());
		assertEquals("card price", 150, card.getCardPrice(), 0);
		assertEquals("card ID", 2373, card.getCardId());
		assertEquals("slot ID", 2, card.getSlotId());

		// card
		card = iter.next();
		assertEquals("card theme name", "蜻蜓款款飞", card.getCardThemeName());
		assertEquals("card name", "褐斑蜻蜓", card.getCardName());
		assertEquals("card price", 10, card.getCardPrice(), 0);
		assertEquals("card ID", 2368, card.getCardId());
		assertEquals("slot ID", 1, card.getSlotId());

		// card
		card = iter.next();
		assertEquals("card theme name", "蜻蜓款款飞", card.getCardThemeName());
		assertEquals("card name", "大团扇春蜓", card.getCardName());
		assertEquals("card price", 10, card.getCardPrice(), 0);
		assertEquals("card ID", 2369, card.getCardId());
		assertEquals("slot ID", 0, card.getSlotId());

	}

	@Test
	public void testGetExchangeBoxSlots_OK_MultiPage() {

		String username = "2517429151";
		String password = "731521_rpbkdg";

		SessionFactory sm = new DefaultSessionFactory();
		Session session = sm.createSession(username, password);

		//
		// WHEN
		//
		List<ExchangeBoxSlot> cards = session.getExchangeBoxSlots();

		int i = 1;
		for (ExchangeBoxSlot slot : cards) {

			log.info("slot " + i + ": " + slot);

			i++;

		}

	}

}
