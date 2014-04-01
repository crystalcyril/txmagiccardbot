/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;
import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionAuthStatus;
import com.cppoon.tencent.magiccard.vendor.qzapp.SessionFactory;
import com.cppoon.tencent.magiccard.vendor.qzapp.impl.DefaultSessionFactory;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SessionOverviewTest {

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
	 * Happy path.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetGameOverview_OK() throws Exception {
		
		String username = TestAccount.getUsername();
		String password = TestAccount.getPassword();
		
		SessionFactory sm = new DefaultSessionFactory();
		Session session = sm.createSession(username, password);
		
		
		//
		// WHEN
		//
		AccountOverview acOverview = session.getAccountOverview();
		
		AccountOverviewUtil.print(acOverview);
		
		
		//
		// Lots of assertion.
		//
		
		assertEquals("authentication status", SessionAuthStatus.AUTHENTICATED, session.getAuthStatus());
		
		assertNotNull("account overview", acOverview);
		
		
		assertEquals("player level", 4, acOverview.getPlayerLevel());
		assertEquals("coin", 16400, acOverview.getCoins(), 0);
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
