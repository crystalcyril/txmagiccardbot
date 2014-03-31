/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.test;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cppoon.tencent.magiccard.CardInfoSynchronizer;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.CardThemeManager;
import com.cppoon.tencent.magiccard.StealStoveResult;
import com.cppoon.tencent.magiccard.api.impl.DesktopSiteJsCardInfoSynchronizer;
import com.cppoon.tencent.magiccard.impl.SimpleCardManager;
import com.cppoon.tencent.magiccard.impl.SimpleCardThemeManager;
import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.impl.DefaultSessionFactory;
import com.google.common.io.Resources;

/**
 * 
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SessionSynthesizeCardInStolenStoveTest {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	CardThemeManager cardThemeManager;
	
	CardManager cardManager;
	
	CardInfoSynchronizer synchronizer;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		cardThemeManager = new SimpleCardThemeManager();
		cardManager = new SimpleCardManager();
		
		DesktopSiteJsCardInfoSynchronizer synchronizer = new DesktopSiteJsCardInfoSynchronizer();
		synchronizer.setCardManager(cardManager);
		synchronizer.setCardThemeManager(cardThemeManager);
		this.synchronizer = synchronizer;
		
		// syn the card information.
		InputStream is = Resources
				.getResource("com/cppoon/tencent/magiccard/api/test/card_info_v3.js")
				.openStream();
		synchronizer.synchronize(is);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
		synchronizer = null;
		cardThemeManager = null;
		cardManager = null;
		
	}

	@Test
	public void testStealStove() {
		
		String username = TestAccount.getAccount("live").getUsername();
		String password = TestAccount.getAccount("live").getPassword();
		
		DefaultSessionFactory sm = new DefaultSessionFactory();
		sm.setCardManager(cardManager);
		Session session = sm.createSession(username, password);
		
		int targetUin = 383664208;
		int targetCardId = 4875;
		
		StealStoveResult result = session.stealStove(targetUin, targetCardId);
		
		assertNotNull("steal stove result", result);

		log.info("steal stove result: {}", result);
		
	}

}
