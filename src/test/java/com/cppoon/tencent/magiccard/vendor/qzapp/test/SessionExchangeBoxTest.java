/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.test;

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
		printSlots(slots);

		//
		// THEN
		//
		
		// FIXME 2014-03-27 cyril add testing code here.

	
	}

	@Test
	public void testGetExchangeBoxSlots_OK_MultiPage() {

		String username = TestAccount.getDefaultAccount().getUsername();
		String password = TestAccount.getDefaultAccount().getPassword();

		SessionFactory sm = new DefaultSessionFactory();
		Session session = sm.createSession(username, password);

		//
		// WHEN
		//
		List<ExchangeBoxSlot> slots = session.getExchangeBoxSlots();

		printSlots(slots);
		
	}

	
	protected void printSlots(List<ExchangeBoxSlot> slots) {
		
		int i = 1;
		for (ExchangeBoxSlot slot : slots) {

			log.info("slot " + i + ": " + slot);

			i++;

		}
		
	}
	
}
