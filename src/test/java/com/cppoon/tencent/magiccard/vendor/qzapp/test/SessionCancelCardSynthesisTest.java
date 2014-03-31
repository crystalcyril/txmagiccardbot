/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.test;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.CardInfoSynchronizer;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.CardThemeManager;
import com.cppoon.tencent.magiccard.api.StoveStatus;
import com.cppoon.tencent.magiccard.api.impl.DesktopSiteJsCardInfoSynchronizer;
import com.cppoon.tencent.magiccard.impl.SimpleCardManager;
import com.cppoon.tencent.magiccard.impl.SimpleCardThemeManager;
import com.cppoon.tencent.magiccard.vendor.qzapp.AccountOverview;
import com.cppoon.tencent.magiccard.vendor.qzapp.Session;
import com.cppoon.tencent.magiccard.vendor.qzapp.SynthesizeResult;
import com.cppoon.tencent.magiccard.vendor.qzapp.impl.DefaultSessionFactory;
import com.cppoon.tencent.magiccard.vendor.qzapp.parser.StoveInfo;
import com.cppoon.tencent.magiccard.vendor.qzapp.test.TestAccount.Account;
import com.google.common.io.Resources;

/**
 * Test the card synthesis cancellation test.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class SessionCancelCardSynthesisTest {

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
		
		cardThemeManager = null;
		cardManager = null;
		synchronizer = null;
		
	}
	
	
	@Test
	public void testCancel_OK() throws Exception {
		
		
		Account account = TestAccount.getAccount("live");
		account = TestAccount.getDefaultAccount();
		
		String username = account.getUsername();
		String password = account.getPassword();
		
		DefaultSessionFactory sm = new DefaultSessionFactory();
		sm.setCardManager(cardManager);
		Session session = sm.createSession(username, password);
		
		// card to synthesize.
		int targetCardId = 40;
		
		AccountOverview acOverview_1 = session.getAccountOverview();
		
		SynthesizeResult result = session.synthesizeCard(targetCardId);
		
		AccountOverview acOverview_2 = session.getAccountOverview();
		
		int cardSlotId = -1;
		for (StoveInfo si : acOverview_2.getStoves()) {
			
			// only deal with matched card.
			if (si.getCardId() != targetCardId) continue;
			
			// only deal with synthesizing cards (which maybe cancellable)
			if (si.getStatus() != StoveStatus.SYNTHESIZING) continue;
			
		}

		assertEquals("synthesize card result", SynthesizeResult.OK, result);
		
		
	}
	
}
