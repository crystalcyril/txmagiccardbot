/**
 * 
 */
package com.cppoon.tencent.magiccard.vendor.qzapp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cppoon.tencent.magiccard.CancelSynthesisResult;
import com.cppoon.tencent.magiccard.CardInfoSynchronizer;
import com.cppoon.tencent.magiccard.CardManager;
import com.cppoon.tencent.magiccard.CardThemeManager;
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
	public void testCancel_OK_OneStove() throws Exception {
		
		
		Account account = TestAccount.getAccount("live");
		account = TestAccount.getDefaultAccount();
		
		String username = account.getUsername();
		String password = account.getPassword();
		
		DefaultSessionFactory sm = new DefaultSessionFactory();
		sm.setCardManager(cardManager);
		Session session = sm.createSession(username, password);
		
		// card to synthesize.
		int targetCardId = 40;
		
		
		//
		// GIVEN
		//
		AccountOverview acOverview_1 = session.getAccountOverview();
		int freeStoveCountBeforeSynthesis = acOverview_1.getFreeStoveCount();
		
		SynthesizeResult result = session.synthesizeCard(targetCardId);
		assertEquals("synthesis result", SynthesizeResult.OK, result);
		
		AccountOverview acOverview_2 = session.getAccountOverview();
		assertEquals("number of free stoves",
				freeStoveCountBeforeSynthesis - 1,
				acOverview_2.getFreeStoveCount());
		
		
		int cardSlotId = -1;
		for (StoveInfo si : acOverview_2.getStoves()) {
			
			// only deal with matched card.
			if (si.getCardId() != targetCardId) continue;

			// skip non-cancellable stoves.
			if (!si.isCancellable()) continue;
			
			cardSlotId = si.getSlotId();
			break;
			
		}
		
		// must make sure stove slot ID is found.
		assertNotEquals("found stove slot ID", -1, cardSlotId);
		
		//
		// WHEN
		//
		CancelSynthesisResult cancelResult = session.cancelSynthesis(cardSlotId);
		assertEquals("cancel synthesis result", CancelSynthesisResult.OK, cancelResult);
		
		//
		// THEN
		//

		// get account overview.
		AccountOverview acOverview_3 = session.getAccountOverview();
		
		assertEquals("number of free stoves should be restored",
				freeStoveCountBeforeSynthesis,
				acOverview_3.getFreeStoveCount());

	}
	
}
